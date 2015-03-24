package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Creates {@link FeasibleRegion} objects for a {@link Problem}.
 * 
 * @author jose
 */
public class FeasibleRegionFactory {
    
    /**
     * The name of the variables in the problem.
     */
    private final List<String> variables;
    
    /**
     * Construct a {@code FeasibleRegionFactory} for a problem.
     * 
     * @param problem The problem;
     */
    public FeasibleRegionFactory(Problem problem) {
        this.variables = new ArrayList<>(problem.getVariables());
    }
    
    /**
     * Construct a {@code FeasibleRegionFactory} for a collection of variables.
     * 
     * @param variables The variables.
     */
    public FeasibleRegionFactory(Collection<String> variables) {
        this.variables = new ArrayList(variables);
    }
    
    /**
     * Construct a {@code FeasibleRegionFactory} for an array of variables.
     * 
     * @param variables The variables.
     */
    public FeasibleRegionFactory(String... variables) {
        this.variables = Arrays.asList(variables);
    }

    /**
     * Creates a {@code FeasibleRegion} for a constraint.
     * 
     * @param constraint The constraint.
     * 
     * @return The region.
     */
    public FeasibleRegion createFeasibleRegion(Constraint constraint) {
        return new FeasibleRegion(variables, constraint);
    }
    
    /**
     * Creates a empty {@code FeasibleRegion}.
     * 
     * @return The region.
     */
    public FeasibleRegion createEmptyRegion() {
        return new FeasibleRegion(variables);
    }
    
}
