/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic.shortestpath;

import cr.tec.lpsolver.dynamic.DynamicSolver;


/**
 *
 * @author Leo
 */
public class ShortestPathSolver implements DynamicSolver
{
    @Override
    public Object solve(Object obj) throws Exception {
        
        Graph graph = (Graph) obj;
        double optimumValue = graph.solveGraph();
        
        ShortestPathResult res = new ShortestPathResult(optimumValue, graph.getRoutes());

        return res;
    }
}
