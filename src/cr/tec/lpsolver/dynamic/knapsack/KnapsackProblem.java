package cr.tec.lpsolver.dynamic.knapsack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class KnapsackProblem {
    
    private List<Item> items;
    private int maxWeight;

    public KnapsackProblem(List<Item> items, int maxWeight){
        this.items = items;
        this.maxWeight = maxWeight;
    }
    
    public KnapsackProblem(int maxWeight){
        this.items = new ArrayList<>();
        this.maxWeight = maxWeight;
    }
    
    public void addItem(Item item) {
        items.add(item);
    }
    
    public void addItem(String name, int weight, int value) {
        items.add(new Item(name, weight, value));
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

}
