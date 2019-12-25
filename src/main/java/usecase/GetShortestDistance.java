package usecase;

import model.entity.data.Location;
import model.entity.data.Warehouse;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.*;

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

                int startX = ((i % 2) == 0 ?  1 : -1) + start.getX();
                int endX =  ((j % 2) == 0 ?  1 : -1) + end.getX();

//                System.out.println(start.getX()+"-"+start.getY()+"-"+startX+"--"+start.getDirection()+"--"+start.getIndex()+"--"+start.getRowIndex());
//                System.out.println(end.getX()+"-"+end.getY()+"-"+endX+"--"+end.getDirection()+"--"+end.getIndex()+"--"+end.getRowIndex());

                int distance = 0;

                if(start.getRowIndex() == end.getRowIndex()){
//                    System.out.println(true);

                    if(Math.abs(start.getX()-end.getX()) == 3){
                        distance += Math.abs(start.getY()-end.getY())+Math.abs(startX-endX);
                    }else{
                        if(start.getX() != end.getX()){
                            if((start.getDirection() == end.getDirection()) && start.getDirection() == 1){
//                                System.out.println("same direct");
                                distance += (warehouse.getNumberOfRows()-start.getIndex())+(warehouse.getNumberOfRows()-end.getIndex())+2;
                            }else{
//                                System.out.println("diff direct");
                                distance += start.getIndex()+end.getIndex();
                            }
                            distance += Math.abs(startX-endX);
                        }else{
                            distance += Math.abs(start.getY()-end.getY());
                        }
                    }
                }else{
//                    System.out.println(false);
                    distance += Math.abs(start.getY()-end.getY())+Math.abs(startX-endX);
                }

//                System.out.println(distance);
                distances.add(distance);
            }
            start.setDistances(distances);
        }
    }



    public ArrayList<Location> getDistance(){
        return map;
    }
}
