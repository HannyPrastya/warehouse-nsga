package model.objective;

import model.interfaces.IObjectiveFunction;

import java.util.ArrayList;

public class Distance implements IObjectiveFunction {

    private static final String OBJECTIVE_TITLE = "Distance";

    @Override
    public String objectiveFunctionTitle() {
        return OBJECTIVE_TITLE;
    }

    @Override
    public double getObjectiveValue(ArrayList<Double> fitness) {

        return 0;
    }
}