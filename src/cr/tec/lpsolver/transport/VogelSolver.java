package cr.tec.lpsolver.transport;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class VogelSolver implements TransportSolver {

    @Override
    public TransportResult solve(TransportProblem problem) throws Exception {
        VogelAlgorithm algorithm = new VogelAlgorithm(problem.getDemand(), problem.getProduction(), problem.getCostTable());
        double[][] assignments = algorithm.execute();
        return new TransportResult(problem.getProducers(), problem.getConsumers(), assignments);
    }
    
}
