/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic.workforce;

import java.util.List;

/**
 *
 * @author Leo
 */
public class WorkForceProblem {
    private List<Integer> currentWorkers;
    private int weeks;
    private int fixedCostA;
    private int fixedCostB;
    private int extraCost;

    public List<Integer> getCurrentWorkers() {
        return currentWorkers;
    }

    public int getWeeks() {
        return weeks;
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

    public void setCurrentWorkers(List<Integer> currentWorkers) {
        this.currentWorkers = currentWorkers;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public void setFixedCostA(int fixedCostA) {
        this.fixedCostA = fixedCostA;
    }

    public void setFixedCostB(int fixedCostB) {
        this.fixedCostB = fixedCostB;
    }

    public void setExtraCost(int extraCost) {
        this.extraCost = extraCost;
    }
    
    
}
