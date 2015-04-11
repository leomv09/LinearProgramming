package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    public Result solve(Problem problem) throws Exception
    {
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
            if (!region.isBounded() && problem.getProblemType() == ProblemType.MAX) {
                return new Result(region, Double.NaN);
            }
            
            List<Point2D> optimum = getOptimun(
                region.getVertex(),
                problem.getProblemType(),
                problem.getObjetiveFunction()
            );

            Map<String, Double> values = new HashMap<>();
            values.put("x", optimum.get(0).getX());
            values.put("y", optimum.get(0).getY());

            Result res = new Result(region, problem.getObjetiveFunction().evaluate(values));

            for (int i = 0; i < optimum.size(); i++) {
                res.createSet();
                res.addVariable("x", optimum.get(i).getX(), i);
                res.addVariable("y", optimum.get(i).getY(), i);
            }

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
     * @return a list containing the optimum points of the feasible region.
     */
    private List<Point2D> getOptimun(Collection<Point2D> vertex, ProblemType type, Linear objectiveFunction)
    {
        Point2D currentPoint = null;
        double currentValue;
        List<Point2D> optimumValues = new ArrayList<>();
        List<Point2D> finalValues = new ArrayList<>();
        if (type == ProblemType.MAX)
        {
            currentValue = 0.0;
            for (Point2D point : vertex)
            {
                Map<String, Double> values = new HashMap();
                values.put("x", point.getX());
                values.put("y", point.getY());
                if (currentValue <= objectiveFunction.evaluate(values))
                {
                    optimumValues.add(point);
                    currentValue = objectiveFunction.evaluate(values);
                }
            }
            
            for(int i = 0; i < optimumValues.size(); i++)//Check if there are more than one optimum solution.
            {
                currentPoint = optimumValues.get(i);
                Map<String, Double> values = new HashMap();
                values.put("x", currentPoint.getX());
                values.put("y", currentPoint.getY());
                if(currentValue == objectiveFunction.evaluate(values))
                {
                    finalValues.add(currentPoint);
                }
            }
            
            return finalValues;
        }
        else
        {
            currentValue = Double.POSITIVE_INFINITY;
            for (Point2D point : vertex)
            {
                Map<String, Double> values = new HashMap();
                values.put("x", point.getX());
                values.put("y", point.getY());
                if (currentValue >= objectiveFunction.evaluate(values))
                {
                    optimumValues.add(point);
                    currentValue = objectiveFunction.evaluate(values);
                }
                
            }
            for(int i = 0; i < optimumValues.size(); i++)//Check if there are more than one optimum solution.
            {
                Point2D point = optimumValues.get(i);
                Map<String, Double> values = new HashMap();
                values.put("x", point.getX());
                values.put("y", point.getY());
                if(currentValue == objectiveFunction.evaluate(values))
                {
                    finalValues.add(currentPoint);
                }
            }
            return finalValues;
        }
    }
    
}
