package algorithm;

import helper.*;
import model.entity.Chromosome;
import model.entity.Dataset;
import model.entity.Location;
import model.entity.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NSGAModule {

    private static final int DOMINANT = 1;
    private static final int INFERIOR = 2;
    private static final int NON_DOMINATED = 3;

    private Dataset dataset;
    private ArrayList<Location> locations;
    private ArrayList<Solution> population;
    private int numberOfGeneration = 10000;
    private int numberOfPopulation = 10;
    private ArrayList<ArrayList<Double>> matrixOfSimilarity;
    private ArrayList<Solution> parentAs;
    private ArrayList<Solution> parentBs;
    private ArrayList<Solution> offsprings = new ArrayList<>();
    private int crossover = 70;
    Map<String, Integer> memory = new HashMap<String, Integer>();
    Map<String, Integer> memoryChromosome = new HashMap<String, Integer>();
    Map<String, int[]> fitnessMemory = new HashMap<String, int[]>();
    private ACOModule aco;
    private int shortestDistance = 999999999;
    int currentGeneration = 0;

    public NSGAModule(Dataset dataset) {
        this.dataset = dataset;
        this.matrixOfSimilarity = new SimilarityRepository(dataset).getMatrix();
        this.locations = WarehouseRepository.getLocations();
        aco = new ACOModule();
        aco.setLocations(locations);
    }

    public void start() throws CloneNotSupportedException {
        this.population = MetaHelpers.createNewPopulation(numberOfPopulation, dataset.getOrders().size(),dataset.getMaxNumberofBatches());
        calculateFitness();
        preparePopulation();
        calculateDistances();

        while (currentGeneration < numberOfGeneration){
            ArrayList<Solution> newPopulation = MetaHelpers.createNewPopulation(numberOfPopulation, dataset.getOrders().size(),dataset.getMaxNumberofBatches());
            population.addAll(newPopulation);
            population.addAll(offsprings);
            calculateFitness();
            preparePopulation();

            population.forEach(solution -> {
                System.out.println(solution.getObjectiveValues());
            });
            calculateDistances();
            System.out.println(currentGeneration+". "+shortestDistance);

            population = getChildFromCombinedPopulation();
            doOperators();

            ++currentGeneration;
        }
    }

    private void calculateDistances(){
        int rank1 = 0;
        int mustBeCalc = 0;
        for (Solution sol: population) {
            if(sol.getRank() == 1){
                String ids = sol.getChromosome().toString();
                int sd;
                if(!memory.containsKey(ids)){
                    aco.setBatches(sol.getBatches());
                    aco.calculateDistance();
                    int shortestDistance = aco.getDistance();
                    memory.put(ids, shortestDistance);
                    sd = shortestDistance;
                    ++mustBeCalc;
                }else{
                    sd = memory.get(ids);
                }
                if(sd < this.shortestDistance){
                    this.shortestDistance = sd;
                }
                ++rank1;
            }
        }

        System.out.println(rank1+" - "+mustBeCalc);
    }

    private void doOperators() throws CloneNotSupportedException {
        selection();
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

    public void selection(){
        parentAs = new ArrayList<>();
        parentBs = new ArrayList<>();
        ArrayList<Solution> wheel = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            Solution sol = population.get(i);
            int x = population.size() - i;
            for (int j = 0; j < x; j++) {
                wheel.add(sol);
            }
        }

        for (int i = 0; i < population.size()/2; i++) {
            int rand = Helpers.randInt(0, wheel.size()-1);
            parentAs.add(wheel.get(rand));
            wheel.removeIf(solution -> solution.equals(wheel.get(rand)));

            int rand2 = Helpers.randInt(0, wheel.size()-1);
            parentBs.add(wheel.get(rand2));
            wheel.removeIf(solution -> solution.equals(wheel.get(rand2)));
        }
    }

    public void calculateFitness() throws CloneNotSupportedException {
        for (Solution solution: population) {
            MetaHelpers.calculateFitness(solution, this.dataset, this.locations, this.matrixOfSimilarity, this.fitnessMemory);
        }

        for (Solution solution: offsprings) {
            MetaHelpers.calculateFitness(solution, this.dataset, this.locations, this.matrixOfSimilarity, this.fitnessMemory);
        }
    }

    public ArrayList<Solution> preparePopulation() {
        Solution[] populace = population.toArray(new Solution[population.size()]);

        fastNonDominatedSort(populace);
        crowdingDistanceAssignment(populace);

        Service.randomizedQuickSortForRank(population, 0, populace.length - 1);
        return population;
    }

    public ArrayList<Solution> getChildFromCombinedPopulation() {
        int lastNonDominatedSetRank = population.get(population.size() - 1).getRank();
        ArrayList<Solution> populace = new ArrayList<>();

        Service.sortForCrowdingDistance(population, lastNonDominatedSetRank);

        for(int i = 0; i < numberOfPopulation; i++){
            populace.add(population.get(i));
        }

        return populace;
    }

    private static void fastNonDominatedSort(final Solution[] populace) {
        for(Solution chromosome : populace) chromosome.reset();
        for(int i = 0; i < populace.length - 1; i++) {

            for(int j = i + 1; j < populace.length; j++) {
                switch(dominates(populace[i], populace[j])) {
                    case DOMINANT:
                        populace[i].setDominatedSolutions(populace[j]);
                        populace[j].incrementDominationCount(1);
                        break;
                    case INFERIOR:
                        populace[i].incrementDominationCount(1);
                        populace[j].setDominatedSolutions(populace[i]);
                        break;
                    case NON_DOMINATED: break;
                }
            }

            if(populace[i].getDominationCount() == 0) populace[i].setRank(1);
        }

        if(populace[populace.length - 1].getDominationCount() == 0) populace[populace.length - 1].setRank(1);

        for(int i = 0; i < populace.length; i++) {
            for(Solution chromosome : populace[i].getDominatedSolutions()) {
                chromosome.incrementDominationCount(-1);
                if(chromosome.getDominationCount() == 0) chromosome.setRank(populace[i].getRank() + 1);
            }
        }
    }

    private static void crowdingDistanceAssignment(final Solution[] nondominatedChromosomes) {
        int size = nondominatedChromosomes.length;
        for(int i = 0; i < 2; i++) {
            Service.sortAgainstObjective(nondominatedChromosomes, i);

            nondominatedChromosomes[0].setCrowdingDistance(Double.MAX_VALUE);
            nondominatedChromosomes[size - 1].setCrowdingDistance(Double.MAX_VALUE);

            double maxObjectiveValue = Service.selectMaximumObjectiveValue(nondominatedChromosomes, i);
            double minObjectiveValue = Service.selectMinimumObjectiveValue(nondominatedChromosomes, i);

            for(int j = 1; j < size - 1; j++) if(nondominatedChromosomes[j].getCrowdingDistance() < Double.MAX_VALUE) nondominatedChromosomes[j].setCrowdingDistance(
                    nondominatedChromosomes[j].getCrowdingDistance() + (
                            (nondominatedChromosomes[j + 1].getObjectiveValues().get(i) - nondominatedChromosomes[j - 1].getObjectiveValues().get(i)) / (maxObjectiveValue - minObjectiveValue)
                    )
            );
        }
    }

    private static int dominates(final Solution chromosome1, final Solution chromosome2) {
        if(isDominant(chromosome1, chromosome2)) return DOMINANT;
        else if(isDominant(chromosome2, chromosome1)) return INFERIOR;
        else return NON_DOMINATED;
    }

    private static boolean isDominant(final Solution chromosome1, final Solution chromosome2) {
        boolean isDominant = true;
        boolean atleastOneIsLarger = false;

        for(int i = 0; i < Configuration.numberOfObjetives; i++) {
            if(Configuration.objTypes[i]){
                if(chromosome1.getObjectiveValues().get(i) > chromosome2.getObjectiveValues().get(i)) {
                    isDominant = false;
                    break;
                } else if(!atleastOneIsLarger && (chromosome1.getObjectiveValues().get(i) < chromosome2.getObjectiveValues().get(i))) atleastOneIsLarger = true;
            }else {
                if(chromosome1.getObjectiveValues().get(i) < chromosome2.getObjectiveValues().get(i)) {
                    isDominant = false;
                    break;
                } else if(!atleastOneIsLarger && (chromosome1.getObjectiveValues().get(i) > chromosome2.getObjectiveValues().get(i))) atleastOneIsLarger = true;
            }
        }

        return isDominant && atleastOneIsLarger;
    }
}
