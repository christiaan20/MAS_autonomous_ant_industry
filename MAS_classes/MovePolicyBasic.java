package MAS_classes;
import src.Gebouw;
import src.Grondstof;
import src.Grondstoffen;
import src.Werker;

import java.util.*;


/**
 * Created by christiaan on 23/04/18.
 */
public class MovePolicyBasic implements movePolicy {

 //   public Model model;
    private Werker werker;
    private int target_x; //absolute x coordinate where the werker is going to
    private int target_y; //absolute y coordinate where the werker is going to
    private Object targetObject; //Object for the worker to enter
    private int distWalked;


    //parameters influencing the behavior of the ant
    private int stepDistance;
    private Random random = new Random(); // Random object to generate directions of travel
    private int bigTurnThreshold = 10; //the amount of steps taken before a mayor turn is possible
    private int bigTurnCount = 0;




    public MovePolicyBasic(Werker w,int stepDistance)
    {
        this.werker = w;
       // this.model = model;
        target_x = w.getX();
        target_y = w.getCoY();
        targetObject = null;
        this.stepDistance = stepDistance;
    }

    public boolean move(int SizeX,int SizeY)
    {
        if(werker.getTask() == Grondstof.explorer)
        {
            return explorerMove(SizeX, SizeY);
        }
        else if (werker.getTask() == Grondstof.steen)
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
        if(werker.getCurrPheromone() == null)
        {
            werker.getPheromonePolicy().selectPheromone();
        }

        Pheromone phero =  werker.getCurrPheromone();

        //if there is no valid pheromone in the area just wander
        if( phero == null)
        {
            wanderWithin(sizeX,sizeY);
        }
        else if (phero.getX() == werker.getX() && phero.getY() == werker.getCoY())
        {
            //if the werker has reached the current pheromone add to the visited pheromones and select a new one
            werker.addVisitedPheromone(phero);
            werker.getPheromonePolicy().selectPheromone();
            phero = werker.getCurrPheromone();
           if( phero != null)
           {
               werker.moveTo(phero.getX(),phero.getY());
           }

        }
        else
        {
            werker.moveTo(phero.getX(),phero.getY());
        }

        return false;
    }

    public boolean foundObject(Object obj)
    {
        if(werker.getTask() == Grondstof.explorer)
        {
            return explorerFoundObject(obj);
        }
        if(werker.getTask() == Grondstof.steen)
        {
            return steenWorkerFoundObject(obj);
        }

        return false;

    }

    public boolean explorerFoundObject(Object obj)
    {
        if (obj instanceof Grondstoffen)
        {
            Grondstoffen stof = (Grondstoffen) obj;

            werker.setMoving(false);
            werker.setTask(stof.getStof());
            werker.removeAllVisitedPhero(); //so that it can find his way home but might want to give this responsibility to the pheromonePolicy

            return true;
        }
        return false;
    }

    public boolean steenWorkerFoundObject(Object obj)
    {
        if (obj instanceof Grondstoffen)
        {
            Grondstoffen stof = (Grondstoffen) obj;

            if(werker.getTask() == stof.getStof())
            {
                werker.setMoving(false);
                werker.removeAllVisitedPhero(); //so that it can find his way home but might want to give this responsibility to the pheromonePolicy
                return true;
            }
            return false;

        }
        else if(obj instanceof Gebouw)
        {
            Gebouw b = (Gebouw) obj;
            if( werker.getLading() > 0)
            {
                werker.setMoving(false);
                werker.removeAllVisitedPhero();
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
        if( distWalked >= stepDistance || ((target_x == werker.getX() && target_y == werker.getCoY())))
        {

            double corner = (random.nextDouble()-0.5)*(Math.PI); //make a bend of min -90° and 90°
            double totCorner = werker.getCurrDirection() + corner; //add bend to currecnt direction of the werker

            //generate a target based on the stepDistance
            if(bigTurnCount >=  bigTurnThreshold)
            {
                totCorner = (random.nextDouble())*(Math.PI*2); //make a bend of 0° to 360°
                bigTurnCount = 0;
            }
            bigTurnCount = bigTurnCount+1;

            target_x = werker.getX() +  (int) (Math.cos(totCorner)*stepDistance);
            target_y = werker.getCoY() + (int) (Math.sin(totCorner)*stepDistance);

            //if the target coordinates are outside of the borders of the map the ant is turned back by decreasing totCorner with 180° (but in radials)
            if(checkOutsideOfBorders(target_x,target_y,sizeX,sizeY))
            {
                totCorner = totCorner - Math.PI;
                target_x = werker.getX() +  (int) (Math.cos(totCorner)*stepDistance);
                target_y = werker.getCoY() + (int) (Math.sin(totCorner)*stepDistance);
            }

            distWalked =0;
            werker.setCurrDirection(totCorner);
            output = true;
        }
        distWalked = distWalked + werker.moveTo(target_x,target_y);
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
