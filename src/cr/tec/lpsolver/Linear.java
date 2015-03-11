package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The class {@code Linear} is a linear expression consisting of variables and
 * their coefficients.
 * 
 * @author jose
 */
public class Linear implements Iterable<Term> {
    
    protected final List<Term> terms;
    
    /**
     * Constructs an empty linear expression.
     */
    public Linear() {
        this.terms = new ArrayList<>();
    }

    /**
     * Returns the variables.
     * 
     * @return The variables.
     */
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        terms.stream().forEach((term) -> {
            variables.add(term.getVariable());
        });
        return variables;
    }
    
    /**
     * Returns the coefficients.
     * 
     * @return The coefficients.
     */
    public List<Double> getCoefficients() {
        List<Double> coefficients = new ArrayList<>();
        terms.stream().forEach((term) -> {
            coefficients.add(term.getCoefficient());
        });
        return coefficients;
    }
    
    /**
     * Returns the coefficient assigned to a specific variable.
     * 
     * @param variable The variable.
     * @return The coefficient.
     */
    public Double getCoefficient(String variable) {
        for (Term term : this.terms) {
            if (term.getVariable().equals(variable)) {
                return term.getCoefficient();
            }
        }
        return null;
    }
    
    /**
     * Adds an term to the linear expression.
     * 
     * @param variable The variable.
     * @param coefficient The coefficient.
     */
    public void add(Double coefficient, String variable) {
        Term term = new Term(variable, coefficient);
        this.add(term);
    }
    
    /**
     * Adds terms to the linear expression.
     * 
     * @param terms The terms to be added.
     */
    public void add(Term... terms) {
        this.terms.addAll(Arrays.asList(terms));
    }
    
    /**
     * Returns the size (number of variables) of the linear expression.
     * 
     * @return The size.
     */
    public int size() {
        return terms.size();
    }
    
    /**
     * Removes all terms.
     */
    public void clear() {
        terms.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Term> iterator() {
        return terms.iterator();
    }
    
    /**
     * Evaluates the equation with a given values.
     * 
     * @param values A map that represent the values of the variables in the equation.
     * 
     * @return The result.
     * 
     * @throws IllegalArgumentException If less or more values than the equation variables are supplied 
     * or a variable value is missing.
     */
    public double evaluate(Map<String, Double> values) throws IllegalArgumentException {
        if (values.size() != terms.size()) {
            throw new IllegalArgumentException("Invalid number of values. Get " + values.size() + " Expect " + terms.size());
        }
        double result = 0;
        for (Term term : terms) {
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
