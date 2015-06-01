package cr.tec.lpsolver.transport;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public interface TransportSolver {
    
    public abstract TransportResult solve(TransportProblem problem);
    
}
