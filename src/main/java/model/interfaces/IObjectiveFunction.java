package model.interfaces;

import java.util.ArrayList;

public interface IObjectiveFunction {
    public String objectiveFunctionTitle();
    public double getObjectiveValue(ArrayList<Double> fitness);
}
