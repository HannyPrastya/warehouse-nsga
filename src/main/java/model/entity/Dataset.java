
package model.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "numberOfOrders",
    "numberOfparts",
    "totalOfWeight",
    "orders"
})
public class Dataset {

    @JsonProperty("numberOfOrders")
    private Integer numberOfOrders;
    @JsonProperty("numberOfparts")
    private Integer numberOfparts;
    @JsonProperty("totalOfWeight")
    private Integer totalOfWeight;
    @JsonProperty("orders")
    private List<Order> orders = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("numberOfOrders")
    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    @JsonProperty("numberOfOrders")
    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    @JsonProperty("numberOfparts")
    public Integer getNumberOfparts() {
        return numberOfparts;
    }

    @JsonProperty("numberOfparts")
    public void setNumberOfparts(Integer numberOfparts) {
        this.numberOfparts = numberOfparts;
    }

    @JsonProperty("totalOfWeight")
    public Integer getTotalOfWeight() {
        return totalOfWeight;
    }

    @JsonProperty("totalOfWeight")
    public void setTotalOfWeight(Integer totalOfWeight) {
        this.totalOfWeight = totalOfWeight;
    }

    @JsonProperty("orders")
    public List<Order> getOrders() {
        return orders;
    }

    @JsonProperty("orders")
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
