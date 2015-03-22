package cr.tec.lpsolver;

import math.geom2d.Point2D;

/**
 * The {@code Result} of a {@link Problem}.
 * 
 * @author Leo
 */
public class Result {
    
    FeasibleRegion feasibleRegion;//Feasible region of the problem.
    double optimumValue;//Best result depending on its type(min or max).
    Point2D optimumPoint;//Best point of the feasible region.
    
    
    /**
     * Creates a new instance of Result. 
     * @param region The feasible region of the problem.
     * @param OptimumValue The best result at the moment of evaluating the points.
     * @param OptimumPoint The best point of the feasible region.
     */
    public Result(FeasibleRegion region, double OptimumValue, Point2D OptimumPoint)
    {
        this.feasibleRegion = region;
        this.optimumValue = OptimumValue;
        this.optimumPoint = OptimumPoint;
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
     * Obtains the optimum point of the feasible region.
     * @return the best Point2D (point) of the feasible region.
     */
    public Point2D getOptimumPoint()
    {
        return this.optimumPoint;
    }
}
