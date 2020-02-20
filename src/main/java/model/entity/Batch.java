package model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "orders",
        "locations",
        "totalWeight",
        "IDs"
})

public class Batch implements Cloneable{
    @JsonProperty("IDs")
    private ArrayList<Integer> IDs = new ArrayList<>();

    @JsonProperty("IDs")
    public ArrayList<Integer> getIDs() {
        return IDs;
    }

    @JsonProperty("IDs")
    public void setIDs(ArrayList<Integer> IDs) {
        this.IDs = IDs;
    }

    @JsonProperty("orders")
    private ArrayList<Order> orders = new ArrayList<>();

    @JsonProperty("orders")
    public ArrayList<Order> getOrders() {
        return orders;
    }

    @JsonProperty("orders")
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @JsonProperty("locations")
    private ArrayList<Integer> locations;

    @JsonProperty("locations")
    public ArrayList<Integer> getLocations() {
        return locations;
    }

    @JsonProperty("locationss")
    public void setLocations(ArrayList<Integer> locations) {
        this.locations = locations;
    }

    @JsonProperty("totalWeight")
    private int totalWeight = 0;

    @JsonProperty("totalWeight")
    public int getTotalWeight() {
        return totalWeight;
    }

    @JsonProperty("orders")
    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void refreshItems(){
        setIDs(new ArrayList<>());
        for (Order order: getOrders()) {
            for (int id : order.getItemIDs()) {
                if(!getIDs().contains(id)){
                    getIDs().add(id);
                }
            }
        }
    }

    public void addOrder(Order order){
        totalWeight += order.getTotalWeight();
        orders.add(order);
    }

    public Order removeAndGetOrder(){
        Order order = getOrders().get(0);
        getOrders ().remove(0);
        totalWeight -= order.getTotalWeight();
        return order;
    }

    public Batch clone() throws CloneNotSupportedException {
        Batch cloned = (Batch) super.clone();
//        cloned.set((ArrayList<Integer>) chromosome.clone());

        return cloned;
    }
}
