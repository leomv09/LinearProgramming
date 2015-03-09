package cr.tec.lpsolver;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jose
 */
public class LPSolverTest {
    
    private final Solver solver;
    
    public LPSolverTest() {
        solver = new LPSolver();
    }

    /**
     * Test of solve method, of class LPSolver.
     */
    @Test
    public void testSolve() {
        System.out.println("Solve basic LP problem.");
        System.out.println("MAX z = x + y");
        System.out.println("Suj:");
        System.out.println("x <= 4");
        System.out.println("y <= 4");
        System.out.println("y >= x/2");

        ObjetiveFunction function = new ObjetiveFunction(new double[] {1, 1});
        
        Constraint[] constraints = new Constraint[] {
            new Constraint(new double[] {1}, Relationship.LEQ, 4),
            new Constraint(new double[] {0, 1}, Relationship.LEQ, 4),
            new Constraint(new double[] {-0.5, 1}, Relationship.GEQ, 0)
        };
        
        LPContext context = new LPContext(function, constraints);
        Result result = solver.solve(context, ProblemType.MAX);
        
        System.out.println(result);
        
        assertEquals(result.getValue(0), 4.0, 0);
        assertEquals(result.getValue(1), 4.0, 0);
    }
    
}
