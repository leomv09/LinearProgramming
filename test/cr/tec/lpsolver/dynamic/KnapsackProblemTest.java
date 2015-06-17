package cr.tec.lpsolver.dynamic;

import cr.tec.lpsolver.dynamic.knapsack.KnapsackProblem;
import cr.tec.lpsolver.dynamic.knapsack.KnapsackResult;
import cr.tec.lpsolver.dynamic.knapsack.KnapsackSolver;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class KnapsackProblemTest {
    
    @Test
    public void testKnapsackProblem_1() throws Exception {
        KnapsackProblem problem = new KnapsackProblem(4);
        problem.addItem("Item 1", 2, 31);
        problem.addItem("Item 2", 3, 47);
        problem.addItem("Item 3", 1, 14);
        
        KnapsackSolver solver = new KnapsackSolver();
        KnapsackResult result = (KnapsackResult) solver.solve(problem);
        
        System.out.println(result);
                
        assertThat(result.getItem("Item 1").getInKnapsack(), is(2));
        assertThat(result.getItem("Item 2").getInKnapsack(), is(0));
        assertThat(result.getItem("Item 2").getInKnapsack(), is(0));
        assertThat(result.getTotalWeight(), is(4.0));
        assertThat(result.getTotalValue(), is(62.0));
    }
    
    @Test
    public void testKnapsackProblem_2() throws Exception {
        KnapsackProblem problem = new KnapsackProblem(3);
        problem.addItem("A", 1, 3);
        problem.addItem("B", 0.25, 4);
        problem.addItem("R", 0.50, 5);
        
        KnapsackSolver solver = new KnapsackSolver();
        KnapsackResult result = (KnapsackResult) solver.solve(problem);
        
        System.out.println(result);
                
        //assertThat(result.getItem("A").getInKnapsack(), is(2));
        //assertThat(result.getItem("B").getInKnapsack(), is(0));
        //assertThat(result.getItem("R").getInKnapsack(), is(0));
        //assertThat(result.getTotalWeight(), is(4.0));
        //assertThat(result.getTotalValue(), is(62.0));
    }
    
    @Test
    public void testKnapsackProblem_3() throws Exception {
        KnapsackProblem problem = new KnapsackProblem(6.0);
        problem.addItem("Item 1", 4, 70);
        problem.addItem("Item 2", 1, 20);
        problem.addItem("Item 3", 2, 40);
        
        KnapsackSolver solver = new KnapsackSolver();
        KnapsackResult result = (KnapsackResult) solver.solve(problem);
        
        System.out.println(result);
        
        assertThat(result.getTotalValue(), is(120.0));
    }
    
}
