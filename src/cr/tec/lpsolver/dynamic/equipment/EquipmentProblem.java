package cr.tec.lpsolver.dynamic.equipment;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class EquipmentProblem {
    
    private int years;
    private int actualYear;
    private int lowerBound;
    private int upperBound;
    private Table table;
    private int newCost;

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getActualYear() {
        return actualYear;
    }

    public void setActualYear(int actualYear) {
        this.actualYear = actualYear;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
    
    public void setTable(int[][] table) {
        this.table = new Table(table);
    }

    public int getNewCost() {
        return newCost;
    }

    public void setNewCost(int newCost) {
        this.newCost = newCost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Years: ").append(years).append("\n");
        sb.append("Current: ").append(actualYear).append("\n");
        sb.append("Lower Bound: ").append(lowerBound).append("\n");
        sb.append("Upper Bound: ").append(upperBound).append("\n");
        sb.append("I: ").append(newCost).append("\n");
        sb.append("Table: \n");
        sb.append(table);
        
        return sb.toString();
    }
    
}
