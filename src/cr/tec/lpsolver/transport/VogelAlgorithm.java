package cr.tec.lpsolver.transport;

import static java.util.Arrays.stream;
 
public class VogelAlgorithm {
 
    private final double[] demand;
    private final double[] supply;
    private final double[][] costs;
 
    private final int nRows;
    private final int nCols;
 
    private final boolean[] rowDone;
    private final boolean[] colDone;
    private final double[][] result;
 
    
    public VogelAlgorithm(double[] demand, double[] supply, double[][] costs) {
        this.supply = supply;
        this.demand = demand;
        this.costs = costs;
        this.nRows = supply.length;
        this.nCols = demand.length;
        this.rowDone = new boolean[nRows];
        this.colDone = new boolean[nCols];
        this.result = new double[nRows][nCols];
    }
    
    double[][] execute() throws Exception {
        double supplyLeft = stream(supply).sum();
 
        while (supplyLeft > 0) {
            int[] cell = nextCell();
            int r = cell[0];
            int c = cell[1];
 
            double quantity = Math.min(demand[c], supply[r]);
            demand[c] -= quantity;
            if (demand[c] == 0)
                colDone[c] = true;
 
            supply[r] -= quantity;
            if (supply[r] == 0)
                rowDone[r] = true;
 
            result[r][c] = quantity;
            supplyLeft -= quantity;
        }
        
        return result;
    }
 
    int[] nextCell() throws Exception {
        int[] res1 = maxPenalty(nRows, nCols, true);
        int[] res2 = maxPenalty(nCols, nRows, false);
 
        if (res1[3] == res2[3])
            return res1[2] < res2[2] ? res1 : res2;
 
        return (res1[3] > res2[3]) ? res2 : res1;
    }
 
    int[] diff(int j, int len, boolean isRow) {
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int minP = -1;
        for (int i = 0; i < len; i++) {
            if (isRow ? colDone[i] : rowDone[i])
                continue;
            int c = isRow ? (int) costs[j][i] : (int) costs[i][j];
            if (c < min1) {
                min2 = min1;
                min1 = c;
                minP = i;
            } else if (c < min2)
                min2 = c;
        }
        return new int[]{min2 - min1, min1, minP};
    }
 
    int[] maxPenalty(int len1, int len2, boolean isRow) {
        int md = Integer.MIN_VALUE;
        int pc = -1, pm = -1, mc = -1;
        for (int i = 0; i < len1; i++) {
            if (isRow ? rowDone[i] : colDone[i])
                continue;
            int[] res = diff(i, len2, isRow);
            if (res[0] > md) {
                md = res[0];  // max diff
                pm = i;       // pos of max diff
                mc = res[1];  // min cost
                pc = res[2];  // pos of min cost
            }
        }
        return isRow ? new int[]{pm, pc, mc, md} : new int[]{pc, pm, mc, md};
    }
    
}