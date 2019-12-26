package controller;

import model.entity.Dataset;
import usecase.CreateRandomOrders;

public class DatasetRepository {
    public Dataset dataset = new Dataset();
    public DatasetRepository(int numberOfItems, int numberOfOrder, int capacity, int numberOfItemPerOrder){
        dataset.setCapacity(capacity);
        dataset.setNumberOfOrders(numberOfOrder);
        dataset.setNumberOfItemPerOrder(numberOfItemPerOrder);
        dataset.setOrders(new CreateRandomOrders(numberOfItems, numberOfOrder, numberOfItemPerOrder).getOrders());
    }

    public Dataset getDataset() {
        return dataset;
    }
}
