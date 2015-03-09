package cr.tec.lpsolver;

/**
 *
 * @author jose
 */
public class Constraint extends Linear {
    
    /**
     * The type of the inequation (<=, >=).
     */
    private Relationship relationship;

    public Constraint(double[] coefficients, Relationship relationship, double constant) {
        super(coefficients, constant);
        this.relationship = relationship;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

}
