package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
        super();
        this.terms = new ArrayList<>();
    }
    
    /**
     * Constructs a linear expression with the predefined variables and their
     * coefficients.
     * 
     * @param coefficients The coefficients.
     * @param variables The variables.
     */
    public Linear(List<Double> coefficients, List<String> variables) {
        this();
        if (coefficients.size() != variables.size()) {
            throw new IllegalArgumentException("The size of the variables and coefficients must be equal.");
        } else {
            for (int i = 0; i < variables.size(); i++) {
                String variable = variables.get(i);
                Double coefficient = coefficients.get(i);
                Term term = new Term(variable, coefficient);
                this.add(term);
            }
        }
    }
    
    /**
     * Constructs a linear expression from the terms.
     * 
     * @param terms The terms to be added
     */
    public Linear(Iterable<Term> terms) {
        this();
        for (Term term : terms) {
            this.add(term);
        }
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
        for (Term t : this) {
            if (t.getVariable().equals(variable)) {
                return t.getCoefficient();
            }
        }
        return null;
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
     * Adds an element to the linear expression.
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
     * Returns the {@code i}-th {@code Term}.
     * 
     * @param i The index.
     * @return The i-th term.
     */
    public Term get(int i) {
        return terms.get(i);
    }
    
}
