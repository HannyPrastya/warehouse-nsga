package algorithm;

import helper.*;
import model.entity.*;

import java.util.*;

public class GAModule {
    Dataset dataset;
    ArrayList<Location> locations;
    ArrayList<Solution> population = new ArrayList<>();
    ArrayList<Solution> paretoSolutions = new ArrayList<>();
    ArrayList<Solution> offsprings = new ArrayList<>();
    ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
    Map<String, Double[]> fitnessMemory = new HashMap<String, Double[]>();
    Map<String, Double[]> memoryChromosome = new HashMap<String, Double[]>();

    ArrayList<Solution> parentAs;
    ArrayList<Solution> parentBs;
    int maxNumberOfBatches;

    int bestFitness = 999999999;
    int maxGeneration = 100;
    int numberOfPopulation = 10;
    int currentGeneration = 0;
    int crossover = 70;
    Solution elite;

    public GAModule(Dataset dataset) {
        this.dataset = dataset;
        this.locations = WarehouseRepository.getLocations();
        this.matrix = new SimilarityRepository(dataset).getMatrix();

        maxNumberOfBatches = MetaHelpers.getMaxNumberofBatches(dataset);
    }

    public void start()throws CloneNotSupportedException{
        while (currentGeneration < maxGeneration){
            if(currentGeneration == 0) {
                population = MetaHelpers.createNewPopulation(numberOfPopulation, dataset.getOrders().size(), maxNumberOfBatches);
            }else{
                population = new ArrayList<>();
                population.add(elite);

                for (int j = 0; j < numberOfPopulation-1; j++) {
                    Solution solution = MetaHelpers.createNewSolution(dataset.getOrders().size(), maxNumberOfBatches);
                    population.add(solution);
                }
            }
            selection();
            doOperators();
            calculateFitness();
            getElitsm();
            System.out.println("Current generation : "+currentGeneration);
//            population.forEach(solution -> {
//                System.out.println(solution.getObjectiveValues());;
//            });
//            System.out.println(elite.getObjectiveValues());
            ++currentGeneration;
        }
        System.out.println(bestFitness);
    }

    private void calculateFitness() throws CloneNotSupportedException {
        for (Solution solution: population) {
            calculatePerSolution(solution);
        }
        for (Solution solution: offsprings) {
            calculatePerSolution(solution);
        }
    }

    private void calculatePerSolution(Solution solution) throws CloneNotSupportedException{
        if(memoryChromosome.containsKey(solution.getChromosome().toString())){
            ArrayList<Batch> batches = MetaHelpers.convertSolutionToBatches(solution, dataset);
            solution.setBatches(batches);

            solution.setDistance(memoryChromosome.get(solution.getChromosome().toString())[0]);
            solution.setSimilarity(memoryChromosome.get(solution.getChromosome().toString())[1]);
        }else{
            MetaHelpers.calculateFitness(solution, this.dataset, this.locations, this.fitnessMemory);

            Double[] r = new Double[2];
            r[0] = solution.getObjectiveValues().get(0);
            r[1] = solution.getObjectiveValues().get(1);
            memoryChromosome.put(solution.getChromosome().toString(), r);
        }
//        ArrayList<Batch> batches = convertSolutionToBatches(solution);
//        solution.setBatches(batches);
//        double x = 0;
//        for (Batch batch: batches) {
//            for (int i = 0; i < batch.getIDs().size(); i++) {
//                Integer[] IDs = batch.getIDs().keySet().toArray(new Integer[0]);
//                int id = IDs[i];
//                for (int j = i+1; j < batch.getIDs().size(); j++) {
//                    Integer[] IDs2 = batch.getIDs().keySet().toArray(new Integer[0]);
//                    int id2 = IDs2[j];
//                    if(Configuration.activeObjetive == 0){
//                        x += locations.get(id).getDistances().get(id2);
//                    }else if(Configuration.activeObjetive == 1){
//                        x += matrix.get(id).get(id2);
//                    }
//                }
//            }
//        }
//        solution.getObjectiveValues().set(Configuration.activeObjetive, x);
    }

    private void selection() throws CloneNotSupportedException {
        calculateFitness();
        parentAs = new ArrayList<>();
        parentBs = new ArrayList<>();

        population.sort(new Comparator<Solution>() {
            public int compare(Solution elm1, Solution elm2) {
                if(Configuration.objTypes[Configuration.activeObjetive]){
                    return Helpers.sortDoubleAsc(elm1.getObjectiveValues().get(Configuration.activeObjetive), elm2.getObjectiveValues().get(Configuration.activeObjetive));
                }else{
                    return Helpers.sortDoubleDesc(elm1.getObjectiveValues().get(Configuration.activeObjetive), elm2.getObjectiveValues().get(Configuration.activeObjetive));
                }
            }
        });

        ArrayList<Solution> wheel = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < population.size(); i++) {
            Solution sol = population.get(i);
            if(Configuration.objTypes[Configuration.activeObjetive]){
                total += sol.getObjectiveValues().get(Configuration.activeObjetive);
            }else{
                total += 1 / sol.getObjectiveValues().get(Configuration.activeObjetive);
            }
        }

