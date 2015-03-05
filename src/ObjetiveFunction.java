/**
 *
 * @author jose
 */
public class ObjetiveFunction {

    private double[] coefficients;
    private double constant;

    public ObjetiveFunction(double[] coefficients, double constant) {
        this.coefficients = coefficients;
        this.constant = constant;
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
    
    public double evaluate(double[] values) throws IllegalArgumentException {
        if (values.length != coefficients.length) {
            throw new IllegalArgumentException("Invalid number of values. Get " + values.length + " Expect " + coefficients.length);
        }
        double result = constant;
        for (int i = 0; i < coefficients.length; i++) {
            result += (coefficients[i] * values[i]);
        }
        return result;
    }

}
