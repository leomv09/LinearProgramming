package cr.tec.lpsolver;

/**
 *
 * @author jose
 */
public class Constraint {
    
    /**
    * The value of the coefficients assigned to each variable of the inequation, in order (x, y, z, ...).
    * Examples:
    * 5x + y - 9z = {5, 1, -9}
    * 2y - 1/4z   = {0, 2, 0.25}
    * 7x          = {7}
    */
    private double[] coefficients;
    
    /**
     * The constant value of the inequation.
     */
    private double constant;
    
    /**
     * The type of the inequation (<=, >=).
     */
    private Relationship relationship;

    public Constraint(double[] coefficients, Relationship relationship, double constant) {
        this.coefficients = coefficients;
        this.constant = constant;
        this.relationship = relationship;
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public double getConstant() {
        return constant;
    }

    public void setConstant(double constant) {
        this.constant = constant;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

}
