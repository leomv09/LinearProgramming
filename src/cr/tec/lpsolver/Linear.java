package cr.tec.lpsolver;

/**
 * Linear equation in the form a1x1 + a2x2 + a3x3 + ... + anxn = c
 * 
 * @author jose
 */
public class Linear {
    
    /**
    * The value of the coefficients assigned to each variable of the equation, in order (x1, x2, x3, ...).
    * Examples:
    * 5x + y - 9z = {5, 1, -9}
    * 2y - 1/4z   = {0, 2, 0.25}
    * 7x          = {7}
    */
    protected double[] coefficients;
    
    /**
     * The constant value of the equation.
     */
    protected double constant;

    public Linear(double[] coefficients, double constant) {
        this.coefficients = coefficients;
        this.constant = constant;
    }
    
    public Linear(double[] coefficients) {
        this.coefficients = coefficients;
        this.constant = 0;
    }
    
    public double[] getCoefficients() {
        return coefficients;
    }
    
    public double getCoefficient(int index) {
        return coefficients[index];
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
    
}
