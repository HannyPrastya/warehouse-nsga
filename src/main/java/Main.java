import controller.WarehouseRepository;
import model.entity.data.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
        int population = 100;
        int capacity = 20;
        int order = 100;
        int items = 5;

        WarehouseRepository warehouse = new WarehouseRepository(items);


        try (PrintWriter writer = new PrintWriter(new File("D:/test.csv"))) {

            StringBuilder sb = new StringBuilder();

            for (Location loc: warehouse.getLocations()) {
                for (int i = 0; i < loc.getDistances().size(); i++) {
                    Integer distance = loc.getDistances().get(i);
                    sb.append(loc.getX()+", "+loc.getY()+";");
                    sb.append(warehouse.getLocations().get(i).getX()+", "+warehouse.getLocations().get(i).getY()+";");
                    sb.append(distance+";");
                    sb.append('\n');
                }
            }
            writer.write(sb.toString());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
