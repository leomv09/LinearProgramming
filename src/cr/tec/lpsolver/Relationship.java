package cr.tec.lpsolver;

/**
 * The type of boolean operator.
 * 
 * @author jose
 */
public enum Relationship {
    LEQ,
    GEQ,
    EQ;
    
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        switch (this) {
            case LEQ:
                return "<=";
            case GEQ:
                return ">=";
            case EQ: 
                return "=";
            default:
                return "";
        }
    }

}