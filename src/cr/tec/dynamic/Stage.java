/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.dynamic;

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
    private Map<Node, Double> aggregateValues;
    
    public Stage()
    {
        stageNodes = new ArrayList<>();
        endNodes = new ArrayList<>();
        optimumNodes = new ArrayList<>();
        aggregateValues = new HashMap<>();
    }
    
    public Stage(List<Node> StageNodes, List<Node> EndNodes)
    {
        stageNodes = StageNodes;
        endNodes = EndNodes;
        optimumNodes = new ArrayList<>();
        aggregateValues = new HashMap<>();
    }
    
    public void addStageNode(Node node)
    {
        this.stageNodes.add(node);
    }
    
    public void addEndNode(Node node)
    {
        this.endNodes.add(node);
    }
    
    public void addOptimumNode(Map<Node, Node> optimum)
    {
        this.optimumNodes.add(optimum);
    }
    
    public void addAggregateValue(Node node, double value)
    {
        this.aggregateValues.put(node, value);
    }
    
    public double getAggregateValue(Node node)
    {
        if(aggregateValues.containsKey(node))
        {
            return aggregateValues.get(node);
        }
        else
        {
            return -1;
        }
    }

    public Map<Node, Double> getAggregateValues() {
        return aggregateValues;
    }

    public void setStageNodes(List<Node> stageNodes) {
        this.stageNodes = stageNodes;
    }

    public void setEndNodes(List<Node> endNodes) {
        this.endNodes = endNodes;
    }

    public void setOptimumNodes(List<Map<Node, Node>> optimumNodes) {
        this.optimumNodes = optimumNodes;
    }

    public List<Node> getStageNodes() {
        return stageNodes;
    }

    public List<Node> getEndNodes() {
        return endNodes;
    }

    public List<Map<Node, Node>> getOptimumNodes() {
        return optimumNodes;
    }
    
}
