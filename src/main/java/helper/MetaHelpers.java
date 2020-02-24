package helper;

import model.entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class MetaHelpers {

    public static ArrayList<Integer> createChromosome(int numberOfOrders, int maxNumberOfBatches){
        ArrayList<Integer> chromosome = new ArrayList<>();
        for (int i = 0; i < numberOfOrders ; i++) {
            int batchNumber = Helpers.randInt(0, maxNumberOfBatches-1);
            chromosome.add(batchNumber);
        }
        Collections.shuffle(chromosome);
        return chromosome;
    }

    public static ArrayList<Solution> createNewPopulation(int numberOfPopulation, int size, int maxNumberOfBatches){
        ArrayList<Solution> population = new ArrayList<>();
        for (int j = 0; j < numberOfPopulation; j++) {
            Solution solution = new Solution();
            solution.setMaxNumberOfBatches(maxNumberOfBatches);
            solution.setChromosome(createChromosome(size, maxNumberOfBatches));
            population.add(solution);
        }

        return population;
    }

    public static void calculateFitness(Solution solution, Dataset dataset, ArrayList<Location> locations, ArrayList<ArrayList<Double>> matrix, Map<String, Double[]> memory) throws CloneNotSupportedException {
        ArrayList<Batch> batches = convertSolutionToBatches(solution, dataset);
        solution.setBatches(batches);

        int distance = 0;
        double similarity = 0;
        for (Batch batch: batches) {
            ArrayList<Integer> IDs = new ArrayList<>(batch.getIDs());
            Collections.sort(IDs);
            String ids = IDs.toString();
            if(!memory.containsKey(ids)){
                Double d = 0.0;
                Double s = 0.0;
                for (int i = 0; i < batch.getIDs().size(); i++) {
                    int id = IDs.get(i);
                    for (int j = i+1; j < batch.getIDs().size(); j++) {
                        int id2 = IDs.get(j);
                        d += locations.get(id).getDistances().get(id2);
                        s += matrix.get(id).get(id2);
                    }
                }
                Double[] r = new Double[2];
                r[0] = d;
                r[1] = s;
                memory.put(ids, r);
                distance += r[0];
                similarity += r[1];
            }else{
                distance += memory.get(ids)[0];
                similarity += memory.get(ids)[1];
            }
        }

        solution.setDistance(distance);
        solution.setSimilarity(similarity);

//        System.out.println(solution.getObjectiveValues());
    }

    public static ArrayList<Batch> convertSolutionToBatches(Solution solution, Dataset dataset) throws CloneNotSupportedException {
//        System.out.println(solution.getChromosome().size()+" - "+solution.getChromosome());
        ArrayList<Batch> batches = new ArrayList<>();

        for (int i = 0; i < solution.getMaxNumberOfBatches(); i++) {
            batches.add(new Batch());
        }

        for (int orderID = 0; orderID < solution.getChromosome().size(); orderID++) {
            Order order = dataset.getOrders().get(orderID);
            int batchNumber = solution.getChromosome().get(orderID);
            batches.get(batchNumber).addOrder(order);
        }
//
//        for (Batch b: batches) {
//            System.out.println(b.getIDs());
////            System.out.println(b.getOrders());
//        }

        for (Batch batch: batches) {
            while(batch.getTotalWeight() > dataset.getCapacity()){
                Order order = batch.removeAndGetOrder();
                for (Batch batchSearch: batches) {
                    if(dataset.getCapacity() - batchSearch.getTotalWeight() > order.getTotalWeight()){
                        batchSearch.addOrder(order);
                        break;
                    }
                }
            }
            batch.refreshItems();
        }
        return batches;
    }

    public static int getMaxNumberofBatches(Dataset ds){
        return (int) Math.ceil((double) ds.getTotalOfWeight()/ds.getCapacity());
    }
}
