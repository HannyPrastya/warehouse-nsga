import controller.DatasetRepository;
import controller.NSGAModule;
import controller.WarehouseRepository;
import model.entity.Dataset;

public class Main {

    public static void main(String[] args) {
        int capacity = 100;
        int numberOfOrder = 100;
        int numberOfItemPerOrder = 5;

//        warehouse
        WarehouseRepository warehouseRepository = new WarehouseRepository();

//        get dataset
        DatasetRepository datasetRepository = new DatasetRepository(warehouseRepository.getLocations().size()-1, numberOfOrder, capacity, numberOfItemPerOrder);

//        set dataset
        Dataset dataset = datasetRepository.getDataset();

//       chromsome
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
