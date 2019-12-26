package model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "chromosome",
        "distance",
        "space",
        "batches"
})

public class Solution {
    @JsonProperty("chromosome")
    private ArrayList<Integer> chromosome;

    @JsonProperty("chromosome")
    public ArrayList<Integer> getChromosome() {
        return chromosome;
    }

    @JsonProperty("chromosome")
    public void setChromosome(ArrayList<Integer> chromosome) {
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

    @JsonProperty("batches")
    private ArrayList<Batch> batches;

    @JsonProperty("batches")
    public ArrayList<Batch> getBatches() {
        return batches;
    }

    @JsonProperty("space")
    public void setBatches(ArrayList<Batch> batches) {
        this.batches = batches;
    }


}
