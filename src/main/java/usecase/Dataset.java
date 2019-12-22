package usecase;

import model.entity.data.Location;
import model.entity.ga.Solution;

import java.util.ArrayList;

public class Dataset {

    ArrayList<ArrayList<Integer>> distances = new ArrayList<>();


    public void getLocationNodes(int horizontalAisle, int verticalAsile, int row){
        int column = 2;
        int x = 1;
        int y = 1;
        ArrayList<Location> locations = new ArrayList<>();
        Location depot = new Location();
        depot.setX(x);
        depot.setY(y);
        locations.add(depot);
        for(int i = 0; i <= verticalAsile; i++){
            x = 1+(2*i)+(2*(i+1));
            for(int j = 0; j <= horizontalAisle; j++){
                y = 1+(column*j)+(2*(j+1));
                for(int k = 0; k < row; k++) {
                    for (int l = 0; l < column; l++) {
                        Location loc = new Location();
                        loc.setX(x+l+(l == 0 ? -1 : 1));
                        loc.setY(y+k);
                        locations.add(loc);
                    }
                }
            }
        }

        for(Location start : locations){
            ArrayList<Integer> dist = new ArrayList<>();
            for(Location end : locations){
                Integer distance = 0;
                dist.add(distance);
            }
            distances.add(dist);
        }

    }

}
