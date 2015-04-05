package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The class {@code Linear} is a linear expression consisting of variables and
 * their coefficients.
 * 
 * @author jose
 */
public class Linear implements Iterable<Term> {
    
    private final List<Term> terms;
    
    /**
     * Constructs an empty linear expression.
     */
    public Linear() {
        this.terms = new ArrayList<>();
    }

    /**
     * Adds a linear object with another one, and returns the result as a new linear object.
     * 
     * @param linear The linear object to add.
     * 
     * @return a new Linear object with the result of the addition.
     */
    public Linear linearAddition(Linear linear)
    {
        Linear newLinear = new Linear();
        List<Term> termsToremove = new ArrayList<>();
        for(Term term : linear.getTerms())
        {
            for(Term term2 : this.getTerms())
            {
                if(!termsToremove.contains(term2))
                {
                    if(term.getVariable() != null)
                    {
                        if(term2.getVariable() != null)
                        {
                            if(term2.getVariable().equals(term.getVariable()))//Adding terms with same variable.
                            {
                                newLinear.add(term.getCoefficient() + term2.getCoefficient(), term.getVariable());
                                termsToremove.add(term2);
                                termsToremove.add(term);
                            }
                        }
                    }
                    else//Adding terms that don't have variables.
                    {
                        if(term2.getVariable() == null)
                        {
                            newLinear.add(term.getCoefficient() + term2.getCoefficient(), null);
                            termsToremove.add(term2);
                            termsToremove.add(term);
                        }
                    }
                }
            }
        }
        //If there are remaining terms.
        for(Term term : linear.getTerms())
        {
            if(!termsToremove.contains(term))
            {
                newLinear.add(term.getCoefficient(), term.getVariable());
            }  
        }
        for(Term term : this.getTerms())
        {
            if(!termsToremove.contains(term))
            {
                newLinear.add(term.getCoefficient(), term.getVariable());
            }
        }
    return newLinear;
    }
    
    /**
     * Obtains the times (*) operation  between a number and a linear object.
     * 
     * @param number The double number to be multiplied for the linear object.
     * 
     * @return The multiplication between the number and each of the terms of the linear object.
     */
    public Linear numberTimesLinear(double number)
    {
        Linear linear = new Linear();
        
        for (Term term : this.terms)
        {
            linear.add(number * term.getCoefficient(), term.getVariable());
        }
        
        return linear;
    }
    
    /**
     * Returns the variables.
     * 
     * @return The variables.
     */
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        for (Term term : terms) {
            variables.add(term.getVariable());
        }
        return variables;
    }
    
    /**
     * Returns the coefficients.
     * 
     * @return The coefficients.
     */
    public List<Double> getCoefficients() {
        List<Double> coefficients = new ArrayList<>();
        for (Term term : terms) {
            coefficients.add(term.getCoefficient());
        }
        return coefficients;
    }
    
    /**
     * Returns the coefficient assigned to a specific variable.
     * 
     * @param variable The variable.
     * 
     * @return The coefficient Double.NaN is returned if the variable don't exist in the linear expression.
     */
    public double getCoefficient(String variable) {
        for (Term term : this.terms) {
            if (term.getVariable().equals(variable)) {
                return term.getCoefficient();
            }
        }
        return Double.NaN;
    }
    
    /**
     * Obtains the terms.
     * 
     * @return The terms.
     */
    public List<Term> getTerms()
    {
        return terms;
    }
    
    /**
     * Get the term with a given variable.
     * 
     * @param variable The variable.
     * @return The term or null if no term have that variable.
     */
    public Term getTerm(String variable) {
        for (Term term : terms) {
            if (term.getVariable().equalsIgnoreCase(variable)) {
                return term;
            }
        }
        return null;
    }
    
    /**
     * Add a term to the linear expression.
     * 
     * @param term The term.
     * @return True if the linear expression changes after this call.
     */
    public boolean add(Term term) {
        Term t = getTerm(term.getVariable());
        
        if (t != null) {
            terms.remove(t);
            double coefficient = term.getCoefficient() + t.getCoefficient();
            if (coefficient != 0) {
                terms.add(new Term(term.getVariable(), coefficient));
                return true;
            }
        }
        else if (term.getCoefficient() != 0 && term.getVariable() != null) {
            terms.add(term);
            return true;
        }

        return false;
    }
    
    /**
     * Adds a term to the linear expression.
     * 
     * @param variable The variable.
     * @param coefficient The coefficient.
     * @return True if the linear expression changes after this call.
     */
    public boolean add(double coefficient, String variable) {
        Term term = new Term(variable, coefficient);
        return add(term);
    }
    
    /**
     * Adds terms to the linear expression.
     * 
     * @param terms The terms to be added.
     * @return True if the linear expression changes after this call.
     */
    public boolean add(Term... terms) {
        boolean changed = false;
        for (Term term : terms) {
            changed |= add(term);
        }
        return changed;
    }

    /**
     * Check if the linear expression contains the given variable.
     * 
     * @param variable The variable.
     * @return True if the linear expression contains the variable.
     */
    public boolean containsVariable(String variable) {
        for (Term term : this.terms) {
            if (term.getVariable().equals(variable)) {
                return true;
            }
        }
        return false;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Term> iterator() {
        return terms.iterator();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            sb.append(terms.get(i));
            if (i + 1 < terms.size()) {
                sb.append(" + ");
            }
        }
        return sb.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.terms);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals()
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Linear other = (Linear) obj;
        return Objects.equals(this.terms, other.terms);
    }
    
}
