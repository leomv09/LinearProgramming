package cr.tec.lpsolver;

/**
 *
 * @author jose
 */
public class ObjetiveFunction extends Linear {

    public ObjetiveFunction(double[] coefficients, double constant) {
        super(coefficients, constant);
    }
    
    public ObjetiveFunction(double[] coefficients) {
        super(coefficients);
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
