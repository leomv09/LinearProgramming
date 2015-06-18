package cr.tec.lpsolver;

import java.util.List;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class DualSolver implements Solver {

    @Override
    public Result solve(Problem problem) throws Exception {
        List<String> variables = problem.getVariables();
        Linear function = problem.getObjetiveFunction();
        double[] c = linearToArray(function, variables);
        
        List<Constraint> constraints = problem.getConstraints();
        double[] b = new double[constraints.size()];
        double[][] A = new double[constraints.size()][];
        Constraint constraint;
        
        for (int i = 0; i < constraints.size(); i++) {
            constraint = constraints.get(i);
            b[i] = constraint.getConstant();
            A[i] = linearToArray(constraint.getLinear(), variables);
        }
        
        DualAlgorithm algorithm = new DualAlgorithm(A, b, c);
        Result result = new Result(null, algorithm.value());
        double[] primal = algorithm.primal();
        result.createSet();
        
        for (int i = 0; i < primal.length; i++) {
            result.addVariable(variables.get(i), primal[i]);
        }
        
        return result;
    }
    
    private double[] linearToArray(Linear linear, List<String> variables) {
        double[] array = new double[variables.size()];
        
        double coefficient;
        for (int i = 0; i < variables.size(); i++) {
            coefficient = linear.getCoefficient(variables.get(i));
            if (Double.isNaN(coefficient)) {
                coefficient = 0;
            }
            array[i] = coefficient;
        }
        return array;
    }
    
}
