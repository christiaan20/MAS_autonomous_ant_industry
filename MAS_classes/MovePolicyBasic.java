package MAS_classes;
import src.Building;
import src.Resource;
import src.Task;
import src.Worker;

import java.util.*;


/**
 * Created by christiaan on 23/04/18.
 */
public class MovePolicyBasic implements movePolicy {

 //   public Model model;
    private Worker worker;
    private int target_x; //absolute x coordinate where the workers is going to
    private int target_y; //absolute y coordinate where the workers is going to
    private Object targetObject; //Object for the worker to enter
    private int distWalked;


    //parameters influencing the behavior of the ant
    private int stepDistance;
    private Random random = new Random(); // Random object to generate directions of travel
    private int bigTurnThreshold = 10; //the amount of steps taken before a mayor turn is possible
    private int bigTurnCount = 0;




    public MovePolicyBasic(Worker w, int stepDistance)
    {
        this.worker = w;
       // this.model = model;
        target_x = w.getX();
        target_y = w.getCoY();
        targetObject = null;
        this.stepDistance = stepDistance;
    }

    public boolean move(int SizeX,int SizeY)
    {
        if(worker.getTask() == Task.explorer)
        {
            return explorerMove(SizeX, SizeY);
        }
        else if (worker.getTask() == Task.steen)
        {
            return steenWorkerMove(SizeX, SizeY);
        }
        return false;
    }

    public boolean explorerMove(int sizeX,int sizeY)
    {
        return wanderWithin(sizeX, sizeY);
    }



    public boolean steenWorkerMove(int sizeX, int sizeY)
    {
        //if the there is no current pheromone in the worker then select a new pheromone to follow
        if(worker.getCurrPheromone() == null)
        {
            worker.getPheromonePolicy().selectPheromone();
            worker.getPheromonePolicy().setEnablePheroDrop(true);
        }

        Pheromone phero =  worker.getCurrPheromone();

        //if there is no valid pheromone in the area just wander
        if( phero == null)
        {
            worker.getPheromonePolicy().setEnablePheroDrop(false);
            wanderWithin(sizeX,sizeY);
           // workers.setTask(Task.explorer);
        }
        /*else if (phero.getX() == workers.getX() && phero.getY() == workers.getCoY())
        {

            //if the workers has reached the current pheromone add to the visited pheromones and select a new one
            workers.addVisitedPheromone(phero);
            workers.getPheromonePolicy().selectPheromone();
            phero = workers.getCurrPheromone();
           if( phero != null)
           {
               workers.moveTo(phero.getX(),phero.getY());
           }

        }*/
        else
        {
            worker.moveTo(phero.getX(),phero.getY());

            if (phero.getX() == worker.getX() && phero.getY() == worker.getCoY())
            {
                worker.addVisitedPheromone(phero);
                worker.setCurrPheromone(null);
                return true;
            }

        }



        return false;
    }

    public boolean foundObject(Object obj)
    {
        worker.getPheromonePolicy().setEnablePheroDrop(true);

            if (worker.getTask() == Task.explorer) {

                return explorerFoundObject(obj);
            }
            if (worker.getTask() == Task.steen) {
                return steenWorkerFoundObject(obj);
            }


        return false;

    }

    public boolean explorerFoundObject(Object obj)
    {
        if (obj instanceof Resource)
        {
            worker.setDistLastPheroDrop(worker.getPheromonePolicy().getDropDistance()+1);

            Resource stof = (Resource) obj;

            worker.setMoving(false);
            worker.setTask(stof.getStof());
            worker.removeAllVisitedPhero(); //so that it can find his way home but might want to give this responsibility to the pheromonePolicy
            worker.removeAllDetectedPhero();

            return true;
        }
        return false;
    }

    public boolean steenWorkerFoundObject(Object obj)
    {
        if (obj instanceof Resource)
        {
            Resource stof = (Resource) obj;

            if(worker.getTask() == stof.getStof() && !(worker.isVol()))
            {
                worker.setDistLastPheroDrop(worker.getPheromonePolicy().getDropDistance()+1);

                worker.setMoving(false);
                worker.removeAllVisitedPhero(); //so that it can find his way home but might want to give this responsibility to the pheromonePolicy
                worker.removeAllDetectedPhero();
                return true;
            }
            return false;

        }
        else if(obj instanceof Building)
        {
            Building b = (Building) obj;
            if( worker.getLoad() > 0)
            {
                worker.setDistLastPheroDrop(worker.getPheromonePolicy().getDropDistance()+1);

                worker.setMoving(false);
                worker.removeAllVisitedPhero();
                worker.removeAllDetectedPhero();
                return true;
            }

                    }
        return false;
    }






    public boolean checkOutsideOfBorders(int x, int y, int sizeX, int sizeY)
    {
        if(x < 0 || y < 0 || x > sizeX || y > sizeY)
        {
            return true;
        }
        return false;
    }

    private boolean wanderWithin(int sizeX, int sizeY) {

        boolean output = false; //whether the move has reached a target
        //each step of stepDistance long the ant turns -90° to 90° after bigTurnThreshold the ant can turn upto 360°
        if( distWalked >= stepDistance || ((target_x == worker.getX() && target_y == worker.getCoY())))
        {

            double corner = (random.nextDouble()-0.5)*(Math.PI); //make a bend of min -90° and 90°
            double totCorner = worker.getCurrDirection() + corner; //add bend to currecnt direction of the workers

            //generate a target based on the stepDistance
            if(bigTurnCount >=  bigTurnThreshold)
            {
                totCorner = (random.nextDouble())*(Math.PI*2); //make a bend of 0° to 360°
                bigTurnCount = 0;
            }
            bigTurnCount = bigTurnCount+1;

            target_x = worker.getX() +  (int) (Math.cos(totCorner)*stepDistance);
            target_y = worker.getCoY() + (int) (Math.sin(totCorner)*stepDistance);

            //if the target coordinates are outside of the borders of the map the ant is turned back by decreasing totCorner with 180° (but in radials)
            if(checkOutsideOfBorders(target_x,target_y,sizeX,sizeY))
            {
                totCorner = totCorner - Math.PI;
                target_x = worker.getX() +  (int) (Math.cos(totCorner)*stepDistance);
                target_y = worker.getCoY() + (int) (Math.sin(totCorner)*stepDistance);
            }

            distWalked =0;
            worker.setCurrDirection(totCorner);
            output = true;
        }
        distWalked = distWalked + worker.moveTo(target_x,target_y);
        return output;
    }

    public int getTarget_x() {
        return target_x;
    }

    public void setTarget_x(int target_x) {
        this.target_x = target_x;
    }

    public int getTarget_y() {
        return target_y;
    }

    public void setTarget_y(int target_y) {
        this.target_y = target_y;
    }
}
