/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic.workforce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Leo
 */
public class Stage {
    private final List<Integer> weekWorkers;
    private final List<Integer> possibleWorkers;
    private final List<Map<Integer, Integer>> optimumWorkforce;
    private final List<Map<Integer, Double>> aggregateValues;
    private final int number;
    
    /**
     * Creates a new instance of Stage.
     * @param workers The stage workers.
     * @param PossibleWorkers The possiblilities.
     * @param Number The stage Number
     */
    public Stage(List<Integer> workers, List<Integer> PossibleWorkers, int Number)
    {
        weekWorkers = workers;
        possibleWorkers = PossibleWorkers;
        optimumWorkforce = new ArrayList<>();
        aggregateValues = new ArrayList<>();
        number = Number;
    }
    
    
    public void addOptimumWorkForce(int stageForce, int optimumForce)
    {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(stageForce, optimumForce);
        this.optimumWorkforce.add(map);
    }
    
    public List<Integer> getOptimumForce(int stageForce)
    {
        List<Integer> res = new ArrayList<>();
        for(Map<Integer, Integer> map : optimumWorkforce)
        {
            if(map.containsKey(stageForce))
            {
                res.add(map.get(stageForce));
            }
        }
        
        return res;
    }
    
    public void addAggregateValue(int stageForce, double value)
    {
        Map<Integer, Double> map = new HashMap<>();
        map.put(stageForce, value);
        aggregateValues.add(map);
    }
    
    public double getAggregateValue(int stageForce)
    {
        double value = Double.POSITIVE_INFINITY;
        for(Map<Integer, Double> map : aggregateValues)
        {
            if(map.containsKey(stageForce))
            {
                double currentValue = map.get(stageForce);
                if(currentValue < value)
                {
                    value = currentValue;
                }
            }
        }
        return value;
    }

    public List<Integer> getWeekWorkers() {
        return weekWorkers;
    }

    public List<Integer> getPossibleWorkers() {
        return possibleWorkers;
    }

    public int getNumber() {
        return number;
    }
    
    
    
    
    
}
