/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic.workforce;

/**
 *
 * @author Leo
 */
public class WorkForceResult {
    double finalCost;
    String res;
    
    public WorkForceResult(double cost, String res)
    {
        this.finalCost = cost;
        this.res = res;
    }

    public double getFinalCost() {
        return finalCost;
    }
    
    @Override
    public String toString()
    {
        return res;
    }
}
