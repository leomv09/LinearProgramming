package cr.tec.lpsolver;

import java.util.Objects;

/**
 * The {@code Term} is the basic element the {@link Linear}. 
 * It is a coefficient and its variable.
 * 
 * @author jose
 */
public class Term {
    
    private final String variable;
    private final double coefficient;

    /**
     * Constructs a {@code Term}.
     * 
     * @param variable The variable.
     * @param coefficient The coefficient.
     *            
     */
    public Term(String variable, double coefficient) throws IllegalArgumentException {
        if (coefficient == 0) {
            throw new IllegalArgumentException("Cannot create a term with coefficient zero.");
        }
        if (variable == null) {
            throw new IllegalArgumentException("Cannot create a term with no variable.");
        }
        this.variable = variable;
        this.coefficient = coefficient;
    }

    /**
     * Returns the variable.
     * 
     * @return the variable
     */
    public String getVariable() {
        return variable;
    }

    /**
     * Returns the coefficient.
     * 
     * @return the coefficient
     */
    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.variable);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.coefficient) ^ (Double.doubleToLongBits(this.coefficient) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Term other = (Term) obj;
        if (!this.variable.equalsIgnoreCase(other.variable)) {
            return false;
        }
        return Double.doubleToLongBits(this.coefficient) == Double.doubleToLongBits(other.coefficient);
    }

    @Override
    public String toString() {
        return coefficient + variable;
    }
    
}
