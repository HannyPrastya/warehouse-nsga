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
    private int distance = 0;

    @JsonProperty("distance")
    public int getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(int distance) {
        this.distance = distance;
    }

    @JsonProperty("space")
    private int space = 0;

    @JsonProperty("space")
    public int getSpace() {
        return space;
    }

    @JsonProperty("space")
    public void setSpace(int space) {
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
