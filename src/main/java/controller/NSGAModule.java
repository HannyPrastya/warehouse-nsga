package controller;

import model.entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NSGAModule {
    int numberOfPopulation = 10;
    Dataset dataset;
    ArrayList<Location> locations;
    ArrayList<Solution> population = new ArrayList<>();
    ArrayList<Solution> offsprings = new ArrayList<>();
    ArrayList<Solution> parentAs;
    ArrayList<Solution> parentBs;

    Integer maxGeneration = 10000;
    int crossover = 70;
    Solution elite;

    public NSGAModule(Dataset dataset, ArrayList<Location> locations) throws CloneNotSupportedException {
        this.dataset = dataset;
        this.locations = locations;

        for (int i = 0; i < maxGeneration; i++) {
            if(i == 0) {
                for (int j = 0; j < numberOfPopulation; j++) {
                    Solution solution = new Solution();
                    solution.setChromosome(createChromosome(dataset.getNumberOfOrders()));
                    population.add(solution);
                }
            }else{
                population = new ArrayList<>();
                population.add(elite);

                for (int j = 0; j < numberOfPopulation-1; j++) {
                    Solution solution = new Solution();
                    solution.setChromosome(createChromosome(dataset.getNumberOfOrders()));
                    population.add(solution);
                }
            }
            selection();
            doOperators();
            calculateFitness();
            elitsm();
            System.out.print("{\"generation\":"+i+",\"fitness\":"+elite.getDistance()+"},");
        }
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
        ArrayList<Solution> all = population;
        all.addAll(offsprings);
        for (Solution solution: all) {
//            System.out.println("START SOLUTION");

//            arrange chromosome to schedule
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
//            System.out.println("total space "+solution.getSpace()+" total distance "+solution.getDistance());
        }
    }

    private void selection() throws CloneNotSupportedException {
        ArrayList<Integer> wheel = new ArrayList<>();
        calculateFitness();

        parentAs = new ArrayList<>();
        parentBs = new ArrayList<>();

        Collections.sort(population, new Comparator<Solution>() {
            public int compare(Solution elm1, Solution elm2) {
                return elm1.getDistance() - elm2.getDistance();
            }
        });

        Integer max = population.get(population.size() - 1).getDistance();
        Integer total = 0;

        for (int i = 0; i < population.size(); i++) {
            Solution sol = population.get(i);
            Integer x = max + 1 - sol.getDistance();
            total += x;
            for (int j = 0; j < x; j++) {
                wheel.add(i);
            }
        }

        for (int i = 0; i < population.size()/2; i++) {
            Integer rand = (int) Math.round((Math.random() * (total - 1)));
            parentAs.add(population.get(wheel.get(rand)).clone());
            Integer rand2 = (int) Math.round((Math.random() * (total - 1)));
            parentBs.add(population.get(wheel.get(rand2)).clone());
        }
    }

    private void doOperators() throws CloneNotSupportedException {
//        selection || random
//        System.out.println("number : "+population.size());
        offsprings = new ArrayList<>();
        Collections.shuffle(population);

        for (int i = 0; i < parentAs.size(); i++) {
            Solution offspring1 = parentAs.get(i).clone();
            Solution offspring2 = parentBs.get(i).clone();

//            population.get(i).printGens();
//            population.get(i+1).printGens();

            if((float) (i+1)/population.size()  <= (float) crossover/100){
//                System.out.println(i+" crossover");
                int start = (int) Math.round((Math.random() * offspring1.getChromosome().size()/2));
                int end = start + (int) (0.3 * offspring1.getChromosome().size());
//                System.out.println(start+" - "+end);
                for (int j = start; j < end; j++) {
                    int temp = offspring1.getChromosome().get(j);
                    offspring1.getChromosome().set(j, offspring2.getChromosome().get(j));
                    offspring2.getChromosome().set(j, temp);
                }
            }else{
//                System.out.println(i+" mutation");
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

//            population.get(i).printGens();
//            population.get(i+1).printGens();
//            offspring1.printGens();
//            offspring2.printGens();

            i++;
            offsprings.add(offspring1);
            offsprings.add(offspring2);
        }

        for (Solution offspring : offsprings) {
            ArrayList<Integer> IDs = new ArrayList<>();

            for (int i = 0; i < offspring.getChromosome().size(); i++) {
                IDs.add(i);
            }

            for (int i = 0; i < offspring.getChromosome().size(); i++) {
                Integer id = offspring.getChromosome().get(i);
                if(IDs.contains(id)){
                    IDs.remove(id);
                }else{
                    offspring.getChromosome().set(i, -1);
                }
            }
            Collections.shuffle(IDs);

            Integer j = 0;
            for (int i = 0; i < offspring.getChromosome().size() ; i++) {
                Integer id = offspring.getChromosome().get(i);

                if(id == -1){
                    offspring.getChromosome().set(i, IDs.get(j));
                    j++;
                }
            }
        }
    }

    public void elitsm(){
        ArrayList<Solution> all = population;
        all.addAll(offsprings);

        Collections.sort(all, new Comparator<Solution>() {
            public int compare(Solution elm1, Solution elm2) {
                return elm1.getDistance() - elm2.getDistance();
            }
        });

        elite = all.get(0);
    }

}
