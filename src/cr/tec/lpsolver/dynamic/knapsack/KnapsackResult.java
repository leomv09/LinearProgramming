package cr.tec.lpsolver.dynamic.knapsack;

import java.util.List;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class KnapsackResult {
    
    private final List<Item> items;
    private final int totalWeight;
    private final int totalValue;

    public KnapsackResult(List<Item> items, int totalWeight, int totalValue) {
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

    public int getTotalWeight() {
        return totalWeight;
    }

    public int getTotalValue() {
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
