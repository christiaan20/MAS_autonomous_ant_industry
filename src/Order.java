package src;

/**
 * Created by Gebruiker on 9/05/2018.
 */
public class Order {

    private int amount;
    private Resource_types type;
    private boolean completed;

    public Order(int amount, Resource_types type) {
        this.amount = amount;
        this.type = type;
        this.completed = false;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Resource_types getType() {
        return type;
    }

    public void setType(Resource_types type) {
        this.type = type;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
