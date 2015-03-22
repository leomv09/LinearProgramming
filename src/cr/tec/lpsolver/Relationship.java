package cr.tec.lpsolver;

/**
 * The type of boolean operator.
 * 
 * @author jose
 */
public enum Relationship {
    EQ,
    LEQ,
    GEQ;
    
    @Override
    public String toString() {
        switch (this) {
            case EQ:
                return "=";
            case LEQ:
                return "<=";
            case GEQ:
                return ">=";
        }
        return "";
    }

}