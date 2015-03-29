package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Creates {@link FeasibleRegion2D} objects for a {@link Problem}.
 * 
 * @author jose
 */
public class FeasibleRegion2DFactory {
    
    /**
     * The name of the variables in the problem.
     */
    private final List<String> variables;
    
    /**
     * Construct a {@code FeasibleRegionFactory} for a problem.
     * 
     * @param problem The problem;
     */
    public FeasibleRegion2DFactory(Problem problem) {
        this.variables = problem.getVariables();
    }
    
    /**
     * Construct a {@code FeasibleRegionFactory} for a collection of variables.
     * 
     * @param variables The variables.
     */
    public FeasibleRegion2DFactory(Collection<String> variables) {
        this.variables = new ArrayList(variables);
    }
    
    /**
     * Construct a {@code FeasibleRegionFactory} for an array of variables.
     * 
     * @param variables The variables.
     */
    public FeasibleRegion2DFactory(String... variables) {
        this.variables = Arrays.asList(variables);
    }

    /**
     * Creates a {@code FeasibleRegion} for a constraint.
     * 
     * @param constraint The constraint.
     * 
     * @return The region.
     */
    public FeasibleRegion2D createFeasibleRegion(Constraint constraint) {
        return new FeasibleRegion2D(variables, constraint);
    }
    
    /**
     * Creates a empty {@code FeasibleRegion}.
     * 
     * @return The region.
     */
    public FeasibleRegion2D createEmptyRegion() {
        return new FeasibleRegion2D(variables);
    }
    
}
