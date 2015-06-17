/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.dynamic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leo
 */
public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;
    private List<Stage> stages;
    private static Graph instance = null;
    Node start;
    Node end;
    
    public Graph(Node Start, Node End)
    {
        this.start = Start;
        this.end = End;
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        stages = new ArrayList<>();
    }
    
    public Graph(Node Start, Node End, List<Edge> Edges)
    {
        this.start = Start;
        this.end = End;
        nodes = new ArrayList<>();
        this.edges = Edges;
        stages = new ArrayList<>();
    }
    
    public void addEdge(Edge edge)
    {
        this.edges.add(edge);
    }
    
    public void setStartNode(Node Start)
    {
        this.start = Start;
    }
    
    public void setEndNode(Node End)
    {
        this.end = End;
    }
    
    
    private ArrayList<Stage> getStages()
    {
        List<Node> startNodes = new ArrayList<>();
        startNodes.add(end);
        List<Stage> res = new ArrayList<>();
        for(int i = 0; i < nodes.size(); i++)
        {
            Stage currentStage = new Stage();
            currentStage.setStageNodes(getConnectedNodes(startNodes));
            currentStage.setEndNodes(startNodes);
            startNodes = currentStage.getStageNodes();
            res.add(currentStage);
            if(i == 1)
            {
                for(Node node : startNodes)
                {
                    currentStage.addAggregateValue(node, 0);
                }
            }
        }
        return (ArrayList<Stage>) res;//First stage is the end stage in the graph.
    }
    
    /**
     * 
     * @param node
     * @return 
     */
    private List<Node> getConnectedNodes(Node node)
    {
        List<Node> res = new ArrayList<>();
        for( Edge e : edges)
        {
            if(e.getEndNode().getName().equals(node.getName()))
            {
                res.add(e.getStartNode());
            }
        }
        return res;
    }
    
    private List<Node> getConnectedNodes(List<Node> nodes)
    {
        List<Node> res = new ArrayList<>();
        for(Node node : nodes)
        {
            for( Edge e : edges)
            {
                if(e.getEndNode().getName().equals(node.getName()))
                {
                    if(!res.contains(e.getStartNode()))
                    {
                        res.add(e.getStartNode());
                    }   
                }
            }
        }
        return res;
    }
    
    
    private void solveGraph()
    {
        stages = getStages();
        int i = 0;
        for(Stage stage : stages)
        {
            
        }
    }
}
