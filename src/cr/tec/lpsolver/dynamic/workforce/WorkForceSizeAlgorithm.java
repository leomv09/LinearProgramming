/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic.workforce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Leo
 */
public class WorkForceSizeAlgorithm {
    private final List<Stage> stages;
    private final List<Integer> currentWorkers;
    private final int weeks;
    private final int fixedCostA;
    private final int fixedCostB;
    private final int extraCost;
    private int maxValue;
    boolean foundMaxValue;
    
    
    public WorkForceSizeAlgorithm(int Weeks, int FixedCostA, int FixedCostB, int ExtraCost, List<Integer> CurrentWorkers)
    {
        this.weeks = Weeks;
        this.fixedCostA = FixedCostA;
        this.fixedCostB = FixedCostB;
        this.extraCost = ExtraCost;
        this.stages = new ArrayList<>();
        this.currentWorkers = CurrentWorkers;
        this.foundMaxValue = false;
        setMaxValue();
    }
    
    private void setMaxValue()
    {
        int value = Integer.MIN_VALUE;
        
        for(int i = 0; i < weeks; i++)
        {
            int currentValue = currentWorkers.get(i);
            if(currentValue > value)
            {
                value = currentValue;
            }
        }
        this.maxValue = value;
    }
    
    private List<Integer> getStageNumbers(int weekNumber, int previousWeekNumber)
    {
        List<Integer> res = new ArrayList<>();
        if(previousWeekNumber >= weekNumber)
        {
            if(previousWeekNumber == maxValue)
            {
                foundMaxValue = true;
                res.add(maxValue);
                return res;
            }
            else
            {
                res.add(previousWeekNumber);
                if(foundMaxValue)
                {
                    res.add(maxValue);
                }
            }
        }
        else
        {
            for(int i = previousWeekNumber; i <= weekNumber; i++)
            {
                res.add(i);
            }
            if(foundMaxValue)
            {
                res.add(maxValue);
            }
        }
        return res;
    }
    
    public void setStages()
    {
        List<Integer> prevStageNumbers = null;
        for(int i = weeks-1; i >= 0; i--)
        {
            if(i-1 < 0)
            {
                List<Integer> list = new ArrayList<>();
                list.add(0);
                stages.add(new Stage(list, prevStageNumbers, i));
                break;
                
            }
            int currentWeek = currentWorkers.get(i);
            int previousWeek = currentWorkers.get(i-1);
            List<Integer> newStageNumbers  = getStageNumbers(currentWeek, previousWeek);
            if(i == weeks-1)
            {
                prevStageNumbers = newStageNumbers;
                List<Integer> list = new ArrayList<>();
                list.add(currentWeek);
                Stage stage = new Stage(newStageNumbers, list, i);
                stage.addAggregateValue(currentWeek, 0);
                stages.add(stage);
            }
            else
            {
                stages.add(new Stage(newStageNumbers, prevStageNumbers, i));
                prevStageNumbers = newStageNumbers;
            }  
        }
    }
    
    /**
     * Applies the workforce formulas with the given numbers.
     * 
     * @param x The current week number.
     * @param x1 The next week number ()
     * @param x2 The previous week
     * @param aggregate
     * @return 
     */
    private Map<Integer, Double> applyFormula(int x, int x1, int x2, double aggregate)
    {
        int cost1 = this.extraCost * (x1 - x);
        int cost2 = 0;
        int sub = x1 - x2;
        
        if(sub > 0)
        {
            cost2 = this.fixedCostA + this.fixedCostB * sub;
        }
        Map<Integer, Double> map = new HashMap();
        map.put(x1, cost1 + cost2 + aggregate);
        return map;
    }
    
    private double getMinValue(List<Map<Integer, Double>> maps)
    {
        double value = Double.POSITIVE_INFINITY;
        for(Map<Integer, Double> map : maps)
        {
            Collection<Double> dlist = map.values();
            Object[] values = dlist.toArray();
            for(int i = 0; i < dlist.size(); i++)
            {
                double currentValue = (double) values[i];
                if(currentValue < value)
                {
                    value = currentValue;
                }
            }
        }
        return value;
    }
    
    
    private int getBestWorkForce(List<Map<Integer, Double>> maps, List<Integer> keys, double value)
    {
        int best = -1;
        
        for(Map<Integer, Double> map : maps)
        {
            for(Integer key : keys)
            {
                if(map.containsKey(key))
                {
                    if(map.get(key) == value)
                    {
                        best = key;
                    }
                }
            }
        }
        return best;
    }
    
    public WorkForceResult solve()
    {
        setStages();
        Stage prev = null;
        for(int i = 0; i< stages.size(); i++)
        {
            Stage stage = stages.get(i);
            for(Integer weekWorkNumber : stage.getWeekWorkers())
            {
                List<Map<Integer, Double>> values = new ArrayList<>();
                for(Integer possibleWorkNumber : stage.getPossibleWorkers())
                {   
                    if(prev == null)
                    {
                        Map<Integer, Double> value = this.applyFormula(this.currentWorkers.get(stage.getNumber()), possibleWorkNumber, weekWorkNumber, 0);
                        values.add(value);
                        prev = stage;
                    }
                    else
                    {
                        Map<Integer, Double> value = this.applyFormula(this.currentWorkers.get(stage.getNumber()), possibleWorkNumber, weekWorkNumber, prev.getAggregateValue(possibleWorkNumber));
                        values.add(value);
                    }
                }
                double optimumValue = this.getMinValue(values);
                stage.addAggregateValue(weekWorkNumber, optimumValue);
                int optimumForce = this.getBestWorkForce(values, stage.getPossibleWorkers(), optimumValue);
                stage.addOptimumWorkForce(weekWorkNumber, optimumForce);
            }
            prev = stage;
        }
        stages.get(0).getAggregateValue(0);
        WorkForceResult res = new WorkForceResult(stages.get(stages.size() - 1).getAggregateValue(0), this.toString());
        return res;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public int getFixedCostA() {
        return fixedCostA;
    }

    public int getFixedCostB() {
        return fixedCostB;
    }

    public int getExtraCost() {
        return extraCost;
    }

    public int getMaxValue() {
        return maxValue;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int j = 1;
        int optimumForce = stages.get(weeks-1).getOptimumForce(0).get(0);
        double optimumCost = stages.get(weeks-1).getAggregateValue(0);
        sb.append("For week ").append(j).append(" the right number(s) is(are): ").append(optimumForce).append("\n");
        j++;
        for(int i = weeks - 2; i >= 0; i--)
        {
            sb.append("For week ").append(j).append(" the right number(s) is(are): ");
            int number = stages.get(i).getOptimumForce(optimumForce).get(0);
            List<Integer> container = new ArrayList<>();
            for(Integer number1 : stages.get(i).getOptimumForce(optimumForce))
            {
                if(!container.contains(number1))
                {
                    sb.append(" ").append(number1);
                    container.add(number1);
                }    
            }
            container.clear();;
            optimumForce = number;   
            sb.append("\n");
            j++;
        }
        sb.append("Optimum cost is: ").append(optimumCost);
        return  sb.toString();
    }
    
    
}
