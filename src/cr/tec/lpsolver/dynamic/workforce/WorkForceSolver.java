/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic.workforce;

import cr.tec.lpsolver.dynamic.DynamicSolver;

/**
 *
 * @author Leo
 */
public class WorkForceSolver implements DynamicSolver{

    @Override
    public Object solve(Object obj) throws Exception {
        
        WorkForceProblem problem = (WorkForceProblem) obj;
        
        WorkForceSizeAlgorithm algo = new WorkForceSizeAlgorithm(problem.getWeeks(), problem.getFixedCostA(), problem.getFixedCostB(), problem.getExtraCost(), problem.getCurrentWorkers());
        return algo.solve();  
    }
    
}
