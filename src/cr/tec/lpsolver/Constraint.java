package cr.tec.lpsolver;

/**
 * The class {@code Constraint} represent a linear constraint.
 * 
 * @author jose
 */
public class Constraint {
    
    private final Linear linear;
    private final Relationship relationship;
    private final Double constant;

    /**
     * Constructs a {@code Constraint}.
     * 
     * @param linear The left hand side.
     * @param relationship The relationship.
     * @param constant The right hand side.
     */
    public Constraint(Linear linear, Relationship relationship, Double constant) {
        this.linear = linear;
        this.relationship = relationship;
        this.constant = constant;
    }

    /**
     * Returns the left-hand-side linear expression.
     * 
     * @return The left-hand-side linear expression.
     */
    public Linear getLinear() {
        return linear;
    }
    
    /**
     * Returns the relationship.
     * 
     * @return The relationship.
     */
    public Relationship getRelationship() {
        return relationship;
    }
    
    /**
     * Returns the right-hand-side number.
     * 
     * @return The right-hand-side number.
     */
    public Double getConstant() {
        return constant;
    }
    
    /**
     * Returns the size of the linear expression.
     * 
     * @return The size.
     */
    public int size() {
        return linear.size();
    }

}
