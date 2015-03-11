package cr.tec.lpsolver;

import java.util.Map;

/**
 *
 * @author Leo
 */
public class ObjetiveFunction {

    private final Linear linear;
    
    /**
     * Constructs a {@code ObjetiveFunction} with a default constant of 0.
     * 
     * @param linear The left-hand-side linear expression.
     */
    public ObjetiveFunction(Linear linear) {
        this.linear = linear;
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
     * Evaluates the function with a given values.
     * 
     * @param values The value for each variable of the function.
     * 
     * @return The result of the evaluated function.
     * 
     * @throws IllegalArgumentException If less or more values are supplied than the function variables 
     * or a variable value is missing.
     */
    public double evaluate(Map<String, Double> values) throws IllegalArgumentException {
        if (values.size() != linear.size()) {
            throw new IllegalArgumentException("Invalid number of values. Get " + values.size() + " Expect " + linear.size());
        }
        double result = 0;
        for (Term term : linear) {
            String variable = term.getVariable();
            if (values.containsKey(variable)) {
                result += (term.getCoefficient() * values.get(variable));
            }
            else {
                throw new IllegalArgumentException("The variable " + variable + " is missing in the given values.");
            }
        }
        return result;
    }

}
