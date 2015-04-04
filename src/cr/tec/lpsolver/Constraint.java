package cr.tec.lpsolver;

import java.util.Map;
import java.util.Objects;

/**
 * The class {@code Constraint} represent a linear constraint.
 * 
 * @author jose
 */
public class Constraint {
    
    private final Linear linear;
    private final Relationship relationship;
    private final double constant;

    /**
     * Constructs a {@code Constraint}.
     * 
     * @param linear The left hand side.
     * @param relationship The relationship.
     * @param constant The right hand side.
     */
    public Constraint(Linear linear, Relationship relationship, double constant) {
        this.linear = linear;
        this.relationship = relationship;
        this.constant = constant;
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
     * Returns the relationship.
     * 
     * @return The relationship.
     */
    public Relationship getRelationship() {
        return relationship;
    }
    
    
    /**
     * Returns the right-hand-side number.
     * 
     * @return The right-hand-side number.
     */
    public Double getConstant() {
        return constant;
    }
    
    
    /**
     * Returns the size of the linear expression.
     * 
     * @return The size.
     */
    public int size() {
        return linear.size();
    }
    
    
    /**
     * Checks if the constraint is negative.
     * 
     * @return true if the constraint is negative, false otherwise.
     */
    public boolean isNegative()
    {
        if(this.constant >= 0)
        {
            return false;
        }
        for(Term term : this.linear)
        {
            if(term.getCoefficient() >= 0)
            {
                return false;
            }
        }     
        return true;
    }
    
    /**
     * Check if the given values satisfies the constraint.
     * 
     * @param values A map that represent the values of the variables in the constraint.
     * 
     * @return True if the values satisfies the equation, false instead.
     * 
     * @throws IllegalArgumentException If less or more values than the constraint variables are supplied 
     * or a variable value is missing.
     */
    public boolean satisfies(Map<String, Double> values) throws IllegalArgumentException {
        Double evaluation = linear.evaluate(values);
        int comparation = Double.compare(evaluation, constant);
        
        switch (this.relationship) {
            case LEQ:
                return comparation <= 0;
            case GEQ:
                return comparation >= 0;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return linear + " " + relationship + " " + constant;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.linear);
        hash = 47 * hash + Objects.hashCode(this.relationship);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.constant) ^ (Double.doubleToLongBits(this.constant) >>> 32));
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
        final Constraint other = (Constraint) obj;
        if (!Objects.equals(this.linear, other.linear)) {
            return false;
        }
        if (this.relationship != other.relationship) {
            return false;
        }
        return Double.doubleToLongBits(this.constant) == Double.doubleToLongBits(other.constant);
    }
    
    
    
}
