package MAS_classes;

import src.Grondstof;
import src.Werker;
import src.Object;

/**
 * Created by christiaan on 23/04/18.
 */
public class Pheromone extends Object{

    private double expireTime; // the expire time of the pheromone in seconds
    private Grondstof task;
    private Werker owner;

    public Pheromone(int x, int y, double expireTime, Grondstof task) {
         super.hoverOver= false;
         super.selected = false;
         super.coX = x;
         super.coY = y;
         super.Xsize = 10;
         super.Ysize = 10;
         this.expireTime = expireTime;
         this.task= task;
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

    public Grondstof getTask() {
        return task;
    }

    public void setTask(Grondstof task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pheromone pheromone = (Pheromone) o;

        if (Double.compare(pheromone.getExpireTime(), getExpireTime()) != 0) return false;
        if(super.equals(o)  &&  getTask() == pheromone.getTask()) return true ;
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(getExpireTime());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getTask().hashCode();
        return result;
    }

    public Werker getOwner() {
        return owner;
    }

    public void setOwner(Werker owner) {
        this.owner = owner;
    }
}
