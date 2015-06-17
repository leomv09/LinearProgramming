package cr.tec.lpsolver.dynamic.knapsack;

import java.util.List;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class KnapsackResult {
    
    private final List<Item> items;
    private final double totalWeight;
    private final double totalValue;

    public KnapsackResult(List<Item> items, double totalWeight, double totalValue) {
        this.items = items;
        this.totalWeight = totalWeight;
        this.totalValue = totalValue;
        //items.removeIf((Item t) -> t.getInKnapsack() == 0);
    }

    public List<Item> getItems() {
        return items;
    }
    
    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public double getTotalValue() {
        return totalValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total Weight: ").append(totalWeight).append("\n");
        sb.append("Total Value: ").append(totalValue).append("\n\n");
        for (Item item : items) {
            sb.append(item).append("\n");
        }
        return sb.toString();
    }
    
}
