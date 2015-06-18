package cr.tec.lpsolver.transport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.util.Arrays.stream;

/**
 *
 * 
 * @author Leo
 */
public class NorthWestCornerAlgorithm {
    
    private final int rows; // The rows quantity.
    private final int columns; // The columns quantity.
    private final double[][] costsTable; // The cost matrix.
    private final double[][] shippingTable; /* The table that stores the quantity to send to each consumer. */
    private final double[] supply; /* Production quantity */
    private final double[] demand; /* Consumer's demand. */
    private final boolean[] rowDone; /* Array that indicates which row is eliminated */
    private final boolean[] colDone; /* Array that indicates which column is eliminated */
    private File reportFile;
    
    
    /**
     * Constructor.
     * 
     * @param CostsTable The problem's cost table.
     * @param Supply The supply information.
     * @param Demand The demand information.
     */
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
        reportFile = new File("northWestCorner.txt");
    }
    
    /**
     * Executes the algorithm. 
     * 
     * @return The resultant shipment matrix.
     * @throws Exception If a cell is null.
     */
    public double[][] execute() throws Exception
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
            printTable();
        }
        printShippingTable();
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
    
    
    public void printTable() throws FileNotFoundException, IOException
    {
        FileWriter out = new FileWriter(reportFile,true);
        out.write("\n");
        out.write("----- Costs Table -----\n");
        for(int i = 0; i < rows; i++)
        {
            String row = "[";
            if(rowDone[i])
            {
                for(int j = 0; j < columns; j++)
                {
                    row += " X ";
                }
                row += "]";
                out.write(row + "\n");
            }
            else
            {
                for(int j = 0; j <columns; j++)
                {
                    if(colDone[j])
                    {
                        row += " X ";
                    }
                    else
                    {
                        row += " "+costsTable[i][j] + " ";
                    }
                }
                row += "]";
                out.write(row + "\n");

            }
        }
        out.write("\n");
        out.close();
    }
    
    private void printShippingTable() throws FileNotFoundException, IOException
    {
        FileWriter out = new FileWriter(reportFile,true);
        out.write("\n");
        out.write("----- Shipping Table -----\n");
        for(int i = 0; i < rows; i++)
        {
            String row = "[";
            for(int j = 0; j < columns; j++)
            {
                row += " " + shippingTable[i][j] + " ";
            }
            row += "]";
            out.write(row + "\n");
        }
        out.close();
    }
    
            
    
}
