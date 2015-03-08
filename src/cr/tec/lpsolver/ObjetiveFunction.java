package cr.tec.lpsolver;

/**
 *
 * @author jose
 */
public class ObjetiveFunction {

    /**
    * The value of the coefficients assigned to each variable of the function, in order (x, y, z, ...).
    * Examples:
    * 5x + y - 9z = {5, 1, -9}
    * 2y - 1/4z   = {0, 2, 0.25}
    * 7x          = {7}
    */
    private double[] coefficients;
    
    /**
     * The constant value of the function.
     */
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
