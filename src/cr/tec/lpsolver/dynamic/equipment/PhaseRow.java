package cr.tec.lpsolver.dynamic.equipment;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class PhaseRow {
    
    private int t;
    private double k;
    private double r;
    private double f;
    
    private PhaseRow(Node node) {
        this.t = node.t();
        this.k = -1;
        this.r = -1;
    }
    
    public PhaseRow(Node node, Table table, int I) {
        this(node);
        if (node.canKeep()) {
            this.k = table.r(t) + table.s(t + 1) - table.c(t);
        }
        if (node.canReplace()) {
            this.r = table.r(0) + table.s(t) + table.s(1) - table.c(0) - I;
        }
        calculateDecision();
    }
    
    public PhaseRow(Node node, Phase prev, Table table, int I) {
        this(node);
        if (node.canKeep()) {
            this.k = table.r(t) - table.c(t) + prev.f(t + 1);
        }
        if (node.canReplace()) {
            this.r = table.r(0) + table.s(t) - table.c(0) - I + prev.f(1);
        }
        calculateDecision();
    }
    
    private void calculateDecision() {
        f = (k > r) ? k : r; 
    }

    public int t() {
        return t;
    }

    public double k() {
        return k;
    }

    public double r() {
        return r;
    }

    public double f() {
        return f;
    }

    @Override
    public String toString() {
        return "t=" + t + ", k=" + k + ", r=" + r + ", f=" + f;
    }
    
}
