package cr.tec.lpsolver;

/**
 *
 * @author jose
 */
class PLContext {
    
    private final ObjetiveFunction function;
    private final Constraint[] constraints;

    public PLContext(ObjetiveFunction function, Constraint[] constraints) {
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
