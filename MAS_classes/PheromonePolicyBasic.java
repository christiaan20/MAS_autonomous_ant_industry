package MAS_classes;

import src.Grondstof;
import src.Werker;

/**
 * Created by christiaan on 23/04/18.
 */
public class PheromonePolicyBasic implements PheromonePolicy {

    private Werker werker;
    private double expireTime; //expire time of the pheromone in seconds
    private int dropDistance;  //the distance needed to walk before a pheromone is dropped
    private int detectDistance;//the distance an ant can detect a pheromone
    private boolean enablePheroDrop;

    public PheromonePolicyBasic(Werker w) {
        werker = w;
        expireTime = 30.0;
        dropDistance = 30;
        detectDistance = 70;
        enablePheroDrop = true;
    }

    public void selectPheromone()
    {
        if(!werker.getDetectedPheromones().isEmpty())
        {
            //find the closest pheromone and the closest pheromone that you own, always preferres the owned pheromone
            Pheromone chosen = this.closestPheroToTask(Grondstof.explorer);             //the chosen out of all paths
            Pheromone chosenTask = this.closestPheroToTask(werker.getTask());           //the chosen out of all path belonging to his task (not explorer)
            Pheromone chosenOwned = this.closestOwnedPheroToTask(Grondstof.explorer);   //the chosen out of only his own path belonging
            Pheromone chosenOwnedTask = this.closestOwnedPheroToTask(werker.getTask()); //the chosen out of only his own path belonging and to his task

            //The not owned pheromone is only returned of no owned pheromone is found
            if(chosenOwnedTask == null)
            {
                if(chosenTask == null)
                {
                    if(chosenOwned == null)
                    {
                        werker.setCurrPheromone(chosen);
                    }
                    else
                    {
                        werker.setCurrPheromone(chosenOwned);
                    }
                }
                else
                {
                    werker.setCurrPheromone(chosenTask);
                }

            }
            else
            {
                werker.setCurrPheromone(chosenOwnedTask);
            }


        }
        else
        {
            werker.setCurrPheromone(null);
        }

    }

    public Pheromone closestOwnedPheroToTask(Grondstof task)
    {
        //find the closest pheromone and the closest pheromone that you own, always preferres the owned pheromone
        int smallestDist = 1000000;

        Pheromone chosen = null;        //the chosen out of all paths
        for (CustomStruct o : werker.getDetectedPheromones())
        {
            Pheromone p = o.getPheromone();
            if(p.getOwner() == werker)
            {
                if (p.getTask() == task)
                {
                   // int dist = distBetween(werkers.getX(), werkers.getCoY(), p.getX(), p.getY());
                    int dist = o.getDistance();
                    if (p.getOwner() == werker)
                    {
                        if (smallestDist >= dist)
                        {
                            smallestDist = dist;
                            chosen = p;
                        }

                    }
                }
            }

        }
        return chosen;
    }

    public void updateDistancesToPhero()
    {

    }

    public Pheromone closestPheroToTask(Grondstof task)
    {
        //find the closest pheromone and the closest pheromone that you own, always preferres the owned pheromone
        int smallestDist = 1000000;

        Pheromone chosen = null;        //the chosen out of all paths
        for (CustomStruct o : werker.getDetectedPheromones())
        {
            Pheromone p = o.getPheromone();
            if (p.getTask() == task)
            {
                //int dist = distBetween(werkers.getX(), werkers.getCoY(), p.getX(), p.getY());
                int dist = o.getDistance();
                if (p.getOwner() == werker)
                {
                    if (smallestDist >= dist)
                    {
                        smallestDist = dist;
                        chosen = p;
                    }

                }
            }

        }
        return chosen;
    }




    public int distBetween(int x1, int y1,int x2, int y2)
    {
        int Xdist = x1 - x2;
        int Ydist = y1 - y2;
        return (int) Math.abs( Math.sqrt(Math.pow(Xdist,2)+ Math.pow(Ydist,2)));
    }


    public double getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(double expireTime) {
        this.expireTime = expireTime;
    }

    public int getDropDistance() {
        return dropDistance;
    }

    public void setDropDistance(int dropDistance) {
        this.dropDistance = dropDistance;
    }

    public int getDetectDistance() {
        return detectDistance;
    }

    public void setDetectDistance(int detectDistance) {
        this.detectDistance = detectDistance;
    }

    public boolean isEnablePheroDrop() {
        return enablePheroDrop;
    }

    public void setEnablePheroDrop(boolean enablePheroDrop) {
        this.enablePheroDrop = enablePheroDrop;
    }
}
