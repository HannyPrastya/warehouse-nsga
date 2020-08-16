package usecase;

import model.entity.Location;
import model.entity.Warehouse;

import java.util.ArrayList;

public class GetShortestDistance {

    ArrayList<Location> map;
    Warehouse warehouse;

    public GetShortestDistance(ArrayList<Location> map, Warehouse warehouse){
        this.map = map;
        this.warehouse = warehouse;

        for (int i = 0; i < map.size(); i++){
            Location start = map.get(i);
            ArrayList<Integer> distances = new ArrayList<>();

            for (int j = 0; j < map.size(); j++){
                Location end = map.get(j);
                int startX = start.getX() + (i != 0 ? (start.getPosition() == 0 ? -1 : 1) : 0);
                int endX = end.getX() + (j != 0 ? (end.getPosition() == 0 ? -1 : 1) : 0);

                int distance = 0;

//                is in one row?
                if(start.getRowIndex() == end.getRowIndex()){
                    if(Math.abs(start.getX()-end.getX()) == 2){
                        distance += Math.abs(start.getY()-end.getY())+Math.abs(startX-endX);
                    }else{
                        if(start.getX() != end.getX()){
                            if((start.getDirection() == end.getDirection()) && start.getDirection() == 1){
                                distance += (warehouse.getNumberOfRows()-start.getIndex())+(warehouse.getNumberOfRows()-end.getIndex())+2;
                            }else{
                                distance += start.getIndex()+end.getIndex();
                            }
                            distance += Math.abs(startX-endX);
                        }else{
                            distance += Math.abs(start.getY()-end.getY());
                        }
                    }
                }else{
                    distance += Math.abs(start.getY()-end.getY())+Math.abs(startX-endX);
                }
                distances.add(distance);
            }
            start.setDistances(distances);
        }
    }

    public ArrayList<Location> getDistance(){
        return map;
    }
}