        for (int i = 0; i < population.size(); i++) {
            Solution sol = population.get(i);
            int x;
            if(Configuration.activeObjetive == 0){
                x = (int) Math.round(sol.getObjectiveValues().get(Configuration.activeObjetive)/total*100);
            }else{
                x = (int) Math.round((1/sol.getObjectiveValues().get(Configuration.activeObjetive))/total*100);
            }

            for (int j = 0; j < x; j++) {
                wheel.add(sol);
            }
        }

        for (int i = 0; i < population.size()/2; i++) {
            int rand = Helpers.randInt(0, wheel.size()-1);
            parentAs.add(wheel.get(rand));
            wheel.removeIf(solution -> solution.equals(wheel.get(rand)));
            int x = wheel.size() - 1;
            int rand2 = Helpers.randInt(0, x);
            parentBs.add(wheel.get(rand2));
            wheel.removeIf(solution -> solution.equals(wheel.get(rand2)));
        }
    }

    private void doOperators() throws CloneNotSupportedException {
        offsprings = new ArrayList<>();

        for (int i = 0; i < parentAs.size(); i++) {
            Solution offspring1 = parentAs.get(i).clone();
            Solution offspring2 = parentBs.get(i).clone();

            if((float) (i+1)/parentAs.size()  <= (float) crossover/100){
                int start = (int) Math.round((Math.random() * offspring1.getChromosome().size()/2));
                int end = start + (int) (0.3 * offspring1.getChromosome().size());
                for (int j = start; j < end; j++) {
                    int temp = offspring1.getChromosome().get(j);
                    offspring1.getChromosome().set(j, offspring2.getChromosome().get(j));
                    offspring2.getChromosome().set(j, temp);
                }
            }else{
                ArrayList<Integer> list = new ArrayList<>();
                for (int j = 0; j < offspring1.getChromosome().size(); j++) {
                    list.add(j);
                }

                for (int j = 0; j < offspring1.getChromosome().size()*0.3; j++) {
                    Collections.shuffle(list);
                    Integer rand = list.get(0);
                    int temp = offspring1.getChromosome().get(rand);
                    offspring1.getChromosome().set(rand, offspring2.getChromosome().get(rand));
                    offspring2.getChromosome().set(rand, temp);
                }
            }

            offsprings.add(offspring1);
            offsprings.add(offspring2);
        }
    }

//    private ArrayList<Batch> convertSolutionToBatches(Solution solution) throws CloneNotSupportedException {
//        ArrayList<Batch> batches = new ArrayList<>();
//
//        for (int i = 0; i < maxNumberOfBatches; i++) {
//            batches.add(new Batch());
//        }
//
//        for (int orderID = 0; orderID < solution.getChromosome().size(); orderID++) {
//            Order order = dataset.getOrders().get(orderID);
//            int batchNumber = solution.getChromosome().get(orderID);
//            batches.get(batchNumber).addOrder(order);
//        }
//
//        for (Batch batch: batches) {
//            while(batch.getTotalWeight() > dataset.getCapacity()){
//                Order order = batch.removeAndGetOrder();
//                for (Batch batchSearch: batches) {
//                    if(dataset.getCapacity() - batchSearch.getTotalWeight() > order.getTotalWeight()){
//                        batchSearch.addOrder(order);
//                        break;
//                    }
//                }
//            }
//        }
//
//        return batches;
//    }

    public void getElitsm() throws CloneNotSupportedException {
        ArrayList<Solution> all = population;
        all.addAll(offsprings);

        all.sort(new Comparator<Solution>() {
            public int compare(Solution elm1, Solution elm2) {
                if(Configuration.objTypes[Configuration.activeObjetive]){
                    return Helpers.sortDoubleAsc(elm1.getObjectiveValues().get(Configuration.activeObjetive), elm2.getObjectiveValues().get(Configuration.activeObjetive));
                }else{
                    return Helpers.sortDoubleDesc(elm1.getObjectiveValues().get(Configuration.activeObjetive), elm2.getObjectiveValues().get(Configuration.activeObjetive));
                }
            }
        });

        for (Solution solution : all) {
            System.out.println(solution.getObjectiveValues());
        }

        Solution tempElite = all.get(0);

        ACOModule acoModule = new ACOModule();
        acoModule.setBatches(tempElite.getBatches());
        acoModule.setLocations(locations);
        acoModule.calculateDistance();
        if(bestFitness > acoModule.getDistance()){
            elite = tempElite;
//            elite.setDistance(acoModule.getDistance());
            bestFitness = acoModule.getDistance();
        }
//        System.out.println(bestFitness);
    }

}
