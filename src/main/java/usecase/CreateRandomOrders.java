package usecase;

import helper.Helpers;
import model.entity.Item;
import model.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateRandomOrders {
    private ArrayList<Order> orders = new ArrayList<>();
    private int totalWeight;
    public CreateRandomOrders(List<Item> items, int numberOfOrders, int numberOfItemPerOrder){
        totalWeight = 0;
        for (int i = 0; i < numberOfOrders ; i++) {
            Order order = new Order();
            ArrayList<Integer> itemIDs = new ArrayList<>();
            int weight = 0;

            int n = Helpers.randInt(2, numberOfItemPerOrder);

            for (int j = 0; j < n; j++){
                int id = Helpers.randInt(1, items.size()-1);
                itemIDs.add(id);
                weight += items.get(id).getWeight();
            }
            order.setTotalWeight(weight);
            order.setItemIDs(itemIDs);
            totalWeight += weight;
            orders.add(order);
        }
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
    public int getTotalWeight(){ return totalWeight; }
}
