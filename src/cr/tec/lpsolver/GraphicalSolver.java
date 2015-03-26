package cr.tec.lpsolver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import math.geom2d.Point2D;

/**
 *
 * @author Leo
 */
public class GraphicalSolver implements Solver {

    /**
     * Solve a optimization problem.
     *
     * @param problem The optimization problem.
     * 
     * @return The result of the problem or {@code null} if there exists no
     * feasible solution.
     */
    @Override
    public Result solve(Problem problem) {
        
        FeasibleRegionFactory regionFactory = new FeasibleRegionFactory(problem);
        FeasibleRegion region = null;
        
        for(Constraint cons : problem.getConstraints())
        {
            if(region == null)
            {
                region = regionFactory.createFeasibleRegion(cons);
            }
            else
            {
                FeasibleRegion region2 = regionFactory.createFeasibleRegion(cons);
                region = region.intersection(region2);
            }
        }
        
        if(region != null)
        {
            Point2D optimumPoint = getMinMax(region.getVertex(), problem.getProblemType(), problem.getObjetiveFunction());
            Map<String, Double> values = new HashMap();
            values.put("x", optimumPoint.getX());
            values.put("y", optimumPoint.getY());
        
            Result res = new Result(region, problem.getObjetiveFunction().evaluate(values));
            res.addVariable("x",  optimumPoint.getX());
            res.addVariable("y",  optimumPoint.getY());
            return res;
        }
        else
        {
            return null;
        }
    }
    
    
    /**
     * Obtains the optimum point (min or max) of a feasible region.
     * @param vertex A list of vertex from the feasible region.
     * @param type The problem type (min or max).
     * @param objectiveFunction The Linear objective function of the problem.
     * @return The optimum Point2D (point) of the feasible region.
     */
    private Point2D getMinMax(Collection<Point2D> vertex, ProblemType type, Linear objectiveFunction)
    {
        Point2D currentPoint = null;
        double currentValue;
        if(type == ProblemType.MAX)
        {
            currentValue = 0.0;
            for(Point2D point : vertex)
            {
                Map<String, Double> values = new HashMap();
                values.put("x", point.getX());
                values.put("y", point.getY());
                if(currentValue < objectiveFunction.evaluate(values))
                {
                    currentPoint = point;
                    currentValue = objectiveFunction.evaluate(values);
                }
            }
            return currentPoint;
        }
        else
        {
            currentValue = Double.POSITIVE_INFINITY;
            for(Point2D point : vertex)
            {
                Map<String, Double> values = new HashMap();
                values.put("x", point.getX());
                values.put("y", point.getY());
                if(currentValue > objectiveFunction.evaluate(values))
                {
                    currentPoint = point;
                    currentValue = objectiveFunction.evaluate(values);
                }
            }
            return currentPoint;
        }  
    }
    
}
