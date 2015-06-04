package cr.tec.lpsolver.transport;

public class NorthWestCornerSolver implements TransportSolver {

    @Override
    public TransportResult solve(TransportProblem problem) throws Exception {
        NorthWestCornerAlgorithm cornerAlgorithm = new NorthWestCornerAlgorithm(problem.getCostTable(), problem.getProduction(), problem.getDemand());
        
        double[][] shippingTable = cornerAlgorithm.execute();
        return new TransportResult(problem.getProducers(), problem.getConsumers(), shippingTable);
    }
    
}
