package cr.tec.lpsolver.transport;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class HungarianSolver implements TransportSolver {

    @Override
    public TransportResult solve(TransportProblem problem) {
        double[][] costs = problem.getCostTable();
        double[][] assignments;
        int[] out;
        
        HungarianAlgorithm algorythm = new HungarianAlgorithm(problem.getCostTable());
        out = algorythm.execute();
        assignments = new double[out.length][out.length];
        
        for (int i = 0; i < assignments.length; i++) {
            for (int j = 0; j < assignments[i].length; j++) {
                if (out[i] == j) {
                    assignments[i][j] = costs[i][j];
                }
                else {
                    assignments[i][j] = 0;
                }
            }
        }
        
        return new TransportResult(problem.getProducers(), problem.getConsumers(), assignments);
    }
    
}
