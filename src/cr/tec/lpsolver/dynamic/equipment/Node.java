package cr.tec.lpsolver.dynamic.equipment;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class Node {
    
    private final int t; // Year
    private final boolean k; // Can keep
    private final boolean r; // Can replace

    public Node(int t, boolean k, boolean r) {
        this.t = t;
        this.k = k;
        this.r = r;
    }

    public int t() {
        return t;
    }

    public boolean canKeep() {
        return k;
    }

    public boolean canReplace() {
        return r;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.t;
        hash = 11 * hash + (this.k ? 1 : 0);
        hash = 11 * hash + (this.r ? 1 : 0);
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
        final Node other = (Node) obj;
        if (this.t != other.t) {
            return false;
        }
        if (this.k != other.k) {
            return false;
        }
        return this.r == other.r;
    }

    @Override
    public String toString() {
        return "Node{" + "t=" + t + ", k=" + k + ", r=" + r + '}';
    }

}
