package helper;

import model.entity.Dataset;
import model.entity.Order;
import model.objective.Similarity;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class SimilarityRepository {
    private static ArrayList<ArrayList<Double>> matrix = new ArrayList<>();

    public SimilarityRepository(Dataset dataset){
        for (int i = 0; i < dataset.getItems().size(); i++) {
            ArrayList<Double> l = new ArrayList<>();
            for (int j = 0; j < dataset.getItems().size(); j++) {
                if(i == j){
                    l.add(j, 1.0);
                }else if(i < j){
                    int x = 0;
                    for (Order o : dataset.getOrders()) {
                        if(o.getItemIDs().contains(i) && o.getItemIDs().contains(j)){
                            ++x;
                        }
                    }

                    if(x == 0){
                        l.add(j, 0.0);
                    }else{
                        l.add(j, ((double) x/ (double)dataset.getOrders().size()));
                    }
                }else{
                    l.add(j, matrix.get(j).get(i));
                }
            }
            matrix.add(i, l);
        }
    }

    public ArrayList<ArrayList<Double>> getMatrix() {
//        for (ArrayList<Double> doubles : matrix) {
//            System.out.println(doubles);
//        }
        return matrix;
    }
}
