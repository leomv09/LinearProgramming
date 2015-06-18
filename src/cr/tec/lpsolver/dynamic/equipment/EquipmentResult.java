package cr.tec.lpsolver.dynamic.equipment;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class EquipmentResult {
    
    private final String[][] decisions;
    private final double cost;
    
    public EquipmentResult(String[][] decisions, double cost) {
        this.decisions = decisions;
        this.cost = cost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total Cost: ").append(cost).append("\n");
        for (String[] row : decisions) {
            for (int i = 0; i < row.length; i++) {
                sb.append(row[i]);
                if (i != row.length - 1) {
                    sb.append(" -> ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
}
