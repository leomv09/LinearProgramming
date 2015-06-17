package cr.tec.lpsolver;

import java.util.List;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class DualSolver implements Solver {

    @Override
    public Result solve(Problem problem) throws Exception {
        Linear function = problem.getObjetiveFunction();
        double[] c = linearToArray(function);
        
        List<Constraint> constraints = problem.getConstraints();
        double[] b = new double[constraints.size()];
        double[][] A = new double[constraints.size()][];
        Constraint constraint;
        
        for (int i = 0; i < constraints.size(); i++) {
            constraint = constraints.get(i);
            b[i] = constraint.getConstant();
            A[i] = linearToArray(constraint.getLinear());
        }
        
        DualAlgorithm algorithm = new DualAlgorithm(A, b, c);
        Result result = new Result(null, algorithm.value());
        double[] dual = algorithm.dual();
        result.createSet();
        
        List<String> variables = problem.getVariables();
        for (int i = 0; i < dual.length; i++) {
            result.addVariable(variables.get(i), dual[i]);
        }
        
        return result;
    }
    
    private double[] linearToArray(Linear linear) {
        List<Double> coefficients = linear.getCoefficients();
        double[] array = new double[coefficients.size()];
        
        for (int i = 0; i < coefficients.size(); i++) {
            array[i] = coefficients.get(i);
        }
        
        return array;
    }
    
}
