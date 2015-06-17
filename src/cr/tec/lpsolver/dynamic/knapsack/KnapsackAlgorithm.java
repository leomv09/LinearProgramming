package cr.tec.lpsolver.dynamic.knapsack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KnapsackAlgorithm {

    protected List<Item> itemList;
    protected double maxWeight;
    protected double solutionWeight;
    protected double profit;
    protected boolean calculated;

    public KnapsackAlgorithm() {
        itemList = new ArrayList<>();
        maxWeight = 0;
        solutionWeight = 0;
        profit = 0;
        calculated = false;
    }

    public KnapsackAlgorithm(double _maxWeight) {
        this();
        setMaxWeight(_maxWeight);
    }

    public KnapsackAlgorithm(List<Item> _itemList) {
        this();
        setItemList(_itemList);
    }

    public KnapsackAlgorithm(List<Item> _itemList, double _maxWeight) {
        this();
        setItemList(_itemList);
        setMaxWeight(_maxWeight);
    }
    
    public KnapsackAlgorithm(KnapsackProblem problem) {
        this(problem.getItems(), problem.getMaxWeight());
    }

    // calculte the solution of 0-1 knapsack problem with dynamic method:
    public List<Item> calcSolution() {
        int n = itemList.size();

        setInitialStateForCalculation();
        if (n > 0 && maxWeight > 0) {
            List< List<Double>> c = new ArrayList<>();
            List<Double> curr = new ArrayList<>();

            double increment = calculateIncrement();
            
            c.add(curr);
            for (int j = 0; j <= maxWeight; j++) {
                curr.add(0.0);
            }
            for (int i = 1; i <= n; i++) {
                List<Double> prev = curr;
                c.add(curr = new ArrayList<>());
                for (int j = 0; j <= maxWeight; j++) {
                    if (j > 0) {
                        double wH = itemList.get(i - 1).getWeight();
                        curr.add(
                                (wH > j)
                                        ? prev.get(j)
                                        : Math.max(
                                                prev.get(j),
                                                itemList.get(i - 1).getValue() + prev.get(j - (int) Math.ceil(wH))
                                        )
                        );
                    } else {
                        curr.add(0.0);
                    }
                } // for (j...)
            } // for (i...)
            profit = curr.get(curr.size() - 1);
            double j = maxWeight;
            for (int i = n; i > 0 && j >= 0; i--) {
                double tempI = c.get(i).get((int) Math.ceil(j));
                double tempI_1 = c.get(i - 1).get((int) Math.ceil(j));
                if ((i == 0 && tempI > 0)
                        || (i > 0 && tempI != tempI_1)) {
                    Item iH = itemList.get(i - 1);
                    double wH = iH.getWeight();
                    iH.setInKnapsack(1);
                    j -= wH;
                    solutionWeight += wH;
                }
            } // for()
            calculated = true;
        } // if()
        return itemList;
    }

    // add an item to the item list
    public void add(String name, double weight, double value) {
        if (name.equals("")) {
            name = "" + (itemList.size() + 1);
        }
        itemList.add(new Item(name, weight, value));
        setInitialStateForCalculation();
    }

    // add an item to the item list
    public void add(double weight, double value) {
        add("", weight, value); // the name will be "itemList.size() + 1"!
    }

    // remove an item from the item list
    public void remove(String name) {
        for (Iterator<Item> it = itemList.iterator(); it.hasNext();) {
            if (name.equals(it.next().getName())) {
                it.remove();
            }
        }
        setInitialStateForCalculation();
    }

    // remove all items from the item list
    public void removeAllItems() {
        itemList.clear();
        setInitialStateForCalculation();
    }

    public double getProfit() {
        if (!calculated) {
            calcSolution();
        }
        return profit;
    }

    public double getSolutionWeight() {
        return solutionWeight;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double _maxWeight) {
        maxWeight = Math.max(_maxWeight, 0);
    }

    public void setItemList(List<Item> _itemList) {
        if (_itemList != null) {
            itemList = _itemList;
            for (Item item : _itemList) {
                item.checkMembers();
            }
        }
    }

    // set the member with name "inKnapsack" by all items:
    private void setInKnapsackByAll(int inKnapsack) {
        for (Item item : itemList) {
            if (inKnapsack > 0) {
                item.setInKnapsack(1);
            } else {
                item.setInKnapsack(0);
            }
        }
    }

    // set the data members of class in the state of starting the calculation:
    protected void setInitialStateForCalculation() {
        setInKnapsackByAll(0);
        calculated = false;
        profit = 0;
        solutionWeight = 0;
    }

    private double calculateIncrement() {
        double increment = Double.MAX_VALUE;
        for (Item item : itemList) {
            if (item.getWeight() < increment) {
                increment = item.getWeight();
            }
        }
        if (increment > 1) {
            increment = 1;
        }
        return increment;
    }

}
