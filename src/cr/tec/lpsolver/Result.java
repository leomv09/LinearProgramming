package cr.tec.lpsolver;

import java.util.Arrays;

/**
 *
 * @author jose
 */
public class Result {
    
    private final double[] values;
    private final double objetive;

    public Result(double[] values, double objetive) {
        this.values = values;
        this.objetive = objetive;
    }

    public double[] getValues() {
        return values;
    }
    
    public double getValue(int index) {
        return values[index];
    }

    public double getObjetive() {
        return objetive;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Result {values=");
        sb.append(Arrays.toString(values));
        sb.append(", objetive=");
        sb.append(objetive);
        sb.append("}");
        return sb.toString();
    }

}
