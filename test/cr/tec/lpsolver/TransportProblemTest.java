package cr.tec.lpsolver;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        System.out.println("addProducer");
        
        problem.addProducer("Producer1");
        problem.addProducer("Producer2");
        
        assertEquals(problem.getProducersCount(), 2);
    }

    /**
     * Test of addConsumer method, of class TransportProblem.
     */
    @Test
    public void testAddConsumer() {
        System.out.println("addConsumer");
        
        problem.addConsumer("Consumer1");
        problem.addConsumer("Consumer2");
        problem.addConsumer("Consumer3");
        
        assertEquals(problem.getConsumersCount(), 3);
    }

    /**
     * Test of setProduction method, of class TransportProblem.
     */
    @Test
    public void testSetProduction() {
        System.out.println("setProduction");
        
        problem.addProducer("Producer1");
        problem.addProducer("Producer2");
        
        problem.setProduction("Producer1", 290);
        problem.setProduction("Producer2", 134);
        
        assertEquals(problem.getProduction("Producer1"), 290, 0);
        assertEquals(problem.getProduction("Producer2"), 134, 0);
    }
    
    /**
     * Test of setDemand method, of class TransportProblem.
     */
    @Test
    public void testSetDemand() {
        System.out.println("setDemand");

        problem.addConsumer("Consumer1");
        problem.addConsumer("Consumer2");
        
        problem.setDemand("Consumer1", 290);
        problem.setDemand("Consumer2", 134);
        
        assertEquals(problem.getDemand("Consumer1"), 290, 0);
        assertEquals(problem.getDemand("Consumer2"), 134, 0);
    }

    /**
     * Test of setCost method, of class TransportProblem.
     */
    @Test
    public void testSetCost() {
        System.out.println("setCost");

        problem.addProducer("Producer1");
        problem.addConsumer("Consumer1");
        problem.setCost("Producer1", "Consumer1", 76);
        
        assertEquals(problem.getCost("Producer1", "Consumer1"), 76, 0);
    }

    /**
     * Test of getTotalProduction method, of class TransportProblem.
     */
    @Test
    public void testGetTotalProduction() {
        System.out.println("getTotalProduction");

        problem.addProducer("Producer1");
        problem.addProducer("Producer2");
        
        problem.setProduction("Producer1", 290);
        problem.setProduction("Producer2", 134);
        
        assertEquals(problem.getTotalProduction(), 424, 0);
    }

    /**
     * Test of getAllProduction method, of class TransportProblem.
     */
    @Test
    public void testGetEachProduction() {
        System.out.println("getEachProduction");
        
        problem.addProducer("Producer1");
        problem.addProducer("Producer2");
        
        problem.setProduction("Producer1", 290);
        problem.setProduction("Producer2", 134);
        
        Map<String, Double> result = problem.getEachProduction();
        assertEquals(result.size(), 2);
        assertEquals(result.get("Producer1"), 290, 0);
        assertEquals(result.get("Producer2"), 134, 0);
    }

    /**
     * Test of getTotalDemand method, of class TransportProblem.
     */
    @Test
    public void testGetTotalDemand() {
        System.out.println("getTotalDemand");
        
        problem.addConsumer("Consumer1");
        problem.addConsumer("Consumer2");
        
        problem.setDemand("Consumer1", 290);
        problem.setDemand("Consumer2", 134);
        
        assertEquals(problem.getTotalDemand(), 424, 0);
    }

    /**
     * Test of getAllDemand method, of class TransportProblem.
     */
    @Test
    public void testGetEachDemand() {
        System.out.println("getEachDemand");
        
        problem.addConsumer("Consumer1");
        problem.addConsumer("Consumer2");
        
        problem.setDemand("Consumer1", 290);
        problem.setDemand("Consumer2", 134);
        
        Map<String, Double> result = problem.getEachDemand();
        assertEquals(result.size(), 2);
        assertEquals(result.get("Consumer1"), 290, 0);
        assertEquals(result.get("Consumer2"), 134, 0);
    }

}
