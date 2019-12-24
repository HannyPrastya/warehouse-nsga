package usecase;

import model.entity.data.Location;

import java.util.ArrayList;

public class GetWarehouseMap {

    private ArrayList<ArrayList<Integer>> distances = new ArrayList<>();
    private ArrayList<Location> blocks = new ArrayList<>();
    private int maxY;
    private int maxX;

    public ArrayList<Location> getLocationNodes(int horizontalAisle, int verticalAsile, int row){
        int column = 2;
        int x = 1;
        int y = 1;
        int numberOfBlocks = 3;
        ArrayList<Location> locations = new ArrayList<>();
        Location depot = new Location();
        depot.setX(x);
        depot.setY(y);
        locations.add(depot);
        for(int i = 0; i <= verticalAsile; i++){
            x = (1*i)+(2*(i+1));
//            x = 1+((column+1)*i)+(2*(i+1));
            for(int j = 0; j <= horizontalAisle; j++){
//                y = 2+((column+3+1)*j)+(2*(j+1));
                y = ((column+2)*j)+(2*(j+1));

//                for(int b = 0; b < numberOfBlocks; b++){
//                    Location block = new Location();
//                    block.setX(x+b);
//                    block.setY(y-1);
//                    blocks.add(block);
//                }

                for(int k = 0; k < row; k++) {
                    Location locLeft = new Location();
                    locLeft.setX(x);
                    locLeft.setY(y+k);
                    locations.add(locLeft);

//                    Location block = new Location();
//                    block.setX(x+1);
//                    block.setY(y+k);
//                    blocks.add(block);

                    Location locRight = new Location();
                    locRight.setX(x+1);
//                    locRight.setX(x+2);
                    locRight.setY(y+k);
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
            maxY = y+row+3;
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
