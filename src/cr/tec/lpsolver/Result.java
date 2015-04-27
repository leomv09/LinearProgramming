package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The {@code Result} of a {@link Problem}.
 * 
 * @author Leo
 */
public class Result implements Iterable {
    
    /**
     * A map with the variable and its corresponding result.
     */
    private List<Map<String, Double>> resultSet;
    
    /**
     * Feasible region of the problem.
     */
    private final FeasibleRegion2D feasibleRegion;
    
    /**
     * Best result depending on its type(min or max).
     */
    private final double optimumValue;
    
    /**
     * Creates a new instance of Result. 
     * 
     * @param region The feasible region of the problem.
     * @param optimumValue The best result at the moment of evaluating the points.
     */
    public Result(FeasibleRegion2D region, double optimumValue)
    {
        this.feasibleRegion = region;
        this.optimumValue = optimumValue;
        this.resultSet = new ArrayList<>();
    }
    
    /**
     * Obtains the feasible region of the problem's result.
     * 
     * @return The feasible region of the problem.
     */
    public FeasibleRegion2D getFeasibleRegion()
    {
        return feasibleRegion;
    }
    
    /**
     * Obtains the optimum value of the problem.
     * 
     * @return The optimum value.
     */
    public double getOptimumValue()
    {
        return optimumValue;
    }
    
    /**
     * Create a new result set if the problem has multiple solutions.
     * 
     * @return The new set index.
     */
    public int createSet() {
        resultSet.add(new HashMap<>());
        return resultSet.size();
    }
    
    /**
     * Adds a new variable to a result.
     * 
     * @param variable The variable.
     * @param result The result.
     * @param index The result index.
     */
    public void addVariable(String variable, Double result, int index) {
        if (resultSet.isEmpty()) {
            createSet();
        }
        
        try {
            Map<String, Double> set = resultSet.get(index);
            set.put(variable, result);
        }
        catch (IndexOutOfBoundsException ex) { }
    }
    
    /**
     * Adds a new variable to the main result.
     * 
     * @param variable the name of the variable.
     * @param result the variable result value.
     */
    public void addVariable(String variable, Double result) {
        addVariable(variable, result, 0);
    }
    
    /**
     * Get a certain result.
     * 
     * @param index The result index.
     * @return The result.
     */
    public Map<String, Double> getResults(int index) {
        return resultSet.get(index);
    }
    
    public void setResults(List<Map<String, Double>> ResultSet)
    {
        this.resultSet = ResultSet;
    }
    
    /**
     * Get the main results
     * 
     * @return The result.
     */
    public Map<String, Double> getResults() {
        return getResults(0);
    }
    
    /**
     * Get the amount of sets in the result.
     * 
     * @return The result set size.
     */
    public int size() {
        return resultSet.size();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator iterator() {
        return resultSet.iterator();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RESULT").append("\n");
        sb.append("Z = ").append(optimumValue).append("\n");
        
        for (Map<String, Double> map : resultSet) {
            for (Entry<String, Double> entry : map.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(" ");
            }
            sb.append("\n");
        }
        
        sb.append(feasibleRegion).append("\n");
        return sb.toString();
    }
    
}
