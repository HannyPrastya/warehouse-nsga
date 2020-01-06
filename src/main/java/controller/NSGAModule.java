package controller;

import model.entity.*;

import java.util.ArrayList;
import java.util.Collections;

public class NSGAModule {
    int numberOfPopulation = 100;
    Dataset dataset;
    ArrayList<Location> locations;
    ArrayList<Solution> population = new ArrayList<>();
    ArrayList<Solution> offsprings = new ArrayList<>();
    int crossover = 70;
    int mutation = 30;

    public NSGAModule(Dataset dataset, ArrayList<Location> locations){
        this.dataset = dataset;
        this.locations = locations;

//        create chromosome
        for (int i = 0; i < numberOfPopulation ; i++) {
            Solution solution = new Solution();
            solution.setChromosome(createChromosome(dataset.getNumberOfOrders()));
            population.add(solution);
//            System.out.println(population.get(i));;
        }
//        calculate fitnes
//        calculateFitness();
//        random selection
        randomSelection();
        doOperators();
//        crossover
//        mutation

//        NDS

    }

    private ArrayList<Integer> createChromosome(int numberOfOrders){
        ArrayList<Integer> chromosome = new ArrayList<>();
        for (int i = 0; i < numberOfOrders ; i++) {
            chromosome.add(i);
        }
        Collections.shuffle(chromosome);
        return chromosome;
    }

    private void calculateFitness(){
        for (Solution solution: population) {
//            System.out.println("START SOLUTION");
//            arrange chromsome to schedule
            ArrayList<Batch> batches = new ArrayList<>();
            for (int orderID : solution.getChromosome()) {
                Order order = dataset.getOrders().get(orderID);

                Boolean flag = false;
                for (Batch batch: batches) {
                    int total = (batch.getTotalWeight() + order.getTotalWeight());
                    if(total <= dataset.getCapacity()){
                        batch.addOrder(order);
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    Batch batch = new Batch();
                    batch.addOrder(order);
                    batches.add(batch);
                }
            }

            solution.setBatches(batches);
            int distance = 0;
            for (Batch batch: solution.getBatches()) {
//                objective 1
//                do ant colony
                int start = 0;
                for (int i = 0; i < batch.getIDs().size(); i++) {
                    int end = batch.getIDs().get(i);
                    distance += locations.get(start).getDistances().get(end);
                    start = end;
                }
//                objective 2
//                int currentSpace = solution.getSpace();
            }

            solution.setDistance(distance);
            solution.setSpace(solution.getBatches().size());

//            System.out.println("END SOLUTION");
            System.out.println("total space "+solution.getSpace()+" total distance "+solution.getDistance());
        }
    }

    private void randomSelection(){
//        for (int i = 0; i < population.size() ; i++) {
//            parents.add(i);
//        }
//        Collections.shuffle(parents);
    }

    private void doOperators(){
//        selection || random
        Collections.shuffle(population);
        for (int i = 0; i < population.size(); i++) {
            Solution offspring1 = population.get(i);
            Solution offspring2 = population.get(i+1);
            
            if((float) (i+1)/population.size()  <= (float) crossover/100){
                System.out.println(i+" crossover");
                int start = (int) Math.round((Math.random() * offspring1.getChromosome().size()/2));
                int end = start + (int) (0.3 * offspring1.getChromosome().size());
                for (int j = start; j < end; j++) {
                    int temp = offspring1.getChromosome().get(j);
                    offspring1.getChromosome().set(j, offspring2.getChromosome().get(j));
                    offspring2.getChromosome().set(j, temp);
                }
            }else{
                System.out.println(i+" mutation");
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
            i++;
            offsprings.add(offspring1);
            offsprings.add(offspring2);
        }

        for (Solution offspring : offsprings) {
            ArrayList<Integer> IDs = new ArrayList<>();
//            ArrayList<Integer> correctIDs = new ArrayList<>();

            for (int i = 0; i < offspring.getChromosome().size(); i++) {
                IDs.add(i);
            }

            System.out.println("size ids "+IDs.size());
            System.out.println("size offspring "+offspring.getChromosome().size());

            Integer j = 0;
            for (int i = 0; i < offspring.getChromosome().size(); i++) {
                Integer id = offspring.getChromosome().get(i);
                if(IDs.contains(id)){
                    IDs.remove(id);
                }else{
                    j++;
                    System.out.println("ZERO = "+j);
                    offspring.getChromosome().set(i, 0);
                }
            }
            Collections.shuffle(IDs);

            System.out.println("number IDS "+IDs.size());
//            j = 0;

            for (int i = 0; i < offspring.getChromosome().size() ; i++) {
                Integer id = offspring.getChromosome().get(i);

                if(id == 0){
//                    j++;
//                    System.out.println("ZERO = "+j);
//                    System.out.println(IDs.size());
                    offspring.getChromosome().set(i, IDs.get(0));
                    IDs.remove(0);
                }
            }
        }
    }
}
