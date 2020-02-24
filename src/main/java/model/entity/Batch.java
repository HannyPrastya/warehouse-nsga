package model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "orders",
        "locations",
        "totalWeight",
        "IDs"
})

public class Batch implements Cloneable{
    @JsonProperty("IDs")
    private Set<Integer> IDs = new HashSet<>();

    @JsonProperty("IDs")
    public Set<Integer> getIDs() {
        return IDs;
    }

    @JsonProperty("IDs")
    public void setIDs(Set<Integer> IDs) {
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
        setIDs(new HashSet<>());
        for (Order order: getOrders()) {
            IDs.addAll(order.getItemIDs());
        }
    }

    public void addOrder(Order order){
        totalWeight += order.getTotalWeight();
        orders.add(order);
        IDs.addAll(order.getItemIDs());
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
