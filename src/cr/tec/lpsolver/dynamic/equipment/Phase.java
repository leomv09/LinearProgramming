package cr.tec.lpsolver.dynamic.equipment;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class Phase {
    
    private final PhaseRow[] rows;
    
    public Phase(Node[] nodes, Table table, int I) {
        this.rows = new PhaseRow[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            rows[i] = new PhaseRow(nodes[i], table, I);
        }
    }
    
    public Phase(Node[] nodes, Phase prev, Table table, int I) {
        this.rows = new PhaseRow[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            rows[i] = new PhaseRow(nodes[i], prev, table, I);
        }
    }
    
    public double f(int t) {
        for (PhaseRow row : rows) {
            if (row.t() == t) {
                return row.f();
            }
        }
        return 0;
    }
    
    public PhaseRow row(int t) {
        for (PhaseRow row : rows) {
            if (row.t() == t) {
                return row;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = rows.length - 1; i >=0; i--) {
            sb.append(rows[i]).append("\n");
        }
        return sb.toString();
    }
    
}
