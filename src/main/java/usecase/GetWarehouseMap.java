package usecase;

import model.entity.data.Location;
import model.entity.data.Warehouse;

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
        int numberOfBlocks = 3;
        ArrayList<Location> locations = new ArrayList<>();
        Location depot = new Location();
        depot.setX(x);
        depot.setY(y);
        locations.add(depot);
        for(int i = 0; i <= warehouse.getNumberOfVerticalAisle(); i++){
            x = 1+(2*i)+(2*(i+1));
//            x = 1+((column+1)*i)+(2*(i+1));
            for(int j = 0; j <= warehouse.getNumberOfHorizontalAisle(); j++){
//                y = 2+((column+3+1)*j)+(2*(j+1));
                y = 1+((column+2)*j)+(2*(j+1));

//                for(int b = 0; b < numberOfBlocks; b++){
//                    Location block = new Location();
//                    block.setX(x+b);
//                    block.setY(y-1);
//                    blocks.add(block);
//                }

                for(int k = 0; k < warehouse.getNumberOfRows(); k++) {
                    int dir = k+1 > warehouse.getNumberOfRows()/2 ? 1 : 0;
                    Location locLeft = new Location();
                    locLeft.setX(x);
                    locLeft.setY(y+k);
                    locLeft.setIndex(k+1);
                    locLeft.setDirection(dir);
                    locLeft.setRowIndex(j);
                    locations.add(locLeft);

//                    Location block = new Location();
//                    block.setX(x+1);
//                    block.setY(y+k);
//                    blocks.add(block);

                    Location locRight = new Location();
                    locRight.setX(x+1);
//                    locRight.setX(x+2);
                    locRight.setY(y+k);
                    locRight.setIndex(k+1);
                    locRight.setDirection(dir);
                    locRight.setRowIndex(j);
                    locations.add(locRight);
                }

//                for(int b = 0; b < numberOfBlocks; b++){
//                    Location block = new Location();
//                    block.setX(x+b);
//                    block.setY(y+row);
//                    blocks.add(block);
//                }
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
