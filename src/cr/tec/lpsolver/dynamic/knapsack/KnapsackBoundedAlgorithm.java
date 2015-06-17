package cr.tec.lpsolver.dynamic.knapsack;

import java.util.List;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class KnapsackBoundedAlgorithm extends KnapsackAlgorithm {
    
    public KnapsackBoundedAlgorithm() {
        super();
    }
 
    public KnapsackBoundedAlgorithm(int _maxWeight) {
        super(_maxWeight);
    }
 
    public KnapsackBoundedAlgorithm(List<Item> _itemList) {
        super(_itemList);
    }
 
    public KnapsackBoundedAlgorithm(List<Item> _itemList, int _maxWeight) {
        super(_itemList, _maxWeight);
    }
    
    public KnapsackBoundedAlgorithm(KnapsackProblem problem) {
        super(problem);
    }
 
    @Override
    public List<Item> calcSolution() {
        int n = itemList.size();
 
        // add items to the list, if bounding > 1
        for (int i = 0; i < n; i++) {
            Item item = itemList.get(i);
            if (item.getBounding() > 1) {
                for (int j = 1; j < item.getBounding(); j++) {
                    add(item.getName(), item.getWeight(), item.getValue());
                }
            }
        }
 
        super.calcSolution();
 
        // delete the added items, and increase the original items
        while (itemList.size() > n) {
            Item lastItem = itemList.get(itemList.size() - 1);
            if (lastItem.getInKnapsack() == 1) {
                for (int i = 0; i < n; i++) {
                    Item iH = itemList.get(i);
                    if (lastItem.getName().equals(iH.getName())) {
                        iH.setInKnapsack(1 + iH.getInKnapsack());
                        break;
                    }
                }
            }
            itemList.remove(itemList.size() - 1);
        }
 
        return itemList;
    }
}