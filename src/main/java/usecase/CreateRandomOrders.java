package usecase;

import model.entity.Order;

import java.util.ArrayList;
import java.util.Random;

public class CreateRandomOrders {
    public ArrayList<Order> orders = new ArrayList<>();
    public CreateRandomOrders(int numberOfItems, int numberOfOrders,int numberOfItemPerOrder){
        for (int i = 0; i < numberOfOrders ; i++) {
            Order order = new Order();
            ArrayList<Integer> items = new ArrayList<>();

            for (int j = 0; j < numberOfItemPerOrder; j++){
                items.add(new Random().nextInt((numberOfItems - 1) + 1));
            }
            order.setItemIDs(items);
            orders.add(order);
        }
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
