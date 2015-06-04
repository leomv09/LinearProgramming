package cr.tec.lpsolver.transport;

import static java.util.Arrays.stream;

public class NorthWestCornerAlgorithm {
    
    private final int rows;
    private final int columns;
    private final double[][] costsTable;
    private final double[][] shippingTable; /* The table that stores the quantity to send to each consumer. */
    private final double[] supply; /* Production quantity */
    private final double[] demand; /* Consumer's demand. */
    private final boolean[] rowDone; /* Array that indicates which row is eliminated */
    private final boolean[] colDone; /* Array that indicates which column is eliminated */
    
    
    
    public NorthWestCornerAlgorithm(double[][] CostsTable, double[] Supply, double[] Demand)
    {
        this.costsTable = CostsTable;
        this.supply = Supply;
        this.demand = Demand;
        this.columns = demand.length;
        this.rows = supply.length;
        this.shippingTable = new double[rows][columns];
        this.rowDone = new boolean[rows];
        this.colDone = new boolean[columns]; 
    }
    
    /**
     * Executes the algorithm. 
     * 
     * @return The resultant shipment matrix.
     * @throws Exception If a cell is null.
     */
    double[][] execute() throws Exception
    {
        double supplyLeft = stream(supply).sum();
        
        while(supplyLeft > 0)
        {
            int[] cell = getNextConrner();
            if(cell == null)
            {
                throw new Exception("Insufficient supply for the given demand.");
            }
            int r = cell[0];
            int c = cell[1];
            
            double minQuantity = Math.min(demand[c], supply[r]);
            
            demand[c] -= minQuantity;
            if (demand[c] == 0)
            {
                colDone[c] = true;
            }
            supply[r] -= minQuantity;
            if (supply[r] == 0)
            {
                rowDone[r] = true;
            }
            shippingTable[r][c] = minQuantity;
            supplyLeft -= minQuantity;
        }
        
        return this.shippingTable;
    }
    
    /**
     * Obtains the next upper left corner element indexes of the costs table.
     * 
     * @return An array containig the cell indexes.
     */
    private int[] getNextConrner()
    {
        int[] res = new int[2];
        boolean hasRow = false;
        
        for(int i = 0; i < rows; i++)
        {
            if(!rowDone[i])
            {
                hasRow = true;
                res[0] = i;
                break;
            }
        }
        
        if(!hasRow)
        {
            return null;
        }
        
        for(int j = 0; j < rows; j++)
        {
            if(!colDone[j])
            {
                res[1] = j;
                break;
            }
        }
        return res;
    }
    
    /**
     * Obtains the total cost of the shipment.
     * 
     * @return The sum of each producers cost of transportation.
     */
    public double getTotalCost()
    {
        int res = 0;
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                if(shippingTable[i][j] > 0)
                {
                    res += costsTable[i][j] * shippingTable[i][j];
                }
            }
        }
        
        return res;
    }
    
    
    
    
}
