package usecase;

import model.entity.Location;
import model.entity.Warehouse;

import java.util.ArrayList;

public class GetWarehouseMap {

    private ArrayList<ArrayList<Integer>> distances = new ArrayList<>();
    private ArrayList<Location> blocks = new ArrayList<>();
    private int maxY;
    private int maxX;

    public ArrayList<Location> getLocationNodes(Warehouse warehouse){
        int column = 2;
        int x = 1;
        int y = 1;

        ArrayList<Location> locations = new ArrayList<>();
        Location depot = new Location();
        depot.setX(x);
        depot.setY(y);
        locations.add(depot);

        for(int i = 0; i <= warehouse.getNumberOfVerticalAisle(); i++){
//            1 = wall left
//            2 * i = pods
//            1 * (i+1) = space
            x = 1+(2*i)+(1*(i+1));
            for(int j = 0; j <= warehouse.getNumberOfHorizontalAisle(); j++){
//                1 = wall bottom
//                warehouse.getNumberOfRows()*j = pods
//                1 * (j+1) = space
                y = 1+(warehouse.getNumberOfRows()*j)+(1*(j+1));
                for(int k = 0; k < warehouse.getNumberOfRows(); k++) {
                    int dir = k+1 > warehouse.getNumberOfRows()/2 ? 1 : 0;
                    Location locLeft = new Location();
                    locLeft.setX(x);
                    locLeft.setY(y+k);
                    locLeft.setIndex(k+1);
//                    go top or bottom for shortest direction
                    locLeft.setDirection(dir);
                    locLeft.setRowIndex(j);
                    locLeft.setWeight((int )(Math.random() * 3 + 1));
                    locLeft.setPosition(0);
                    locations.add(locLeft);

                    Location locRight = new Location();
                    locRight.setX(x+1);
                    locRight.setY(y+k);
                    locRight.setIndex(k+1);
                    locRight.setDirection(dir);
                    locRight.setRowIndex(j);
                    locRight.setPosition(1);
                    locRight.setWeight((int )(Math.random() * 3 + 1));
                    locations.add(locRight);
                }
            }
            maxX = x+3;
            maxY = y+warehouse.getNumberOfRows()+3;
        }

        return locations;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public ArrayList<Location> getBlocks() {
        return blocks;
    }

}
