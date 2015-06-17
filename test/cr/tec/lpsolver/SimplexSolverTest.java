package cr.tec.lpsolver;

import java.util.Map;
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

    @Test
    public void testMax1() throws Exception {
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
        Map<String, Double> values = result.getResults();
        
        assertThat(result.getOptimumValue(), is(1000.0));
        assertThat(result.size(), is(1));
        assertThat(values, hasEntry("x1", 0.0));    
        assertThat(values, hasEntry("x2", 0.0));
        assertThat(values, hasEntry("x3", 100.0));
    }
    
    @Test
    public void testMax2() throws Exception {
        Problem problem = new Problem();
        Linear linear;
        
        linear = new Linear();
        linear.add(2.0, "x");
        linear.add(4.0, "y");
        problem.setObjetiveFunction(linear);
        problem.setProblemType(ProblemType.MAX);
        
        linear = new Linear();
        linear.add(1.0, "x");
        linear.add(2.0, "y");
        problem.addConstraint(linear, Relationship.LEQ, 5.0);
        
        linear = new Linear();
        linear.add(1.0, "x");
        linear.add(1.0, "y");
        problem.addConstraint(linear, Relationship.LEQ, 4.0);
        
        Result result = solver.solve(problem);
        Map<String, Double> values = result.getResults();
        
        assertThat(result.getOptimumValue(), is(10.0));
        assertThat(result.size(), is(2));
        assertThat(values, hasEntry("x", 3.0));    
        assertThat(values, hasEntry("y", 1.0));
    }
    
}
