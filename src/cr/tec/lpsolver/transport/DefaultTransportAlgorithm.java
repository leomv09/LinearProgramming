/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.transport;

import cr.tec.lpsolver.Constraint;
import cr.tec.lpsolver.GraphicalSolver;
import cr.tec.lpsolver.Linear;
import cr.tec.lpsolver.Problem;
import cr.tec.lpsolver.ProblemType;
import cr.tec.lpsolver.Relationship;
import cr.tec.lpsolver.Result;
import cr.tec.lpsolver.Term;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Leo
 */
public class DefaultTransportAlgorithm {
    
    private final double[] demand;
    private final double[] supply;
    private final double[][] costsTable;
 
    private final int rows;
    private final int columns;
    
    Constraint[][] shippingTable;
    
    
    public DefaultTransportAlgorithm(double[][] CostsTable, double[] Demand, double[] Supply)
    {
        this.demand = Demand;
        this.supply = Supply;
        this.costsTable = CostsTable;
        this.rows = supply.length;
        this.columns = demand.length;
        this.shippingTable = new Constraint[rows][columns];
    }
    
    /**
     * Solves a transport problem using the graphical method.
     * 
     * @return The resultant shipment matrix.
     * @throws Exception 
     */
    public double[][] execute() throws Exception
    {
        double[][] resultMatrix = new double[rows][columns];
        Problem linearProblem = this.toProblem();
        GraphicalSolver solver = new GraphicalSolver();
        Result res = solver.solve(linearProblem);
        
        Map optimumPoint = res.getResults(0);
        
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                Linear function = shippingTable[i][j].getLinear();
                resultMatrix[i][j] = function.evaluate(optimumPoint);
            }
        }
        return resultMatrix;
    }
    
    
    /**
     * Obtains the objective function based on the constraints. 
     * Multiplies the cost matrix with the shipping matrix and the results are added up together.
     * 
     * @param shippingTable The table of constraints.
     * 
     * @return a Linear object resulting from the operations.
     */
    private Linear getObjectiveFunction(Constraint[][] shippingTable)
    {
        Linear function;
        List<Linear> terms = new ArrayList<>();
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                Linear linear = (shippingTable[i][j]).getLinear().numberTimesLinear(costsTable[i][j]);
                terms.add(linear);
            }
        }
        
        function = terms.get(0);
        terms.remove(0);
        
        for (Linear l : terms)
        {
            function = function.linearAddition(l);
        }
        
        return function;
    }
    
    
    /**
     * Obtains the constraints from the shipping table(constraints matrix).
     * 
     * @param shippingTable a matrix that stores the shipping values.
     * 
     * @return A list of Constraints.
     */
    private List<Constraint> getConstraints(Constraint[][] shippingTable)
    {
        List<Constraint> constraints = new ArrayList<>();
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j< columns; j++)
            {
                if(i == 0)
                {
                    if(j == 0 || j == 1)
                    {
                        constraints.add(shippingTable[i][j]);
                    }
                    else
                    {
                        Linear linear = shippingTable[i][j].getLinear().numberTimesLinear(-1);
                        Constraint cons = new Constraint(linear, Relationship.LEQ, supply[i]);
                        constraints.add(cons);
                    }
                }
                else
                {
                    if(j == 0 || j == 1)
                    {
                        Linear linear = shippingTable[i][j].getLinear().numberTimesLinear(-1);
                        Constraint cons = new Constraint(linear, Relationship.LEQ, demand[j]);
                        constraints.add(cons);
                    }
                    else
                    {
                        Linear linear = shippingTable[i][j].getLinear();
                        double res = demand[j] - supply[i-1];
                        linear = linear.numberTimesLinear(-1);
                        Constraint cons = new Constraint(linear, Relationship.LEQ, res);
                        if(cons.isNegative())
                        {
                            linear = linear.numberTimesLinear(-1);
                            cons = new Constraint(linear, Relationship.GEQ, res*-1);
                            constraints.add(cons);
                        }
                        else
                        {
                            linear = linear.numberTimesLinear(-1);
                            cons = new Constraint(linear, Relationship.LEQ, res);
                            constraints.add(cons);
                        }
                    }
                }
                
            }
        }
        
        return constraints;
    }
    
    /**
     * Converts a transport problem to an object Problem.
     * Converts only transport problems that have a costs matrix of 2xn.
     * 
     * @return a Problem object.
     */
    public Problem toProblem() 
    {
        String[] variables = {"x", "y"};
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                if (i == 0)
                {
                    if (j == 0 || j == 1)
                    {
                        Linear linear = new Linear();
                        linear.add(1, variables[j]);
                        Constraint cons = new Constraint(linear, Relationship.GEQ, 0);
                        shippingTable[i][j] = cons;
                    }
                   else
                    {
                        Linear linear = new Linear();
                        linear.add(-1, "x");
                        linear.add(-1, "y");
                        Constraint cons = new Constraint(linear, Relationship.GEQ, 0);
                        shippingTable[i][j] = cons;
                    }
                }
                else
                {
                    if (j == 0 || j == 1)
                    {
                        Constraint currentConstr = shippingTable[0][j];
                        Linear linear = new Linear();
                        String variable = currentConstr.getLinear().getVariables().get(0);
                        linear.add(-1, variable);
                        Constraint cons = new Constraint(linear, Relationship.GEQ, 0);
                        shippingTable[i][j] = cons;
                    }
                    else
                    {
                        Constraint cons1 = shippingTable[i][j-2];
                        Constraint cons2 = shippingTable[i][j-1];
                        Linear linear = new Linear();
                        for(Term term : cons1.getLinear().getTerms())
                        {
                            linear.add(- term.getCoefficient(), term.getVariable());
                        }
                        for(Term term: cons2.getLinear().getTerms())
                        {
                            linear.add(- term.getCoefficient(), term.getVariable());
                        }
                        Constraint cons = new Constraint(linear, Relationship.GEQ, 0);
                        shippingTable[i][j] = cons;
                    }
                }
            }
        }
        
        Problem problem = new Problem();
        problem.setObjetiveFunction(getObjectiveFunction(shippingTable));
        problem.addConstraints(getConstraints(shippingTable));
        problem.setProblemType(ProblemType.MIN);
        
        return problem;
    }
    
}
