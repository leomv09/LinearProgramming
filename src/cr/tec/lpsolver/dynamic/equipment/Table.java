package cr.tec.lpsolver.dynamic.equipment;

import java.util.Arrays;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class Table {
    
    private final int[][] matrix;
    
    public Table(int rows, int cols) {
        this.matrix = new int[rows][cols];
    }
    
    public Table(int[][] matrix) {
        this.matrix = matrix;
    }
    
    /**
     * Set a row data.
     * 
     * @param row Row index.
     * @param r ingreso r(t)
     * @param c costo operacion c(t)
     * @param s valor recuperacion s(t)
     */
    public void setData(int row, int r, int c, int s) {
        if (0 <= row && row < matrix.length) {
            matrix[row][0] = r;
            matrix[row][1] = c;
            matrix[row][2] = s;
        }
    }
    
    public int r(int row) {
        if (0 <= row && row < matrix.length) {
            return matrix[row][0];
        }
        else {
            return 0;
        }
    }
    
    public int c(int row) {
        if (0 <= row && row < matrix.length) {
            return matrix[row][1];
        }
        else {
            return 0;
        }
    }
    
    public int s(int row) {
        if (0 <= row && row < matrix.length) {
            return matrix[row][2];
        }
        else {
            return 0;
        }
    }
    
    public int[] row(int row) {
        if (0 <= row && row < matrix.length) {
            return matrix[row];
        }
        else {
            return null;
        }
    }
    
    public int[][] table() {
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            sb.append(Arrays.toString(row)).append("\n");
        }
        return sb.toString();
    }
    
}
