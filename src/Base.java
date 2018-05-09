package src;

import java.util.ArrayList;
import java.util.List;

import static src.Resource_types.steen;

/**
 * Created by Gebruiker on 9/05/2018.
 */
public class Base {

    private ArrayList<Order> orderList;
    private int num_workers;

    /**
     * Initialise a goal to complete
     */
    public List<Order> init_order_list(){

        Order steen1 = new Order(100, steen);

        ArrayList<Order> orderList = new ArrayList<Order>();
        orderList.add(steen1);

        return orderList;
    }

}
