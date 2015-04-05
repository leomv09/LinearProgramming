package cr.tec.lpsolver;

/**
 * The type of boolean operator.
 * 
 * @author jose
 */
public enum Relationship {
    LEQ,
    GEQ;
    
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
            default:
                return "";
        }
    }

}