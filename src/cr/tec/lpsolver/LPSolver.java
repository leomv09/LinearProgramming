package cr.tec.lpsolver;

import net.sf.javailp.Linear;
import net.sf.javailp.Operator;
import net.sf.javailp.Problem;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryGLPK;

/**
 *
 * @author Leo
 */
public class LPSolver extends Solver {

    /**
     * Method that solves a "basic" linear programming problem. Works with only two variables.
     * @param context object that contains the problem context(constraints, objective function).
     * @param type the problem type(Maximize or Minimize).
     * @return The two points that represents the problem's solution.
     */
    @Override
    public Result solve(Object context, ProblemType type) {
        LPContext data = (LPContext) context;
        SolverFactory factory = initSolverFactory();
        Problem problem = new Problem();//Creates a new problem intance in order to solve the context.
        
        Linear linear = new Linear();//A linear equation for the objective function.
        linear.add(data.getFunction().getCoefficient(0), "x");//Adds the x coefficient to the equation.
        linear.add(data.getFunction().getCoefficient(1), "y");//Adds the y coefficient to the equation.
        problem.setObjective(linear, type.name());//Sets the objective function(Not really sure if this works)
        
        String[] variableNames = {"x", "y"};
        for (Constraint c : data.getConstraints())//Adds each constraint's equation to the problem.
        {
            linear = new Linear();//Creates a new inequation for each constraint.
            for (int i = 0; i < c.getCoefficients().length; i++)
            {
                linear.add(c.getCoefficient(i), variableNames[i]);
            }
            problem.add(linear, toOperatorILP(c.getRelationship()), c.getConstant());//Adds the equation with its operator and constant value.
        }
        
        problem.setVarType("x", Double.class);
        problem.setVarType("y", Double.class);
        
        net.sf.javailp.Solver solver = factory.get();
        net.sf.javailp.Result result = solver.solve(problem);
        
        double objetive = (double) result.getObjective();
        double[] values = new double[2];
        values[0] = (double) result.get("x");
        values[1] = (double) result.get("y");
        
        return new Result(values, objetive);
        
        /*
        * IMPORTANT
        * How to add the constant value of the objective function to each result?-> Just by adding it to each variable result at the end?
        * Note that to retrieve each value (x and y) use -> Result.get(variablename). And in order to retrieve the result use-> Result.getObjective().
        */
    }
    
    /**
     * Initializes the SolverFactory instance needed to solve the problem.
     * @return SolverFactory instance.
     */
    private SolverFactory initSolverFactory()
    {
        SolverFactory factory = new SolverFactoryGLPK(); // use glpk
        factory.setParameter(net.sf.javailp.Solver.VERBOSE, 0); 
        factory.setParameter(net.sf.javailp.Solver.TIMEOUT, 100); // set timeout to 100 seconds
        return factory;
    }
    
    private Operator toOperatorILP(Relationship relationship)
    {
        switch (relationship)
        {
            case LEQ:
                return Operator.LE;
            case GEQ:
                return Operator.GE;
        }
        return null;
    }
    
}