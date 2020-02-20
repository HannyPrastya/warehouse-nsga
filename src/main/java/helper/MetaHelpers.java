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
            int batchNumber = (int) Math.round((Math.random() * (maxNumberOfBatches-1)));
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

    public static void calculateFitness(Solution solution, Dataset dataset, ArrayList<Location> locations, ArrayList<ArrayList<Double>> matrix, Map<String, int[]> memory) throws CloneNotSupportedException {
        ArrayList<Batch> batches = convertSolutionToBatches(solution, dataset);
        solution.setBatches(batches);

        int distance = 0;
        double similarity = 0;
        for (Batch batch: batches) {
            String ids = batch.getIDs().toString();
            Collections.sort(batch.getIDs());
            if(!memory.containsKey(ids)){
                int d = 0;
                int s = 0;
                for (int i = 0; i < batch.getIDs().size(); i++) {
                    int id = batch.getIDs().get(i);
                    for (int j = i+1; j < batch.getIDs().size(); j++) {
                        int id2 = batch.getIDs().get(j);
                        d += locations.get(id).getDistances().get(id2);
                        s += matrix.get(id).get(id2);
                    }
                }
                int[] r = new int[2];
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
    }

    private static ArrayList<Batch> convertSolutionToBatches(Solution solution, Dataset dataset) throws CloneNotSupportedException {
        ArrayList<Batch> batches = new ArrayList<>();

        for (int i = 0; i < solution.getMaxNumberOfBatches(); i++) {
            batches.add(new Batch());
        }

        for (int orderID = 0; orderID < solution.getChromosome().size(); orderID++) {
            Order order = dataset.getOrders().get(orderID);
            int batchNumber = solution.getChromosome().get(orderID);
            batches.get(batchNumber).addOrder(order);
        }

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
}
