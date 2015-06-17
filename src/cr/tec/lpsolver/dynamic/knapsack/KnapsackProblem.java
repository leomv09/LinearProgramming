package cr.tec.lpsolver.dynamic.knapsack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class KnapsackProblem {
    
    private List<Item> items;
    private double maxWeight;

    public KnapsackProblem(List<Item> items, double maxWeight) {
        this.items = items;
        this.maxWeight = maxWeight;
    }
    
    public KnapsackProblem(double maxWeight){
        this.items = new ArrayList<>();
        this.maxWeight = maxWeight;
    }
    
    public void addItem(Item item) {
        int bounding = (int) Math.floor(this.maxWeight / item.getWeight());
        item.setBounding(bounding);
        items.add(item);
    }
    
    public void addItem(String name, double weight, double value) {
        addItem(new Item(name, weight, value));
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

}
