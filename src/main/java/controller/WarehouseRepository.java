package controller;

import model.entity.data.Location;
import model.entity.data.Node;
import usecase.Astar;
import usecase.GetWarehouseMap;

import java.util.ArrayList;

public class WarehouseRepository {

    ArrayList<Location> locations;

    public WarehouseRepository(int items){
        int rows = 5;

//        get locations
        GetWarehouseMap map = new GetWarehouseMap();
        locations = map.getLocationNodes(1,5,rows);

//        set search area
//        Astar astar = new Astar(map.getMaxY(),map.getMaxX());
////        create blocks
//        for (int i = 0; i < locations.size(); i++ ) {
//            Location loc = locations.get(i);
//            astar.setBlock(loc.getY(), loc.getX());
//            System.out.println(i+". "+loc.getY()+"-"+loc.getX());
//        }

//        for(Location block : map.getBlocks()){
//            astar.setBlock(block.getY(), block.getX());
//            System.out.println(block.getX()+" - "+block.getY());
//        }

//        find all destinations

//        int x1 = (1 & 0) == 0 ?  1 : -1;
//        int x2 = (5 & 1) == 0 ?  1 : -1;
//
//        astar.setInitialNode(new Node(locations.get(1).getY(), locations.get(1).getX()+x1));
//        astar.setFinalNode(new Node(locations.get(5).getY(), locations.get(5).getX()+x2));
//
//        System.out.println(locations.get(1).getX()+","+locations.get(1).getY());
//        System.out.println(locations.get(5).getX()+","+locations.get(5).getY());
//
//        List<Node> path = astar.findPath();
//        for (Node n: path){
//            System.out.println(n);
//        }
//        System.out.println(astar.findPath().size());



//        Astar astar = new Astar(map.getMaxY(),map.getMaxX());
////        create blocks
//        for (int i = 0; i < locations.size(); i++ ) {
//            Location loc = locations.get(i);
//            astar.setBlock(loc.getY(), loc.getX());
//            System.out.println(i+". "+loc.getY()+"-"+loc.getX());
//        }
//        int y1 = 0;
//        int y2 = 3;
//        Location s = locations.get(y1);
//        Location d = locations.get(y2);
//
//        int x1 = y1 == 0 ? 0 : (y1 & 1) == 0 ?  1 : -1;
//        int x2 = y2 == 0 ? 0 : (y2 & 1) == 0 ?  1 : -1;
//
//        astar.setInitialNode(new Node(s.getY(), s.getX()+x1));
//        astar.setFinalNode(new Node(d.getY(), d.getX()+x2));
//        System.out.println(s.getY()+"-"+(s.getX()+x1));
//        System.out.println(d.getY()+"-"+(d.getX()+x2));
//        System.out.println(astar.findPath().size());


        for (int i = 0; i < locations.size(); i++) {
            Location start = locations.get(i);
            ArrayList<Integer> distances = new ArrayList<Integer>();
            for (int j = 0; j < locations.size(); j++){
                Location destination = locations.get(j);

                Astar astar = new Astar(map.getMaxY(),map.getMaxX());
                for (int k = 0; k < locations.size(); k++ ) {
                    Location loc = locations.get(k);
                    astar.setBlock(loc.getY(), loc.getX());
//                    System.out.println(i+". "+loc.getY()+"-"+loc.getX());
                }

                int startX = i == 0 ? 0 : (i & 1) == 0 ?  1 : -1;
                int destinationX = j == 0 ? 0 : (j & 1) == 0 ?  1 : -1;

                astar.setInitialNode(new Node(start.getY(), start.getX()+startX));
                astar.setFinalNode(new Node(destination.getY(), destination.getX()+destinationX));
//                System.out.println(start.getY()+"-"+(start.getX()+startX));
//                System.out.println(destination.getY()+"-"+(destination.getX()+destinationX));
                int dist = astar.findPath().size();
//                System.out.println(dist);

                distances.add(dist);
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (IntuptedException e) {
//                    e.printStackTerrrace();
//                }
            }
            locations.get(i).setDistances(distances);
        }
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }
}
