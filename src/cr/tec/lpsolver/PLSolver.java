package cr.tec.lpsolver;

import net.sf.javailp.Linear;
import net.sf.javailp.Problem;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactoryLpSolve;

/**
 *
 * @author Leo
 */
public class PLSolver extends ProblemSolver {

    /**
     * Method that solves a "basic" linear programming problem. Works with only two variables.
     * @param context object that contains the problem context(constraints, objective function).
     * @param type the problem type(Maximize or Minimize).
     * @return The two points that represents the problem's solution.
     */
    @Override
    public Object solve(Object context, ProblemType type) {
        PLContext data = (PLContext) context;
        SolverFactory lpFactory = initSolverFactory();
        Problem problem = new Problem();//Creates a new problem intance in order to solve the context.
        Linear linear = new Linear();//A linear equation for the objective function.
        linear.add(data.getFunction().getCoefficients()[0], "x");//Adds the x coefficient to the equation.
        linear.add(data.getFunction().getCoefficients()[1], "y");//Adds the y coefficient to the equation.
        
        problem.setObjective(linear, type.name());//Sets the objective function(Not really sure if this works)
        String[] variableNames = {"x", "y"}; 
        for(Constraint cons : data.getConstraints())//Adds each constraint's equation to the problem.
        {
            linear = new Linear();//Creates a new inequation for each constraint.
            for(int i = 0; i < cons.getCoefficients().length; i++)
            {
                linear.add(cons.getCoefficients()[i], variableNames[i]);
            }
            problem.add(linear, cons.getRelationship().name(), cons.getConstant());//Adds the equation with its operator and constant value.
        }
        problem.setVarType("x", Double.class);
        problem.setVarType("y", Double.class);
        
        Solver solver = lpFactory.get(); // Returns a new solver instance.
        return solver.solve(problem); //Returns a "net.sf.javailp.Result;" object.
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
        SolverFactory factory = new SolverFactoryLpSolve(); // use lp_solve
        factory.setParameter(Solver.VERBOSE, 0); 
        factory.setParameter(Solver.TIMEOUT, 100); // set timeout to 100 seconds
        return factory;
    }
    
}