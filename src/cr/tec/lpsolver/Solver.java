package cr.tec.lpsolver;

/**
 * A generic {@link Problem} {@code Solver}.
 * 
 * @author Leo
 */
public interface Solver {
    
    /**
     * Solve a optimization problem.
     *
     * @param problem The optimization problem.
     * 
     * @return The result of the problem or {@code null} if there exists no
     * feasible solution.
     */
    public abstract Result solve(Problem problem);
    
}
