package cr.tec.lpsolver;

import cr.tec.lpsolver.transport.DefaultTransportAlgorithm;
import cr.tec.lpsolver.transport.TransportProblem;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author jose
 */
public class TransportProblemToProblemTest {
    
    private TransportProblem problem;
    private DefaultTransportAlgorithm problem2;
    
    @Before
    public void setUp() {
        problem = new TransportProblem();
    }
    
    @Test
    public void test_1() {
        problem.addProducer("Fab 1", 800);
        problem.addProducer("Fab 2", 1500);
        
        problem.addConsumer("A", 1000);
        problem.addConsumer("B", 700);
        problem.addConsumer("C", 600);
        
        problem.setCost("Fab 1", "A", 3);
        problem.setCost("Fab 1", "B", 7);
        problem.setCost("Fab 1", "C", 1);
        
        problem.setCost("Fab 2", "A", 2);
        problem.setCost("Fab 2", "B", 2);
        problem.setCost("Fab 2", "C", 6);
        
        problem2 = new DefaultTransportAlgorithm(problem.getCostTable(), problem.getDemand(), problem.getProduction());
        Problem p = problem2.toProblem();
        Linear fun = p.getObjetiveFunction();
        List<Constraint> cons = p.getConstraints();
        Linear linear;
        Constraint constraint;
        
        assertThat("Problem type is MIN", p.getProblemType(), is(ProblemType.MIN));
        
        assertThat("Problem have 6 constraints.", p.getConstraintsCount(), is(6));
        assertThat("Problem have 2 variables.", p.getVariablesCount(), is(2));
        
        assertThat("Function x is 6", fun.getCoefficient("x"), is(6.0));
        assertThat("Function y is 10", fun.getCoefficient("y"), is(10.0));
        
        linear = new Linear();
        linear.add(1, "x");
        constraint = new Constraint(linear, Relationship.LEQ, 1000.0);
        assertThat("Problem has constraint [x <= 1000]", cons, hasItem(constraint));
        
        linear = new Linear();
        linear.add(1, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 700.0);
        assertThat("Problem has constraint [y <= 700]", cons, hasItem(constraint));
        
        linear = new Linear();
        linear.add(1, "x");
        linear.add(1, "y");
        constraint = new Constraint(linear, Relationship.LEQ, 800.0);
        assertThat("Problem has constraint [x + y <= 800]", cons, hasItem(constraint));
        
        linear = new Linear();
        linear.add(1, "x");
        linear.add(1, "y");
        constraint = new Constraint(linear, Relationship.GEQ, 200.0);
        assertThat("Problem has constraint [x + y >= 200]", cons, hasItem(constraint));
    }
    
}
