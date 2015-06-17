/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic;

/**
 *
 * @author Leo
 */
public class Edge {
    private final Node startNode;
    private final Node endNode;
    private final double wight;

    public Edge(Node start, Node end, double Weight)
    {
        this.startNode = start;
        this.endNode = end;
        this.wight = Weight;
    }
    
    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public double getWight() {
        return wight;
    }   
}
