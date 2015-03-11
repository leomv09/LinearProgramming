package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.List;

/**
 * The class {@code Problem} represents a linear problem consisting of multiple
 * constraints and up to one objective function.
 * 
 * @author jose
 */
public class Problem {

    private ObjetiveFunction function;
    private final List<Constraint> constraints;
    private ProblemType type;

    /**
     * Constructs a {@code Problem}.
     */
    public Problem() {
        this.constraints = new ArrayList<>();
        this.type = ProblemType.MAX;
    }

    /**
     * Returns the objective function.
     * 
     * @return The objective function.
     */
    public ObjetiveFunction getObjetiveFunction() {
        return function;
    }

    /**
     * Sets the objective function.
     * 
     * @param function The objective function.
     */
    public void setObjetiveFunction(ObjetiveFunction function) {
        this.function = function;
    }
    
    /**
     * Sets the objective function.
     * 
     * @param linear The left-hand-side linear expression of the function.
     */
    public void setObjetiveFunction(Linear linear) {
        this.function = new ObjetiveFunction(linear);
    }

    /**
     * Returns the constraints.
     * 
     * @return The constraints.
     */
    public List<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * Returns the number of constraints.
     * 
     * @return The number of constraints.
     */
    public int getConstraintsCount() {
        return constraints.size();
    }

    /**
     * Returns the optimization type (MIN - MAX).
     * 
     * @return The optimization type.
     */
    public ProblemType getProblemType() {
        return type;
    }

    /**
     * Sets the optimization type (MIN - MAX).
     * 
     * @param type The optimization type.
     */
    public void setProblemType(ProblemType type) {
        this.type = type;
    }

    /**
     * Adds a constraint.
     * 
     * @param constraint The constraint to be added.
     */
    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    /**
     * Adds a constraint.
     * 
     * @param linear The left-hand-side linear expression.
     * @param relationship The relationship.
     * @param constant The right-hand-side number.
     */
    public void addConstraint(Linear linear, Relationship relationship, Double constant) {
        Constraint constraint = new Constraint(linear, relationship, constant);
        this.addConstraint(constraint);
    }

}
