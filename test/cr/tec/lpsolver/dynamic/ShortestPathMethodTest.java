/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Leo
 */
public class ShortestPathMethodTest {
    private Graph graph;
    private Node node1, node2, node3, node4, node5, node6, node7;
    
    @Before
    public void setUp() 
    {
        List<Edge> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        node1 = new Node( "1");nodes.add(node1);
        node2 = new Node("2");nodes.add(node2);
        node3 = new Node("3");nodes.add(node3);
        node4 = new Node("4");nodes.add(node4);
        node5 = new Node("5");nodes.add(node5);
        node6 = new Node("6");nodes.add(node6);
        node7 = new Node("7");nodes.add(node7);
        edges.add(new Edge(node1, node2, 7));
        edges.add(new Edge(node1, node3, 8));
        edges.add(new Edge(node1, node4, 5));
        edges.add(new Edge(node2, node5, 12));
        edges.add(new Edge(node3, node5, 8));
        edges.add(new Edge(node3, node6, 9));
        edges.add(new Edge(node4, node5, 7));
        edges.add(new Edge(node4, node6, 13));
        edges.add(new Edge(node5, node7, 9));
        edges.add(new Edge(node6, node7, 6));
        
        this.graph = new Graph(node1, node7, nodes, edges);
    }
    
    @Test
    public void connections()
    {
        List<Node> nodes = new ArrayList<>();
        nodes.add(node7);
        assertThat("Nodes 7 has 2 connections: ", graph.getConnectedNodes(nodes).size(), is(2));
        nodes.clear();
        nodes.add(node6);
        assertThat("Nodes 6 has 2 connections: ", graph.getConnectedNodes(nodes).size(), is(2));
        nodes.clear();
        nodes.add(node5);
        assertThat("Nodes 5 has 3 connections: ", graph.getConnectedNodes(nodes).size(), is(3));
        nodes.clear();
        nodes.add(node4);
        assertThat("Nodes 4 has 1 connection: ", graph.getConnectedNodes(nodes).size(), is(1));
        nodes.clear();
        nodes.add(node3);
        assertThat("Nodes 3 has 1 connection: ", graph.getConnectedNodes(nodes).size(), is(1));
        nodes.clear();
        nodes.add(node2);
        assertThat("Nodes 2 has 1 connection: ", graph.getConnectedNodes(nodes).size(), is(1));
        nodes.clear();
        nodes.add(node6);
        nodes.add(node5);
        assertThat("Nodes 6 and 5 have 3 connections: ", graph.getConnectedNodes(nodes).size(), is(3));
    }
    
    @Test
    public void testContent()
    {
        assertThat("Nodes: ", graph.getNodes().size(), is(7));
        assertThat("Edges: ", graph.getEdges().size(), is(10));
        assertThat("Stages: ", graph.getStages().size(), is(3));
    }
    
    @Test
    public void solve()
    {
        ShortestPathSolver solver = new ShortestPathSolver();
        try {
            ShortestPathResult res = (ShortestPathResult) solver.solve(graph);
            assertThat("Optimum cost is: ", res.getValue(), is(21.0));
            assertThat("Optimum paths is: ", res.getRoutes().size(), is(1));
        } catch (Exception ex) {
            Logger.getLogger(ShortestPathMethodTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
