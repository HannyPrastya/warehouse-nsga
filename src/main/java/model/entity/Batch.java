package model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "orders",
        "locations",
        "totalWeight"
})

public class Batch {
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
    private Integer totalWeight;

    @JsonProperty("orders")
    public Integer getTotalWeight() {
        return totalWeight;
    }

    @JsonProperty("orders")
    public void setTotalWeight(Integer totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void addOrder(Order order){
        orders.add(order);
    }
}
