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

    public PheromonePolicyBasic(Werker w) {
        werker = w;
        expireTime = 30.0;
        dropDistance = 30;
        detectDistance = 40;
    }

    public void selectPheromone()
    {
        if(!werker.getDetectedPheromones().isEmpty())
        {
            //find the closest pheromone and the closest pheromone that you own, always preferres the owned pheromone
            int smallestDist = 1000000;
            int smallestDistToOwnedPhero = 1000000;
            Pheromone chosen = null;
            Pheromone chosenOwned = null;
            for(Pheromone o: werker.getDetectedPheromones())
            {

                if(o.getTask() == werker.getTask() || o.getTask() == Grondstof.explorer)
                {
                    int dist = distBetween(werker.getX(),werker.getCoY(),o.getX(),o.getY());
                    if(o.getOwner() == werker)
                    {
                        if(smallestDistToOwnedPhero >= dist)
                        {
                            chosenOwned = o;
                            smallestDistToOwnedPhero = dist;
                        }
                    }
                    else
                    {
                        if(smallestDist >= dist)
                        {
                            smallestDist = dist;
                            chosen = o;
                        }

                    }


                }
            }
            //The not owned pheromone is only returned of no owned pheromone is found
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
            werker.setCurrPheromone(null);
        }

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
}
