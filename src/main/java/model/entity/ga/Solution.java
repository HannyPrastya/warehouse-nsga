package model.entity.ga;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "chromosome",
        "distance",
        "space"
})

public class Solution {
    @JsonProperty("chromosome")
    private Integer[] chromosome;

    @JsonProperty("chromosome")
    public Integer[] getChromosome() {
        return chromosome;
    }

    @JsonProperty("chromosome")
    public void setChromosome(Integer[] chromosome) {
        this.chromosome = chromosome;
    }

    @JsonProperty("distance")
    private float distance;

    @JsonProperty("distance")
    public float getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(float distance) {
        this.distance = distance;
    }

    @JsonProperty("space")
    private float space;

    @JsonProperty("space")
    public float getSpace() {
        return space;
    }

    @JsonProperty("space")
    public void setSpace(float space) {
        this.space = space;
    }

}
