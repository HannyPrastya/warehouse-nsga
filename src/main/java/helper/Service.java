package helper;

import model.entity.Solution;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Service {
    public static void randomizedQuickSortForRank(final List<Solution> populace, final int head, final int tail) {

        if(head < tail) {

            int pivot = Service.randomizedPartitionForRank(populace, head, tail);

            Service.randomizedQuickSortForRank(populace, head, pivot - 1);
            Service.randomizedQuickSortForRank(populace, pivot + 1, tail);
        }
    }

    public static void sortForCrowdingDistance(final List<Solution> populace, final int lastNonDominatedSetRank) {

        int rankStartIndex = -1;
        int rankEndIndex = -1;

        for(int i = 0; i < populace.size(); i++)
            if((rankStartIndex < 0) && (populace.get(i).getRank() == lastNonDominatedSetRank)) rankStartIndex = i;
            else if((rankStartIndex >= 0) && (populace.get(i).getRank() == lastNonDominatedSetRank)) rankEndIndex = i;

        Service.randomizedQuickSortForCrowdingDistance(populace, rankStartIndex, rankEndIndex);
    }

    public static void sortAgainstObjective(final Solution[] populace, int objectiveIndex) {
        Service.randomizedQuickSortAgainstObjective(populace, 0, populace.length - 1, objectiveIndex);
    }

    public static double selectMaximumObjectiveValue(final Solution[] populace, int objectiveIndex) {

        double result = populace[0].getObjectiveValues().get(objectiveIndex);

        for(Solution chromosome : populace) if(chromosome.getObjectiveValues().get(objectiveIndex) > result) result = chromosome.getObjectiveValues().get(objectiveIndex);

        return result;
    }

    public static double selectMinimumObjectiveValue(final Solution[] populace, int objectiveIndex) {

        double result = populace[0].getObjectiveValues().get(objectiveIndex);

        for(Solution chromosome : populace) if(chromosome.getObjectiveValues().get(objectiveIndex) < result) result = chromosome.getObjectiveValues().get(objectiveIndex);

        return result;
    }
//
//    /**
//     * this method decodes the genetic code that is represented as a string of binary values, converted into
//     * decimal value.
//     *
//     * @param   geneticCode     the genetic code as an array of Allele. Refer Allele.java for more information
//     * @return                  the decimal value of the corresponding binary string.
//     */
//    private static double decodeGeneticCode(final Allele[] geneticCode) {
//
//        double value = 0;
//        String binaryString = "";
//
//        for(Allele bit : geneticCode) binaryString += bit.getGene() ? "1" : "0";
//        for(int i = 0; i < binaryString.length(); i++)
//            if(binaryString.charAt(i) == '1')
//                value += Math.pow(2, binaryString.length() - 1 - i);
//
//        return value;
//    }
//
//    /**
//     * an implementation of min-max normalization
//     *
//     * @param   value   the value that is to be normalized
//     * @return          the normalized value
//     */
//    private static double minMaxNormalization(final double value) {
//        return (((value - Configuration.ACTUAL_MIN) / (Configuration.ACTUAL_MAX - Configuration.ACTUAL_MIN)) * (Configuration.NORMALIZED_MAX - Configuration.NORMALIZED_MIN)) + Configuration.NORMALIZED_MIN;
//    }

    private static int randomizedPartitionForRank(final List<Solution> populace, final int head, final int tail) {

        Service.swapForRank(populace, head, ThreadLocalRandom.current().nextInt(head, tail + 1));

        return Service.partitionForRank(populace, head, tail);
    }

    private static void swapForRank(final List<Solution> populace, final int firstIndex, final int secondIndex) {

        Solution temporary = populace.get(firstIndex);

        populace.set(firstIndex, populace.get(secondIndex));
        populace.set(secondIndex, temporary);
    }

    private static int partitionForRank(final List<Solution> populace, final int head, final int tail) {

        int pivot = populace.get(tail).getRank();
        int pivotIndex = head;

        for(int j = head; j < tail; j++) {

            if(populace.get(j).getRank() <= pivot) {

                Service.swapForRank(populace, pivotIndex, j);
                ++pivotIndex;
            }
        }

        Service.swapForRank(populace, pivotIndex, tail);

        return pivotIndex;
    }

    private static void randomizedQuickSortForCrowdingDistance(final List<Solution> populace, final int head, final int tail) {

        if(head < tail) {

            int pivot = Service.randomizedPartitionForCrowdingDistance(populace, head, tail);

            Service.randomizedQuickSortForCrowdingDistance(populace, head, pivot - 1);
            Service.randomizedQuickSortForCrowdingDistance(populace, pivot + 1, tail);
        }
    }

    private static int randomizedPartitionForCrowdingDistance(final List<Solution> populace, final int head, final int tail) {

        Service.swapForCrowdingDistance(populace, head, ThreadLocalRandom.current().nextInt(head, tail + 1));

        return Service.partitionForCrowdingDistance(populace, head, tail);
    }

    private static void swapForCrowdingDistance(final List<Solution> populace, final int firstIndex, final int secondIndex) {

        Solution temporary = populace.get(firstIndex);

        populace.set(firstIndex, populace.get(secondIndex));
        populace.set(secondIndex, temporary);
    }

    private static int partitionForCrowdingDistance(final List<Solution> populace, final int head, final int tail) {

        double pivot = populace.get(tail).getCrowdingDistance();
        int pivotIndex = head;

        for(int j = head; j < tail; j++) {

            if(populace.get(j).getCrowdingDistance() >= pivot) {

                Service.swapForRank(populace, pivotIndex, j);
                ++pivotIndex;
            }
        }

        Service.swapForRank(populace, pivotIndex, tail);

        return pivotIndex;
    }

    private static void randomizedQuickSortAgainstObjective(final Solution[] populace, final int head, final int tail, final int objectiveIndex) {

        if(head < tail) {

            int pivot = Service.randomizedPartitionAgainstObjective(populace, head, tail, objectiveIndex);

            Service.randomizedQuickSortAgainstObjective(populace, head, pivot - 1, objectiveIndex);
            Service.randomizedQuickSortAgainstObjective(populace, pivot + 1, tail, objectiveIndex);
        }
    }

    private static int randomizedPartitionAgainstObjective(final Solution[] populace, final int head, final int tail, final int objectiveIndex) {

        Service.swapAgainstObjective(populace, head, ThreadLocalRandom.current().nextInt(head, tail + 1));

        return Service.partitionAgainstObjective(populace, head, tail, objectiveIndex);
    }

    private static void swapAgainstObjective(final Solution[] populace, final int firstIndex, final int secondIndex) {

        Solution temporary = populace[firstIndex];
        populace[firstIndex] = populace[secondIndex];
        populace[secondIndex] = temporary;
    }

    private static int partitionAgainstObjective(final Solution[] populace, final int head, final int tail, final int objectiveIndex) {

        double pivot = populace[tail].getObjectiveValues().get(objectiveIndex);
        int pivotIndex = head;

        for(int j = head; j < tail; j++) {

            if(populace[j].getObjectiveValues().get(objectiveIndex) <= pivot) {

                Service.swapAgainstObjective(populace, pivotIndex, j);
                ++pivotIndex;
            }
        }

        Service.swapAgainstObjective(populace, pivotIndex, tail);

        return pivotIndex;
    }
}
