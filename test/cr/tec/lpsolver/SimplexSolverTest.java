package cr.tec.lpsolver;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jose
 */
public class SimplexSolverTest {
    
    private final Solver solver;
    
    public SimplexSolverTest() {
        solver = new SimplexSolver();
    }

    /**
     * Test of solve method, of class SimplexSolver.
     */
    @Test
    public void testMax() {
        System.out.println("Solve a Maximization Simplex Problem");
        System.out.println("Max. 7x1 + 8x2 + 10x3");
        System.out.println("s.a.");
        System.out.println("2x1 + 3x2 + 2x3 <= 1000");
        System.out.println("x1 + x2 + 2x3 <= 800");
        System.out.println("x1 + x2 + x3 <= 100");
        System.out.println("x1, x2, x3 >= 0");
        
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
