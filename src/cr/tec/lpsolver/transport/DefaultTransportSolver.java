package cr.tec.lpsolver.transport;


public class DefaultTransportSolver implements TransportSolver {

    
    @Override
    public TransportResult solve(TransportProblem problem) throws Exception 
    {
        System.out.println(problem);
        DefaultTransportAlgorithm algorithm = new DefaultTransportAlgorithm(problem.getCostTable(), problem.getDemand(), problem.getProduction());
        double[][] shippingTable = algorithm.execute();
        return new TransportResult(problem.getProducers(), problem.getConsumers(), shippingTable);
    }
    
}
