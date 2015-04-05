package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The class {@code Problem} represents a linear problem consisting of multiple
 * constraints and up to one objective function.
 * 
 * @author jose
 */
public class Problem {

    private final List<Constraint> constraints;
    private final Set<String> variables;
    private Linear objetiveFunction;
    private ProblemType type;

    /**
     * Constructs a {@code Problem}.
     */
    public Problem() {
        this.constraints = new ArrayList<>();
        this.variables = new HashSet<>();
        this.objetiveFunction = new Linear();
        this.type = ProblemType.MAX;
    }

    /**
     * Returns the objective function.
     * 
     * @return The objective function.
     */
    public Linear getObjetiveFunction() {
        return objetiveFunction;
    }
    
    /**
     * Sets the objective function.
     * 
     * @param linear The left-hand-side linear expression of the function.
     */
    public void setObjetiveFunction(Linear linear) {
        this.objetiveFunction = linear;
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
     * Returns the variables.
     * 
     * @return the variables.
     */
   public List<String> getVariables() {
        List<String> l = new ArrayList<>(variables);
        Collections.sort(l);
        return l;
   }

    /**
     * Returns the number of variables.
     * 
     * @return the number of variables.
     */
    public int getVariablesCount() {
        return variables.size();
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
        for (Term term : constraint.getLinear()) {
            variables.add(term.getVariable());
        }
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
        addConstraint(constraint);
    }
    
    /**
     * Add a collection of constraints.
     * 
     * @param constraints The constraints.
     */
    public void addConstraints(Collection<Constraint> constraints) {
        for (Constraint constraint : constraints) {
            addConstraint(constraint);
        }
    }
    
    /**
     * Add an array of constraints.
     * 
     * @param constraints The constraints.
     */
    public void addConstraints(Constraint... constraints) {
        for (Constraint constraint : constraints) {
            addConstraint(constraint);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name()).append(" ").append(objetiveFunction).append("\n");
        sb.append("Suj:\n");
        for (Constraint constraint : constraints) {
            sb.append("    ").append(constraint).append("\n");
        }
        return sb.toString();
    }

}
