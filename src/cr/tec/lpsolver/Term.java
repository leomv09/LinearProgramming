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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.variable);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.coefficient) ^ (Double.doubleToLongBits(this.coefficient) >>> 32));
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
        final Term other = (Term) obj;
        if (!this.variable.equalsIgnoreCase(other.variable)) {
            return false;
        }
        return Double.doubleToLongBits(this.coefficient) == Double.doubleToLongBits(other.coefficient);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return coefficient + variable;
    }
    
}
