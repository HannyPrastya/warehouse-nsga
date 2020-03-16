import algorithm.GAModule;
import algorithm.NSGAModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import helper.DatasetRepository;
import helper.Helpers;
import helper.SimilarityRepository;
import helper.WarehouseRepository;
import model.entity.Dataset;
import model.entity.Item;
import model.entity.Warehouse;
import model.objective.Similarity;
import usecase.CreateRandomItems;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        ObjectMapper mapper = new ObjectMapper();

        int[][] datasetList = {
            {50,200,5},
            {50,200,10},
            {50,400,5},
            {50,400,10},
            {100,200,5},
            {100,200,10},
            {100,400,5},
            {100,400,10},
            {200,200,5},
            {200,200,10},
            {200,400,5},
            {200,400,10},
            {500,200,5},
            {500,200,10},
            {500,400,5},
            {500,400,10}
        };
//        int[][] datasetList = {{10,200,6}};

//        ArrayList<Integer> temp = new ArrayList<>();
//        temp.add(2);
//        temp.add(46);
//        temp.add(55);
//        temp.add(64);
//        temp.add(83);
//        temp.add(120);
//        temp.add(136);
//        ACOModule aco = new ACOModule();
//        aco.setLocations(warehouseRepository.getLocations());
//        System.out.println(aco.calculateDistanceACO(temp));

//            ArrayList<Item> items = new CreateRandomItems(WarehouseRepository.getLocations().size()-1).getItems();
//            for (int i = 0; i < datasetList.length; i++) {
//                int[] list = datasetList[i];
//    //            get dataset
//                DatasetRepository datasetRepository = new DatasetRepository(items, list[0], list[1], list[2]);
//    //            set dataset
//                Dataset dataset = datasetRepository.getDataset();
//                dataset.setItems(items);
//
//                String json = mapper.writeValueAsString(dataset);
//
//                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
//                        new FileOutputStream("C:\\Users\\prast\\IdeaProjects\\warehouse-nsga\\src\\main\\resources\\"+list[0]+"-"+list[1]+"-"+list[2]+".json"), "utf-8"))) {
//                    writer.write(json);
//                }
//            }

        int nr = 5;
        for (int i = 0; i < datasetList.length; i++) {
            int[] list = datasetList[i];
            String filename = list[0]+"-"+list[1]+"-"+list[2]+".json";

            Dataset dataset = mapper.readValue(new File(Helpers.getResource(filename).getPath()), Dataset.class);
//            Dataset dataset = mapper.readValue(new File("/Users/hirito48/IdeaProjects/warehouse-nsga/src/main/resources/"+list[0]+"-"+list[1]+"-"+list[2]+".json"), Dataset.class);
//            DatasetRepository.setDataset(dataset);

            for (int j = 0; j < nr; j++) {
//                GAModule ga = new GAModule(dataset);
//                ga.start();

                NSGAModule nsga = new NSGAModule(dataset);
                nsga.start();
            }
        }

////        import dataset
//        String json = mapper.writeValueAsString(dataset);
//        System.out.println(json);

//        Dataset dataset = mapper.readValue(new File("/Users/hirito48/IdeaProjects/warehouse-nsga/src/main/resources/dataset-small.json"), Dataset.class);

//        NSGAModule nsga = new NSGAModule(dataset, warehouseRepository.getLocations());

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
