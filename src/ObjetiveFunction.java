/**
 *
 * @author jose
 */
public class ObjetiveFunction {

    private double[] variables;
    private double constant;

    public ObjetiveFunction(double[] variables, double constant) {
        this.variables = variables;
        this.constant = constant;
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
    
    public double evaluate(double[] values) throws IllegalArgumentException {
        if (values.length != variables.length) {
            throw new IllegalArgumentException("Invalid number of values. Get " + values.length + " Expect " + variables.length);
        }
        double result = constant;
        for (int i = 0; i < variables.length; i++) {
            result += (variables[i] * values[i]);
        }
        return result;
    }

}
