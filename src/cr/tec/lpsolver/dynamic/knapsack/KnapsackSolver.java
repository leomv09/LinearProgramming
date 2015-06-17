package cr.tec.lpsolver.dynamic.knapsack;

import java.util.List;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class KnapsackSolver {
    
    public Object solve(Object obj) throws Exception {
        KnapsackProblem problem = (KnapsackProblem) obj;
        KnapsackBoundedAlgorithm algorithm = new KnapsackBoundedAlgorithm(problem);
        List<Item> items = algorithm.calcSolution();
        
        if (algorithm.isCalculated()) {
            int weight = algorithm.getSolutionWeight();
            int value = algorithm.getProfit();
            return new KnapsackResult(items, weight, value);
        }
        else {
            throw new Exception("Could not solve the problem");
        }
    }
    
}
