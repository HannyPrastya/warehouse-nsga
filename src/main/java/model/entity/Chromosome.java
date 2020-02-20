package model.entity;

public class Chromosome {
    private String key;
    private int distance;

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getDistance() {
        return distance;
    }

    public String getKey() {
        return key;
    }
}
