package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
            
            List<Point2D> optimum = getOptimum(
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
    
    private double evaluatePoint(Point2D point, Linear function) {
        Map<String, Double> values = new HashMap();
        values.put("x", point.getX());
        values.put("y", point.getY());
        return function.evaluate(values);
    }

    private List<Point2D> getOptimum(Collection<Point2D> vertex, ProblemType type, Linear objectiveFunction)
    {
        List<Point2D> points = new ArrayList<>();
        Double optimum = null;
        Double value;
        
        for (Point2D point : vertex) {
            value = evaluatePoint(point, objectiveFunction);
            
            if (optimum == null) {
                optimum = value;
                points.add(point);
            }
            else if (Objects.equals(value, optimum)) {
                points.add(point);
            }
            else if (type == ProblemType.MIN && value < optimum) {
                optimum = value;
                points.clear();
                points.add(point);
            }
            else if (type == ProblemType.MAX && value > optimum) {
                optimum = value;
                points.clear();
                points.add(point);
            }
        }
        
        return points;
    }
    
}
