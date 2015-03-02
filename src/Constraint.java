/**
 *
 * @author jose
 */
public class Constraint {
    
    private double[] variables;
    private double constant;
    private Relationship relationship;
    private String name;

    public Constraint(double[] variables, Relationship relationship, double constant, String name) {
        this.variables = variables;
        this.constant = constant;
        this.relationship = relationship;
        this.name = name;
    }

    public double[] getVariables() {
        return variables;
    }

    public void setVariables(double[] variables) {
        this.variables = variables;
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
