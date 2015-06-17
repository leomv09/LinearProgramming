package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Leo
 */
public class SimplexSolver implements Solver {

    private Object[][] simplexTable;
    private List<String> slackVariables; /*List containig the slack variables*/
    private int rows;
    private int cols;
    
    public SimplexSolver()
    {
        slackVariables = new ArrayList();
    }
    
    /**
     * Creates the slack variables needed in order to solve the problem.
     * 
     * @param constraints The problem constraints.
     */
    private void createSlackVariables(List<Constraint> constraints)
    {
        int slacksCount = 0;
        
        for(Constraint cons : constraints)
        {
            slackVariables.add("S"+slacksCount);
            slacksCount++;
        }
    }
    
    
    /**
     * Initializes the simplex table, needed to solve the problem.
     * 
     * @param problem Problem object.
     * @throws Exception 
     */
    private void loadTable(Problem problem) throws Exception
    {
        Linear newOF = problem.getObjetiveFunction().numberTimesLinear(-1);//Equals the OF to zero.
        int i = 0;
        int j = 0;
        String currentSlack;
        List<Constraint> constraints = problem.getConstraints();
        for (Constraint cons : constraints)
        {
            if(cons.getRelationship() == Relationship.LEQ)
            {
                currentSlack = slackVariables.get(i);
                simplexTable[i][j] = currentSlack;
                j++;
                for(Term term : cons.getLinear().getTerms())
                {
                    simplexTable[i][j] = term.getCoefficient();
                    j++;
                }
                //Create the identity matrix.
                while( j <= simplexTable[i].length-2)
                {
                    if(j - (slackVariables.size()+1) == slackVariables.indexOf(currentSlack))
                    {
                        simplexTable[i][j] = 1.0;
                        j++;
                    }
                    else
                    {
                        simplexTable[i][j] = 0.0;
                        j++;
                    }
                }
                simplexTable[i][j] = cons.getConstant();
                j = 0;
                i++;
            }
            else
            {
                throw new Exception("Incomplatible relationship for constraint. Expected: <=. Got: "+cons.getRelationship());
            }
        }
        //Creates the z row.
        simplexTable[i][j] = "z";
        j++;
        for(Term term : newOF.getTerms())
        {
            simplexTable[i][j] = term.getCoefficient();
            j++;
        }
        
        for(int k = 0; k <= slackVariables.size(); k++)
        {
            simplexTable[i][j] = 0.0;
            j++;
        }
        System.out.println("Finish loading matrix: "+Arrays.deepToString(simplexTable));
    }
    
