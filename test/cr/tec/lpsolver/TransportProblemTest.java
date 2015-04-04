package cr.tec.lpsolver;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.Map;

/**
 *
 * @author jose
 */
public class TransportProblemTest {
    
    private TransportProblem problem;
    
    @Before
    public void setUp() {
        problem = new TransportProblem();
    }

    /**
     * Test of addProducer method, of class TransportProblem.
     */
    @Test
    public void testAddProducer() {
        problem.addProducer("Producer1");
        problem.addProducer("Producer2");
        
        assertThat(problem.getProducersCount(), is(2));
    }

    /**
     * Test of addConsumer method, of class TransportProblem.
     */
    @Test
    public void testAddConsumer() {
        problem.addConsumer("Consumer1");
        problem.addConsumer("Consumer2");
        problem.addConsumer("Consumer3");
        
        assertThat(problem.getConsumersCount(), is(3));
    }

    /**
     * Test of setProduction method, of class TransportProblem.
     */
    @Test
    public void testSetProduction() {
        problem.addProducer("Producer1");
        problem.addProducer("Producer2");
        
        problem.setProduction("Producer1", 290);
        problem.setProduction("Producer2", 134);
        
        assertThat(problem.getProduction("Producer1"), is(290.0));
        assertThat(problem.getProduction("Producer2"), is(134.0));
    }
    
    /**
     * Test of setDemand method, of class TransportProblem.
     */
    @Test
    public void testSetDemand() {
        problem.addConsumer("Consumer1");
        problem.addConsumer("Consumer2");
        
        problem.setDemand("Consumer1", 290);
        problem.setDemand("Consumer2", 134);
        
        assertThat(problem.getDemand("Consumer1"), is(290.0));
        assertThat(problem.getDemand("Consumer2"), is(134.0));
    }

    /**
     * Test of setCost method, of class TransportProblem.
     */
    @Test
    public void testSetCost() {
        problem.addProducer("Producer1");
        problem.addConsumer("Consumer1");
        problem.setCost("Producer1", "Consumer1", 76);
        
        assertThat(problem.getCost("Producer1", "Consumer1"), is(76.0));
    }

    /**
     * Test of getTotalProduction method, of class TransportProblem.
     */
    @Test
    public void testGetTotalProduction() {
        problem.addProducer("Producer1");
        problem.addProducer("Producer2");
        
        problem.setProduction("Producer1", 290);
        problem.setProduction("Producer2", 134);

        assertThat(problem.getTotalProduction(), is(424.0));
    }

    /**
     * Test of getAllProduction method, of class TransportProblem.
     */
    @Test
    public void testGetEachProduction() {
        problem.addProducer("Producer1");
        problem.addProducer("Producer2");
        
        problem.setProduction("Producer1", 290);
        problem.setProduction("Producer2", 134);
        
        Map<String, Double> result = problem.getEachProduction();
        
        assertThat(result, allOf(
            hasEntry("Producer1", 290.0),
            hasEntry("Producer2", 134.0))
        );
    }

    /**
     * Test of getTotalDemand method, of class TransportProblem.
     */
    @Test
    public void testGetTotalDemand() {
        problem.addConsumer("Consumer1");
        problem.addConsumer("Consumer2");
        
        problem.setDemand("Consumer1", 290);
        problem.setDemand("Consumer2", 134);
        
        assertThat(problem.getTotalDemand(), is(424.0));
    }

    /**
     * Test of getAllDemand method, of class TransportProblem.
     */
    @Test
    public void testGetEachDemand() {
        problem.addConsumer("Consumer1");
        problem.addConsumer("Consumer2");
        
        problem.setDemand("Consumer1", 290);
        problem.setDemand("Consumer2", 134);
        
        Map<String, Double> result = problem.getEachDemand();
        
        assertThat(result, allOf(
            hasEntry("Consumer1", 290.0),
            hasEntry("Consumer2", 134.0))
        );
    }

}
