package model.entity.data;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numberOfOrder",
        "capicityOfPicker",
        "itemsInOrder"
})
public class Plan {

    @JsonProperty("numberOfOrder")
    private Integer numberOfOrder;
    @JsonProperty("capicityOfPicker")
    private Integer capicityOfPicker;
    @JsonProperty("itemsInOrder")
    private Integer itemsInOrder;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("numberOfOrder")
    public Integer getNumberOfOrder() {
        return numberOfOrder;
    }

    @JsonProperty("numberOfOrder")
    public void setNumberOfOrder(Integer numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }

    @JsonProperty("capicityOfPicker")
    public Integer getCapicityOfPicker() {
        return capicityOfPicker;
    }

    @JsonProperty("capicityOfPicker")
    public void setCapicityOfPicker(Integer capicityOfPicker) {
        this.capicityOfPicker = capicityOfPicker;
    }

    @JsonProperty("itemsInOrder")
    public Integer getItemsInOrder() {
        return itemsInOrder;
    }

    @JsonProperty("itemsInOrder")
    public void setItemsInOrder(Integer itemsInOrder) {
        this.itemsInOrder = itemsInOrder;
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