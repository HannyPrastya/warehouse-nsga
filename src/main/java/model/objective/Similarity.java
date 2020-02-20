package model.objective;

import model.interfaces.IObjectiveFunction;

import java.util.ArrayList;

public class Similarity implements IObjectiveFunction {

    private static final String OBJECTIVE_TITLE = "Similarity";

    @Override
    public String objectiveFunctionTitle() {
        return OBJECTIVE_TITLE;
    }

    @Override
    public double getObjectiveValue(ArrayList<Double> fitness) {
        return 0;
    }
}