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

public class Batch {
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

    public void addOrder(Order order){
//        System.out.println(order.getTotalWeight());
        for (int id : order.getItemIDs()) {
            if(!getIDs().contains(id)){
                getIDs().add(id);
            }
        }
        totalWeight += order.getTotalWeight();
        orders.add(order);
    }
}
