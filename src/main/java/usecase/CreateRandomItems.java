package usecase;

import model.entity.Item;

import java.util.ArrayList;

public class CreateRandomItems {

    public ArrayList<Item> items = new ArrayList<>();

    public CreateRandomItems(int numberOfItems){
        for (int i = 0; i < numberOfItems; i++) {
            Item item = new Item();
            int w = (int) Math.round((Math.random() * 9) + 1);
            item.setWeight(w);
            items.add(item);
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
