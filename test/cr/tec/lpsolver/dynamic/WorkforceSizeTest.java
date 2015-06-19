/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic;

import cr.tec.lpsolver.dynamic.workforce.WorkForceSizeAlgorithm;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Leo
 */
public class WorkforceSizeTest {
    WorkForceSizeAlgorithm algo;
    
    @Before
    public void setUp() 
    {
        List<Integer> currentWorkers = new ArrayList<>();
        currentWorkers.add(9);
        currentWorkers.add(8);
        currentWorkers.add(6);
        currentWorkers.add(7);
        currentWorkers.add(8);
        currentWorkers.add(9);
        currentWorkers.add(5);
        currentWorkers.add(7);
        currentWorkers.add(8);
        currentWorkers.add(9);
        
        
        algo = new WorkForceSizeAlgorithm(10, 400, 200, 100, currentWorkers);
    }
    
    @Test
    public void content()
    {
        algo.setStages();
        assertThat("Stages: ", algo.getStages().size(), is(5));
        assertThat("Max value is: ", algo.getMaxValue(), is(8));
    }
    
    @Test
    public void solution()
    {
        algo.solve();
        //assertThat(algo.getStages().get(0).getOptimumForce(algo.getStages().size() - 1).size(), is(1));
        assertThat(algo.getStages().get(algo.getStages().size() - 1).getAggregateValue(0), is(3600.0));
    }
}
