package cr.tec.lpsolver;

import java.util.logging.Level;
import java.util.logging.Logger;
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

        System.out.println(problem);
        try {
            Result result = solver.solve(problem);
        } catch (Exception ex) {
            Logger.getLogger(SimplexSolverTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
