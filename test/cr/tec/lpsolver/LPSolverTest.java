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
    public void test1() {
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
        System.out.println();
        
        assertEquals(result.getValue(0), 4.0, 0);
        assertEquals(result.getValue(1), 4.0, 0);
    }
    
    /**
     * Test of solve method, of class LPSolver.
     */
    @Test
    public void test2() {
        System.out.println("Solve basic LP problem.");
        System.out.println("MAX z = -2x + 5y");
        System.out.println("Suj:");
        System.out.println("100 <= x <= 200");
        System.out.println("80 <= y <= 170");
        System.out.println("y >= -x + 200");

        ObjetiveFunction function = new ObjetiveFunction(new double[] {-2, 5});
        
        Constraint[] constraints = new Constraint[] {
            new Constraint(new double[] {1}, Relationship.GEQ, 100),
            new Constraint(new double[] {1}, Relationship.LEQ, 200),
            new Constraint(new double[] {0, 1}, Relationship.GEQ, 80),
            new Constraint(new double[] {0, 1}, Relationship.LEQ, 170),
            new Constraint(new double[] {1, 1}, Relationship.GEQ, 200)
        };
        
        LPContext context = new LPContext(function, constraints);
        Result result = solver.solve(context, ProblemType.MAX);
        
        System.out.println(result);
        System.out.println();
        
        assertEquals(result.getValue(0), 100.0, 0);
        assertEquals(result.getValue(1), 170.0, 0);
    }
    
}
