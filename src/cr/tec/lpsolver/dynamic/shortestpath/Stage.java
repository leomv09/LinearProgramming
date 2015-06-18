/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic.shortestpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Leo
 */
public class Stage {
    private List<Node> stageNodes;
    private List<Node> endNodes;
    private List<Map<Node, Node>> optimumNodes;
    private List<Map<Node, Double>> aggregateValues;
    
    /**
     * 
     */
    public Stage()
    {
        stageNodes = new ArrayList<>();
        endNodes = new ArrayList<>();
        optimumNodes = new ArrayList<>();
        aggregateValues = new ArrayList<>();
    }
    
    /**
     * Creates a new instance of stage,
     * @param StageNodes The nodes of the stage.
     * @param EndNodes The nodes of the previous stage,
     */
    public Stage(List<Node> StageNodes, List<Node> EndNodes)
    {
        stageNodes = StageNodes;
        endNodes = EndNodes;
        optimumNodes = new ArrayList<>();
        aggregateValues = new ArrayList<>();
    }
    
    /**
     * Adds a new stage node.
     * @param node The node to add.
     */
    public void addStageNode(Node node)
    {
        this.stageNodes.add(node);
    }
    
    /**
     * Adds a new node from a previous stage.
     * @param node 
     */
    public void addEndNode(Node node)
    {
        this.endNodes.add(node);
    }
    
    /**
     * Adds a new optimum node.
     * @param startNode The stage node.
     * @param endNode The previous node to connect with.
     */
    public void addOptimumNode(Node startNode, Node endNode)
    {
        Map<Node, Node> map = new HashMap<>();
        map.put(startNode, endNode);
        this.optimumNodes.add(map);
    }
    
    /**
     * Obtains the optimum nodes that connect with a node.
     * @param node The node to check.
     * @return 
     */
    public List<Node> getOptimumNode(Node node)
    {
        List<Node> nodes = new ArrayList<>();
        for(Map<Node, Node> map : optimumNodes)
        {
            if(map.containsKey(node))
            {
                nodes.add(map.get(node));
            }
        }
        return nodes;
    }
    
    /**
     * Adds a new aggregate value for a node.
     * @param map The new map value to add.
     */
    public void addAggregateValue(Map<Node, Double> map)
    {
        this.aggregateValues.add(map);
    }
    
    /**
     * Obtains the aggregate value of a node.
     * @param node
     * @return 
     */
    public double getAggregateValue(Node node)
    {
        double value = Double.POSITIVE_INFINITY;
        for(Map<Node, Double> map : aggregateValues)
        {
            if(map.containsKey(node))
            {
                double currentValue = map.get(node);
                if(currentValue >= 0 && currentValue < value)
                {
                    value = currentValue;
                }    
            }
        }
        
        return value;
    }

    /**
     * Obtains the list of all aggregate values.
     * @return The list of aggregate values.
     */
    public List<Map<Node, Double>> getAggregateValues() {
        return aggregateValues;
    }
    
    /**
     * Sets the stage nodes.
     * @param stageNodes 
     */
    public void setStageNodes(List<Node> stageNodes) {
        this.stageNodes = stageNodes;
    }

    /**
     * Sets the previous stage nodes.
     * @param endNodes 
     */
    public void setEndNodes(List<Node> endNodes) {
        this.endNodes = endNodes;
    }

    /**
     * Obtains the nodes of the stage.
     * @return A list with the nodes of the stage.
     */
    public List<Node> getStageNodes() {
        return stageNodes;
    }

    /**
     * Obtains the previous stage nodes.
     * @return A list with nodes.
     */
    public List<Node> getEndNodes() {
        return endNodes;
    }

    /**
     * Obtains the list of optimum nodes of the stage.
     * @return A list of maps.
     */
    public List<Map<Node, Node>> getOptimumNodes() {
        return optimumNodes;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        sb.append("Stage Nodes").append("\n");
        sb.append("{").append("\n");
        String a = "";
        for(Node node : stageNodes)
        {
            if(isFirst)
            {
                isFirst = false;
            }
            sb.append(a).append(node.getName()).append(" Aggregate Value: ").append(getAggregateValue(node));
            a = " | ";
        }
        isFirst = true;
        sb.append("\n");
        sb.append("}").append("\n");
        sb.append("Previous stage nodes: ");
        sb.append("{").append("\n");
        for(Node node : endNodes)
        {
            if(isFirst)
            {
                a = "";
                isFirst = false;
            }
            sb.append(a).append(node.getName());
            a = " | ";
        }
        isFirst = true;
        sb.append("\n");
        sb.append("}").append("\n");
        sb.append("Optimum nodes: ");
        sb.append("{").append("\n");
        for(Node node : stageNodes)
        {
            if(isFirst)
            {
                a = "";
                isFirst = false;
            }
            sb.append(a).append(getOptimumNode(node).get(0).getName());
            a = " | ";
        }
        sb.append("\n");
        sb.append("}").append("\n");
        return sb.toString();
    }
    
}