    /**
     * Determines if there is a possible solution in the simplex table.
     * 
     * @param row the 'z' row from the simplex table.
     * @return true if there is a solution, false otherwise.
     */
    private boolean isSolution(Object[] row)
    {
        for(int i = 1; i < row.length; i++)
        {
            if((double)row[i] < 0)
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Obtains the pivot column index, in order to know what is the entry variable. 
     * 
     * @param row The 'z' row from the simplex table.
     * @return The index of the smallest number.
     */
    private int getPivotColumnIndex(Object[] row)
    {
        double smallerNumber = 0.0;
        int index = -1;
        
        for(int i = 1; i < row.length; i++)
        {
            if((double)row[i] < smallerNumber)
            {
                smallerNumber = (double)row[i];
                index = i;
            }
        }
        
        return index;
    }
    
    /**
     * Indicates if the degenerate case is in the current iteration.
     * 
     * @return true if it is a degenerate, false otherwise.
     */
    private boolean isDegenerateCase()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Obtains the pivot row index, in order to know what is the out variable.
     * 
     * @param columnIndex The pivot column index.
     * @param lastColumn The last column index in the simplex table.
     * @return The index of the pivot row.
     */
    private int getPivotRowIndex(int columnIndex, int lastColumn)
    {
        int index = -1;
        double currentSolution = Double.MAX_VALUE;
        for(int i = 1; i < slackVariables.size(); i++)
        {
            double res = (double)simplexTable[i][lastColumn] / (double)simplexTable[i][columnIndex];
            if(res < currentSolution && res >= 0)
            {
                currentSolution = res;
                index = i;
            }
        }
        
        return index;
    }
    
    /**
     * Changes the value of each element of the row. Divides each value by the pivot element.
     * 
     * @param rowIndex The privot row index.
     * @param pivotValue The pivot value for the current iteration.
     * @param variableName The name of the variable that enters.
     */
    private void changeRow(int rowIndex, double pivotValue, String variableName)
    {
        simplexTable[rowIndex][0] = variableName;
        for(int i = 1; i < simplexTable[rowIndex].length; i++)
        {
            double currentValue = (double)simplexTable[rowIndex][i];
            simplexTable[rowIndex][i] = currentValue / pivotValue;
        }
    }
    
    /**
     * Changes the value of each element of the row.
     * 
     * @param rowIndex The privot row index.
     * @param pivotColumn The pivot value for the current iteration
     * @param newRow The row value for the entry variable.
     */
    private void changeRow(int rowIndex, int pivotColumn, Object[] newRow)
    {
        double pivotValue = (double)simplexTable[rowIndex][pivotColumn];
        for(int i = 1; i < simplexTable[rowIndex].length; i++)
        {
            double currentValue = (double)simplexTable[rowIndex][i];
            simplexTable[rowIndex][i] = currentValue - (pivotValue * (double)newRow[i]);
        }
    }
    
    /**
     * Updates the simplex table.
     * 
     * @param pivotColumn The pivot value for the current iteration
     * @param newRowIndex The privot row index.
     */
    private void updateSimplexTable(int pivotColumn, int newRowIndex)
    {

        for(int i = 0; i <= slackVariables.size(); i++)
        {
            if(i != newRowIndex)
            {
               changeRow(i, pivotColumn, simplexTable[newRowIndex]); 
            }
            
        }
    }
    
    
    /**
     * Obtains the solution value (last column number in the row) of a given variable.
     * 
     * @param variable The name of the variable to retrieve the solution value.
     * @return The solution value.
     */
    private double getSolutionValueOf(String variable)
    {
        for(int i = 0; i < slackVariables.size(); i++)
        {
            if(((String) simplexTable[i][0]).equals(variable))
            {
                int columnIndex = simplexTable[i].length -1;
                return (double)simplexTable[i][columnIndex];
            }
        }
        return -1;
    }
    
    
    /**
     * Indicates if a variable is in the base column.
     * 
     * @param variable The variable to check.
     * @return true if the variable is in base column, false otherwise.
     */
    private boolean isInBaseColumn(String variable)
    {
        for(int i = 0; i < slackVariables.size(); i++)
        {
            if(((String) simplexTable[i][0]).equals(variable))
            {
                return true;
            }
        }
        return false;
    }
    
     /**
      * Obtains the optimum values of the current simplex table.
      * 
      * @param problem Problem object.
      * @return A map (Variable, solution) of solution points.
      */
    private void getOptimumPoints(Problem problem, Result res)
    {
        for(String variable : problem.getVariables())
        {
            double result = getSolutionValueOf(variable);
            if(result == -1)
            {
                result = 0;
            }
            res.addVariable(variable, result);
        }
    }
    
    private void printTable()
    {
        
    }
    
    /**
     * Method that solves the problem with the simplex method.
     * 
     * @param problem The problem to solve.
     * @return A Result object containing the result onformation of the problem.
     * @throws Exception 
     */
    @Override
    public Result solve(Problem problem) throws Exception {
        if(problem.getProblemType() == ProblemType.MIN)
        {
            problem.setObjetiveFunction(problem.getObjetiveFunction().numberTimesLinear(-1));
        }
        createSlackVariables(problem.getConstraints());
        cols = (problem.getVariables().size() + slackVariables.size()) + 2;
        rows = slackVariables.size()+1;
        simplexTable = new Object[rows][cols];

        loadTable(problem);
        boolean solution = isSolution(simplexTable[slackVariables.size()]);
        String entryVariable;
        
        while(!solution)
        {
            int pivotColumnIndex = getPivotColumnIndex(simplexTable[slackVariables.size()]);
            if(pivotColumnIndex >= 0)
            {
                int pivotRowIndex = getPivotRowIndex(pivotColumnIndex, cols - 1);
                if(pivotRowIndex >= 0)
                {
                    entryVariable = problem.getVariables().get(pivotColumnIndex - 1);
                    double pivotValue = (double)simplexTable[pivotRowIndex][pivotColumnIndex];
                    changeRow(pivotRowIndex, pivotValue, entryVariable);
                    updateSimplexTable(pivotColumnIndex, pivotRowIndex);
                    solution = isSolution(simplexTable[slackVariables.size()]);
                }
                else
                {
                    break;//Error.
                }
            }
            else
            {
                break;//Possible solution.
            }
            
        }
        double optimumValue = (double)simplexTable[slackVariables.size()][cols-1];
        Result res = new Result(null, optimumValue);
        //Check if the desition variables are in the table.
        for(String variable : problem.getVariables())
        {
            if(!isInBaseColumn(variable))//It means it's a multiple solution.
            {
                getOptimumPoints(problem, res);//Adds the current solution before the matrix changes.
                int pivotColumnIndex = problem.getVariables().indexOf(variable)+1;
                int pivotRowIndex = getPivotRowIndex(pivotColumnIndex, cols - 1);
                double pivotValue = (double)simplexTable[pivotRowIndex][pivotColumnIndex];
                changeRow(pivotRowIndex, pivotValue, variable);
                updateSimplexTable(pivotColumnIndex, pivotRowIndex);
            }
        }
        getOptimumPoints(problem, res);
        

        return res;
    }
    
}