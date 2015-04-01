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
    public Result solve(Problem problem) throws Exception {
        
        FeasibleRegion2DFactory factory = new FeasibleRegion2DFactory(problem);
        FeasibleRegion2D region = null;
        
        for (Constraint cons : problem.getConstraints())
        {
            if (region == null)
            {
                region = factory.createFeasibleRegion(cons);
            }
            else
            {
                region = region.intersection(factory.createFeasibleRegion(cons));
            }
        }
        
        if (region != null && !region.isEmpty())
        {
            Point2D optimum = getOptimun(region.getVertex(), problem.getProblemType(), problem.getObjetiveFunction());
            Map<String, Double> values = new HashMap();
            values.put("x", optimum.getX());
            values.put("y", optimum.getY());
        
            Result res = new Result(region, problem.getObjetiveFunction().evaluate(values));
            res.addVariable("x",  optimum.getX());
            res.addVariable("y",  optimum.getY());
            return res;
        }
        else
        {
            throw new Exception("Empty region. No solutions.");
        }
    }
    
    
    /**
     * Obtains the optimum point (min or max) of a feasible region.
     * 
     * @param vertex A list of vertex from the feasible region.
     * @param type The problem type (min or max).
     * @param objectiveFunction The Linear objective function of the problem.
     * 
     * @return The optimum Point2D (point) of the feasible region.
     */
    private Point2D getOptimun(Collection<Point2D> vertex, ProblemType type, Linear objectiveFunction)
    {
        Point2D currentPoint = null;
        double currentValue;
        
        if (type == ProblemType.MAX)
        {
            currentValue = 0.0;
            for (Point2D point : vertex)
            {
                Map<String, Double> values = new HashMap();
                values.put("x", point.getX());
                values.put("y", point.getY());
                if (currentValue < objectiveFunction.evaluate(values))
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
            for (Point2D point : vertex)
            {
                Map<String, Double> values = new HashMap();
                values.put("x", point.getX());
                values.put("y", point.getY());
                if (currentValue > objectiveFunction.evaluate(values))
                {
                    currentPoint = point;
                    currentValue = objectiveFunction.evaluate(values);
                }
            }
            return currentPoint;
        }  
    }
    
}
