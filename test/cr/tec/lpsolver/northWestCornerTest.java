/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver;

import cr.tec.lpsolver.transport.NorthWestCornerAlgorithm;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import cr.tec.lpsolver.transport.TransportProblem;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Leo
 */
public class northWestCornerTest {
    private TransportProblem problem;
    
    @Before
    public void setUp() {
        problem = new TransportProblem();
    }

    /**
     * Test of setProduction method, of class TransportProblem.
     */
    @Test
    public void test()
    {
        problem.addProducer("Producer1", 100.0);
        problem.addProducer("Producer2", 120.0);
        problem.addProducer("Producer3", 80.0);
        problem.addProducer("Producer4", 95.0);
        
        problem.addConsumer("A", 125.0);
        problem.addConsumer("B", 50.0);
        problem.addConsumer("C", 130.0);
        problem.addConsumer("D", 90.0);
        
        problem.setCost("Producer1", "A", 2);
        problem.setCost("Producer1", "B", 3);
        problem.setCost("Producer1", "C", 4);
        problem.setCost("Producer1", "D", 6);
        
        problem.setCost("Producer2", "A", 1);
        problem.setCost("Producer2", "B", 5);
        problem.setCost("Producer2", "C", 8);
        problem.setCost("Producer2", "D", 3);
        
        problem.setCost("Producer3", "A", 8);
        problem.setCost("Producer3", "B", 5);
        problem.setCost("Producer3", "C", 1);
        problem.setCost("Producer3", "D", 4);
        
        problem.setCost("Producer4", "A", 4);
        problem.setCost("Producer4", "B", 5);
        problem.setCost("Producer4", "C", 6);
        problem.setCost("Producer4", "D", 3);
        
        NorthWestCornerAlgorithm algo = new NorthWestCornerAlgorithm (problem.getCostTable(), problem.getProduction(), problem.getDemand());
        try 
        {
            algo.execute();
            assertThat("Total cost is 1215", algo.getTotalCost(), is(1215.0));
            System.out.println("Total cost: " + algo.getTotalCost());
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(northWestCornerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
