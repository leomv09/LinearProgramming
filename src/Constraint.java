/**
 *
 * @author jose
 */
public class Constraint {
    
    private double[] coefficients;
    private double constant;
    private Relationship relationship;
    private String name;

    public Constraint(double[] coefficients, Relationship relationship, double constant, String name) {
        this.coefficients = coefficients;
        this.constant = constant;
        this.relationship = relationship;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
