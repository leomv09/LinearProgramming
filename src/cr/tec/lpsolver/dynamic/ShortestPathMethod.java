/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic;

import java.util.List;

/**
 *
 * @author Leo
 */
public class ShortestPathMethod 
{
    public static void solveGraph(List<Node> Nodes, List<Edge> Edges)
    {
        Graph graph = new Graph(Nodes.get(0), Nodes.get(Nodes.size()-1), Nodes, Edges);
        
        double optimumValue = graph.solveGraph();
    }
}
