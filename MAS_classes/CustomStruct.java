package MAS_classes;

/**
 * Created by christiaan on 28/04/18.
 */
public class CustomStruct {

    private int distance;
    private Pheromone pheromone;

    public CustomStruct(Pheromone pheromone) {
        this.distance = 10000000;
        this.pheromone = pheromone;
    }

    public CustomStruct(Pheromone pheromone, int distance) {
        this.distance = distance;
        this.pheromone = pheromone;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Pheromone getPheromone() {
        return pheromone;
    }

    public void setPheromone(Pheromone pheromone) {
        this.pheromone = pheromone;
    }
}
