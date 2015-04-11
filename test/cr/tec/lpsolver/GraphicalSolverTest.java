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
        linear.add(5, "x");
        linear.add(4, "y");
        problem.setObjetiveFunction(linear);
        
        linear = new Linear();
        linear.add(6, "x");
        linear.add(4, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 24);
        problem.addConstraint(constraint);
        
        linear = new Linear();
        linear.add(1, "x");
        linear.add(2, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 6);
        problem.addConstraint(constraint);
        
        linear = new Linear();
        linear.add(-1, "x");
        linear.add(1, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 1);
        problem.addConstraint(constraint);
        
        linear = new Linear();
        linear.add(1, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 2);
        problem.addConstraint(constraint);
        
        Result result = solver.solve(problem);
        
        assertThat("Problem have 1 solution", result.size(), is(1));
        
        assertThat("Optimum value is 21", result.getOptimumValue(), is(21.0));
        
        assertThat(result.getResults(), allOf(
            hasEntry(equalToIgnoringCase("x"), closeTo(3.0, 0.001)),
            hasEntry(equalToIgnoringCase("y"), closeTo(1.5, 0.001))
        ));
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
        
        assertThat("Problem has 2 solutions", result.size(), is(2));
        
        assertThat("Optimum value is 42", result.getOptimumValue(), is(42.0));

        System.out.println(result.getResults(0));
        System.out.println(result.getResults(1));
        
        assertThat(result.getResults(0), anyOf(
            allOf(
                hasEntry(equalTo("x"), closeTo(0, 0.001)),
                hasEntry(equalTo("y"), closeTo(3, 0.001))
            ),
            allOf(
                hasEntry(equalTo("x"), closeTo(7/3, 0.001)),
                hasEntry(equalTo("y"), closeTo(7/3, 0.001))
            )
        ));
        
        assertThat(result.getResults(1), anyOf(
            allOf(
                hasEntry(equalTo("x"), closeTo(0, 0.001)),
                hasEntry(equalTo("y"), closeTo(3, 0.001))
            ),
            allOf(
                hasEntry(equalTo("x"), closeTo(7/3, 0.001)),
                hasEntry(equalTo("y"), closeTo(7/3, 0.001))
            )
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
        
        solver.solve(problem);
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
        
        assertThat("Problem has no solutions", result.size(), is(0));
        
        assertTrue("Optimum value is Double.NaN", Double.isNaN(result.getOptimumValue()));
        
        assertThat("Result have a valid Feasible Region", result.getFeasibleRegion(), notNullValue());
    }
    
}
