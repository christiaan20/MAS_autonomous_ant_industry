package MAS_classes;

import src.Resource_types;
import src.Worker;
import src.Object;

/**
 * Created by christiaan on 23/04/18.
 */
public class Pheromone extends Object{

    private double expireTime; // the expire time of the pheromone in seconds
    private Resource_types resourcetypes;
    private Worker owner;

    public Pheromone(int x, int y, double expireTime, Resource_types resourcetypes, Worker owner) {
         super.hoverOver= false;
         super.selected = false;
         super.coX = x;
         super.coY = y;
         super.Xsize = 10;
         super.Ysize = 10;
         this.expireTime = expireTime;
         this.resourcetypes = resourcetypes;
         this.owner = owner;
    }

    //method to decrease the lifetime of the pheromine returns true when pheromone out of time and is deleted
    public boolean tick(double msec)
    {
        expireTime = expireTime - msec/1000;
        if(expireTime <= 0.0)
        {
            return true;
        }
        return false;
    }

    public double getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(double expireTime) {
        this.expireTime = expireTime;
    }

    public Resource_types getResourcetypes() {
        return resourcetypes;
    }

    public void setResourcetypes(Resource_types resourcetypes) {
        this.resourcetypes = resourcetypes;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pheromone pheromone = (Pheromone) o;

        if (Double.compare(pheromone.getExpireTime(), getExpireTime()) != 0) return false;
        if(super.equals(o)  &&  getResourcetypes() == pheromone.getResourcetypes()) return true ;
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(getExpireTime());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getResourcetypes().hashCode();
        return result;
    }

    public Worker getOwner() {
        return owner;
    }

    public void setOwner(Worker owner) {
        this.owner = owner;
    }
}
