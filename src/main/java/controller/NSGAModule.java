package controller;

import model.entity.*;

import java.util.ArrayList;
import java.util.Collections;

public class NSGAModule {
    int numberOfPopulation = 100;
    Dataset dataset;
    ArrayList<Location> locations;
    ArrayList<Solution> population = new ArrayList<>();

    public NSGAModule(Dataset dataset, ArrayList<Location> locations){
        dataset = dataset;

//        create chromosome
        for (int i = 0; i < numberOfPopulation ; i++) {
            Solution solution = new Solution();
            solution.setChromosome(createChromosome(dataset.getNumberOfOrders()));
            population.add(solution);
//            System.out.println(population.get(i));;
        }
//        calculate fitnes
        calculateFitness();
//        NDS
//        selection
//        crossover
//        mutation
//

    }

    private ArrayList<Integer> createChromosome(int numberOfOrders){
        ArrayList<Integer> chromosome = new ArrayList<>();
        for (int i = 0; i < numberOfOrders ; i++) {
            chromosome.add(i+1);
        }
        Collections.shuffle(chromosome);
        return chromosome;
    }

    private void calculateFitness(){
        ArrayList<Batch> notFullBatches = new ArrayList<>();
        for (Solution solution: population) {
//            arrange chromsome to schedule
            for (int orderID : solution.getChromosome()) {
                Order order = dataset.getOrders().get(orderID);
                if(notFullBatches.size() == 0){
                    Batch batch = new Batch();

                    if(dataset.getCapacity() <= dataset.getCapacity()){
                        batch.addOrder(dataset.getOrders().get(orderID));
                    }else{

                    }
                }else{
                    for (Batch batch: notFullBatches) {
                    }
                }
            }


//            objective 1

//            objective 2

        }
    }
}
