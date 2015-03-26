package cr.tec.lpsolver;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Result} of a {@link Problem}.
 * 
 * @author Leo
 */
public class Result {
    
    FeasibleRegion feasibleRegion;//Feasible region of the problem.
    double optimumValue;//Best result depending on its type(min or max).
    Map<String, Double> variablesResult;//A map with the variable and its corresponding result.
    
    
    /**
     * Creates a new instance of Result. 
     * @param region The feasible region of the problem.
     * @param OptimumValue The best result at the moment of evaluating the points.
     */
    public Result(FeasibleRegion region, double OptimumValue)
    {
        this.feasibleRegion = region;
        this.optimumValue = OptimumValue;
        this.variablesResult = new HashMap();
    }
    
    /**
     * Obtains the feasible region of the prroblem's result.
     * @return feasible region of the problem.
     */
    public FeasibleRegion getFeasibleRegion()
    {
        return this.feasibleRegion;
    }
    
    /**
     * Obtains the optimum value of the problem.
     * @return The double optimum value.
     */
    public double getOptimumResult()
    {
        return this.optimumValue;
    }
    
    /**
     * Adds a new variable to the variables map
     * @param variable the name of the variable.
     * @param result the variable result value.
     */
    public void addVariable(String variable, Double result)
    {
        this.variablesResult.put(variable, result);
    }
    
    /**
     * Obtains the variables map.
     * @return Map object corrsponding to the variables map.
     */
    public Map getResults()
    {
        return this.variablesResult;
    }
}
