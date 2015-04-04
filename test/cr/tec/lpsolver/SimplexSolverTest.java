package cr.tec.lpsolver;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author jose
 */
public class SimplexSolverTest {
    
    private final Solver solver;
    
    public SimplexSolverTest() {
        solver = new SimplexSolver();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMax() throws Exception {
        Problem problem = new Problem();
        Linear linear;
        
        linear = new Linear();
        linear.add(7.0, "x1");
        linear.add(8.0, "x2");
        linear.add(10.0, "x3");
        problem.setObjetiveFunction(linear);
        problem.setProblemType(ProblemType.MAX);
        
        linear = new Linear();
        linear.add(2.0, "x1");
        linear.add(3.0, "x2");
        linear.add(2.0, "x3");
        problem.addConstraint(linear, Relationship.LEQ, 1000.0);
        
        linear = new Linear();
        linear.add(1.0, "x1");
        linear.add(1.0, "x2");
        linear.add(2.0, "x3");
        problem.addConstraint(linear, Relationship.LEQ, 800.0);
        
        linear = new Linear();
        linear.add(1.0, "x1");
        linear.add(1.0, "x2");
        linear.add(1.0, "x3");
        problem.addConstraint(linear, Relationship.LEQ, 100.0);

        Result result = solver.solve(problem);
    }
    
}
