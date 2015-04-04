package cr.tec.lpsolver;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author jose
 */
public class GraphicalSolverTest {
    
    private final Solver solver;
    
    public GraphicalSolverTest() {
        this.solver = new GraphicalSolver();
    }
    
    @Test
    public void testUniqueSolution() throws Exception {
        Problem problem = new Problem();
        Constraint constraint;
        Linear linear;
        
        problem.setProblemType(ProblemType.MAX);
        
        linear = new Linear();
        linear.add(2, "x");
        linear.add(1, "y");
        problem.setObjetiveFunction(linear);
        
        linear = new Linear();
        linear.add(4, "x");
        linear.add(3, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 12);
        problem.addConstraint(constraint);
        
        linear = new Linear();
        linear.add(4, "x");
        linear.add(1, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 8);
        problem.addConstraint(constraint);
        
        linear = new Linear();
        linear.add(4, "x");
        linear.add(-1, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 8);
        problem.addConstraint(constraint);
        
        Result result = solver.solve(problem);
        
        assertThat("Problem have 1 solution", result.size(), is(1));
        
        assertThat("Optimum value is 5", result.getOptimumValue(), is(5.0));
        
        assertThat(result.getResults(), allOf(hasEntry("x", 1.5), hasEntry("y", 2.0)));
    }

    @Test
    public void testMultipleSolutions() throws Exception {
        Problem problem = new Problem();
        Constraint constraint;
        Linear linear;
        
        problem.setProblemType(ProblemType.MAX);
        
        linear = new Linear();
        linear.add(4, "x");
        linear.add(14, "y");
        problem.setObjetiveFunction(linear);
        
        linear = new Linear();
        linear.add(2, "x");
        linear.add(7, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 21);
        problem.addConstraint(constraint);
        
        linear = new Linear();
        linear.add(7, "x");
        linear.add(2, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 21);
        problem.addConstraint(constraint);
        
        Result result = solver.solve(problem);
        
        assertThat("Problem have 2 solutions", result.size(), is(2));
        
        assertThat("Optimum value is 42", result.getOptimumValue(), is(42.0));
        
        assertThat(result.getResults(0), anyOf(
            allOf(hasEntry("x", 0), hasEntry("y", 3)),
            allOf(hasEntry("x", 7/3), hasEntry("y", 7/3))
        ));
        
        assertThat(result.getResults(1), anyOf(
            allOf(hasEntry("x", 0), hasEntry("y", 3)),
            allOf(hasEntry("x", 7/3), hasEntry("y", 7/3))
        ));
    }
    
    @Test(expected = Exception.class)
    public void testNoSolutions() throws Exception {
        Problem problem = new Problem();
        Constraint constraint;
        Linear linear;
        
        problem.setProblemType(ProblemType.MAX);
        
        linear = new Linear();
        linear.add(4, "x");
        linear.add(6, "y");
        problem.setObjetiveFunction(linear);
        
        linear = new Linear();
        linear.add(3, "x");
        linear.add(4, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 12);
        problem.addConstraint(constraint);
        
        linear = new Linear();
        linear.add(3, "x");
        linear.add(5, "y");
        constraint = new Constraint(linear, Relationship.GEQ, 30);
        problem.addConstraint(constraint);
        
        linear = new Linear();
        linear.add(1, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 10);
        problem.addConstraint(constraint);
        
        Result result = solver.solve(problem);
    }
    
    @Test
    public void testUnboundSolution() throws Exception {
        Problem problem = new Problem();
        Constraint constraint;
        Linear linear;
        
        problem.setProblemType(ProblemType.MAX);
        
        linear = new Linear();
        linear.add(2, "x");
        linear.add(1, "y");
        problem.setObjetiveFunction(linear);
        
        linear = new Linear();
        linear.add(1, "x");
        linear.add(-1, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 10);
        problem.addConstraint(constraint);
        
        linear = new Linear();
        linear.add(2, "x");
        linear.add(-1, "y");
        constraint = new Constraint(linear, Relationship.GEQ, 40);
        problem.addConstraint(constraint);
        
        Result result = solver.solve(problem);
        
        assertThat("Problem have no solutions", result.size(), is(0));
        
        assertTrue("Optimum value is Double.NaN", Double.isNaN(result.getOptimumValue()));
        
        assertThat("Result have a valid Feasible Region", result.getFeasibleRegion(), notNullValue());
    }
    
}
