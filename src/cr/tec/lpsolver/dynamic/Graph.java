/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Leo
 */
public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;
    private List<Stage> stages;
    Node start;
    Node end;
    private String route;
    
    public Graph(Node Start, Node End)
    {
        this.start = Start;
        this.end = End;
        this.route = "";
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        stages = new ArrayList<>();
    }
    
    public Graph(Node Start, Node End, List<Node> Nodes, List<Edge> Edges)
    {
        this.start = Start;
        this.end = End;
        nodes = Nodes;
        this.route = "";
        this.edges = Edges;
        stages = new ArrayList<>();
    }
    
    /**
     * Adds a new edge to the graph.
     * 
     * @param edge The edge to add.
     */
    public void addEdge(Edge edge)
    {
        this.edges.add(edge);
    }
    
    /**
     * Sets the start node of the graph.
     * @param Start The start node.
     */
    public void setStartNode(Node Start)
    {
        this.start = Start;
    }
    
    /**
     * Sets the end of the graph.
     * @param End The final node.
     */
    public void setEndNode(Node End)
    {
        this.end = End;
    }
    
    /**
     * Obtains the stages of the current graph.
     * @return A list of stages.
     */
    private ArrayList<Stage> getTotalStages()
    {
        List<Node> startNodes = new ArrayList<>();
        startNodes.add(end);
        List<Stage> res = new ArrayList<>();
        int i = 0;
        while(startNodes.size() > 0)
        {
            List<Node> connectedNodes = getConnectedNodes(startNodes);
            if(connectedNodes.isEmpty())
            {
                break;
            }
            Stage currentStage = new Stage();
            currentStage.setStageNodes(connectedNodes);
            currentStage.setEndNodes(startNodes);
            startNodes = currentStage.getStageNodes();
            
            res.add(currentStage);
            if(i == 0)
            {
                for(Node node : startNodes)
                {
                    Map<Node, Double> map = new HashMap<>();
                    map.put(node, -1.0);
                    currentStage.addAggregateValue(map);
                }
            }
            i++;
        }
        return (ArrayList<Stage>) res;//First stage is the end stage in the graph.
    }
    
    /**
     * Obtains the connected nodes with a given nodes.
     * @param nodes The nodes to check.
     * @return A list of connected nodes.
     */
    public List<Node> getConnectedNodes(List<Node> nodes)
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
    
    /**
     * Checks if there is an edge between two nodes.
     * 
     * @param a The first node.
     * @param b The second node.
     * @return true if there is a connection, false otherwise.
     */
    private boolean connectionExists(Node a, Node b)
    {
        for(Edge e : edges)
        {
            if(e.getStartNode().getName().equals(a.getName()))
            {
                if(e.getEndNode().getName().equals(b.getName()))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Obtains the edge value between two nodes.
     * 
     * @param a The first node.
     * @param b The second node.
     * @return The connection weight.
     */
    private double getConnectionValue(Node a, Node b)
    {
        for(Edge e : edges)
        {
            if(e.getStartNode().getName().equals(a.getName()))
            {
                if(e.getEndNode().getName().equals(b.getName()))
                {
                    return e.getWight();
                }
            }
        }
        return -1;
    }
    
    /**
     * Obtains the best node option to connect with.
     * @param node The node to check
     * @param endNodes A list of nodes that the node is connected with.
     * @return A list of optimum nodes.
     */
    private List<Node> calculateBestPath(Node node, List<Node> endNodes)
    {
        double currentValue = Double.POSITIVE_INFINITY;
        List<Node> res = new ArrayList<>();
        for(Node endNode : endNodes)
        {
            if(connectionExists(node, endNode))
            {
                double value = getConnectionValue(node, endNode);
                if(value < currentValue)
                {
                    currentValue = value;
                }
            }
        }
        
        for(Node endNode : endNodes)
        {
            double value = getConnectionValue(node, endNode);
            if(value == currentValue)
            {
                res.add(endNode);
            }
        }
        
        return res;
    }
    
    /**
     * Obtains the aggregate value of a node of a previous stage.
     * 
     * @param index The stage index.
     * @param node The node to obtain the aggregate value.
     * @return The aggregate value of the node in the previous stage.
     */
    private double getPreviousAggregateValue(int index, Node node)
    {
        if(index >= 0)
        {
            Stage stage = this.stages.get(index);
            return stage.getAggregateValue(node);
        }
        
        return 0;
    }
    
    /**
     * Solves the graph.
     * @return The optimum cost value.
     */
    public double solveGraph()
    {
        stages = getTotalStages();
        int i = 0;
        for(Stage stage : stages)
        {
            for(Node startNode : stage.getStageNodes())
            {
                for(Node endNode : stage.getEndNodes())
                {
                    double aggregateValue = getConnectionValue(startNode, endNode);
                    Map<Node, Double> map = new HashMap<>();
                    if(aggregateValue > 0)
                    {
                        aggregateValue += getPreviousAggregateValue(i - 1, endNode);
                        map.put(startNode, aggregateValue);
                        stage.addAggregateValue(map);
                    }
                    else
                    {
                        map.put(startNode, aggregateValue);
                        stage.addAggregateValue(map);
                    }
                }
                List<Node> bestNodes = calculateBestPath(startNode, stage.getEndNodes());
                for(Node node : bestNodes)
                {
                    stage.addOptimumNode(startNode, node);
                }
            }
            i++;
        }
        i--;
        
        Stage firstStage = stages.get(i);
        double value = firstStage.getAggregateValue(start);

        return value;
    }
    
    
    public List<String> getRoutes(int stageIndex, Node endNode)
    {
        List<String> res = new ArrayList<>();
        Stage firstStage = stages.get(stageIndex);
        List<Node> bestNodes = firstStage.getOptimumNode(start);
        for(Node bestNode : bestNodes)
        {
            String route = start.getName();
            Node currentNode = bestNode;
           for(int j = 1; j < stages.size(); j++)
           {
               route += "->" + currentNode.getName();
               stageIndex--;
               currentNode = stages.get(stageIndex).getOptimumNode(currentNode).get(0);
           }
           route += "->" + endNode.getName();
           res.add(route);
        }     
        return res;
    }

    /**
     * Obtains the nodes of the graph.
     * @return A list of nodes.
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Obtains the edges of the graph.
     * 
     * @return A list of edges.
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Obtains the stages of the graph.
     * 
     * @return A list of stages.
     */
    public List<Stage> getStages() {
        if(stages.isEmpty())
        {
            return getTotalStages();
        }
        return stages;
    }
    
    /**
     * Obtains the optimum routes.
     * @return A list of optimum paths.
     */
    public String getRoute()
    {
        return this.route;
    }
}
