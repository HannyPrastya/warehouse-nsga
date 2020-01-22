import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.DatasetRepository;
import controller.NSGAModule;
import controller.WarehouseRepository;
import model.entity.Dataset;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        int capacity = 100;
        int numberOfOrder = 20;
        int numberOfItemPerOrder = 5;

        System.out.println(96/10);
//        warehouse
        WarehouseRepository warehouseRepository = new WarehouseRepository();

//        get dataset
//        DatasetRepository datasetRepository = new DatasetRepository(warehouseRepository.getLocations().size()-1, numberOfOrder, capacity, numberOfItemPerOrder);

//        set dataset
//        Dataset dataset = datasetRepository.getDataset();

//        import dataset
        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(dataset);
//        System.out.println(json);
        Dataset dataset = mapper.readValue(new File("/Users/hirito48/IdeaProjects/warehouse-nsga/src/main/resources/dataset.json"), Dataset.class);

        NSGAModule nsga = new NSGAModule(dataset, warehouseRepository.getLocations());

//        try (PrintWriter writer = new PrintWriter(new File("/Users/hirito48/Desktop/test.csv"))) {
//
//            StringBuilder sb = new StringBuilder();
//
//            for (Location loc: warehouse.getLocations()) {
//                for (int i = 0; i < loc.getDistances().size(); i++) {
//                    Integer distance = loc.getDistances().get(i);
//                    sb.append(loc.getX()+", "+loc.getY()+";");
//                    sb.append(warehouse.getLocations().get(i).getX()+", "+warehouse.getLocations().get(i).getY()+";");
//                    sb.append(distance+";");
//                    sb.append('\n');
//                }
//            }
//            writer.write(sb.toString());
//
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
    }
}
