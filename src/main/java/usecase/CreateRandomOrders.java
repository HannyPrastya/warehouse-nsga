package usecase;

import model.entity.Item;
import model.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateRandomOrders {
    public ArrayList<Order> orders = new ArrayList<>();
    public CreateRandomOrders(List<Item> items, int numberOfItems, int numberOfOrders, int numberOfItemPerOrder){
        for (int i = 0; i < numberOfOrders ; i++) {
            Order order = new Order();
            ArrayList<Integer> itemIDs = new ArrayList<>();
            int weight = 0;

            for (int j = 0; j < numberOfItemPerOrder; j++){
                int id = new Random().nextInt((numberOfItems - 1) + 1);
                itemIDs.add(id);
                weight += items.get(id).getWeight();
            }
            order.setTotalWeight(weight);
            order.setItemIDs(itemIDs);
            orders.add(order);
        }
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
