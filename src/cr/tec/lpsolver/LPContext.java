package cr.tec.lpsolver;

/**
 *
 * @author jose
 */
public class LPContext {
    
    private final ObjetiveFunction function;
    private final Constraint[] constraints;

    public LPContext(ObjetiveFunction function, Constraint[] constraints) {
        this.function = function;
        this.constraints = constraints;
    }

    public ObjetiveFunction getFunction() {
        return function;
    }

    public Constraint[] getConstraints() {
        return constraints;
    }
    
}
