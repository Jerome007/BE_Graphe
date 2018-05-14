package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.Label;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;

import org.insa.algo.shortestpath.*;

public class DikstraTest {

	// Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a1to3 is the arc from node 1 to 2.
    @SuppressWarnings("unused")
    private static Arc a1to2, a2to4,a2to5,a2to6,a5to4,a5to6,a3to6,a5to3,a6to5,a1to3,a3to1,a3to2;

    // Some paths...
    private static Path emptyPath, singleNodePath, shortPath, longPath, loopPath, longLoopPath,
            invalidPath;

    private static ArcInspector inspector;
    
    private static BellmanFordAlgorithm bellman;
    
    private static DijkstraAlgorithm dijkstra;
    
    @BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        
        // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a1to2 = Node.linkNodes(nodes[0], nodes[1], 7, speed10, null);
        a2to4 = Node.linkNodes(nodes[1], nodes[3], 4, speed10, null);
        a2to5 = Node.linkNodes(nodes[1], nodes[4], 1, speed10, null);
        a2to6 = Node.linkNodes(nodes[1], nodes[5], 5, speed10, null);
        a5to4 = Node.linkNodes(nodes[4], nodes[3], 2, speed10, null);
        a5to6 = Node.linkNodes(nodes[4], nodes[5], 3, speed10, null);
        a3to6 = Node.linkNodes(nodes[2], nodes[5], 2, speed10, null);
        a5to3 = Node.linkNodes(nodes[4], nodes[2], 2, speed10, null);
        a6to5 = Node.linkNodes(nodes[5], nodes[4], 3, speed10, null);
        a1to3 = Node.linkNodes(nodes[0], nodes[2], 8, speed10, null);
        a3to1 = Node.linkNodes(nodes[2], nodes[0], 7, speed10, null);
        a3to2 = Node.linkNodes(nodes[2], nodes[1], 2, speed10, null);
        
        graph = new Graph("ID", "", Arrays.asList(nodes), null);

        emptyPath = new Path(graph, new ArrayList<Arc>());
        singleNodePath = new Path(graph, nodes[1]);
        shortPath = new Path(graph, Arrays.asList(new Arc[] { a1to2,a2to4,a2to5,a2to6,a5to4,a5to6,a3to6,a5to3,a6to5,a1to3,a3to1,a3to2 }));
        
        
    	}
    
    @Test
    public void testShortestPathSolution() {
        //Creation de la data, utilisation de l'oracle et lancement de dijsktra
    	inspector = ArcInspectorFactory.getAllFilters().get(0);
    	
    	ShortestPathData data0 = new ShortestPathData(graph,graph.get(0),graph.get(4),inspector);
    	bellman = new BellmanFordAlgorithm(data0);
    	dijkstra = new DijkstraAlgorithm(data0);
    	ShortestPathSolution sol0 = bellman.doRun();
    	ShortestPathSolution test0 = dijkstra.doRun();
    	if (!sol0.isFeasible() || !test0.isFeasible())
    	{
    		assertEquals(sol0.isFeasible(),test0.isFeasible());
    	}
    	else
    	{
    		assertEquals(sol0.getPath().getLength(), test0.getPath().getLength(),0.1);
    	}
    	
    	ShortestPathData data1 = new ShortestPathData(graph,graph.get(2),graph.get(5),inspector);
    	bellman = new BellmanFordAlgorithm(data1);
    	dijkstra = new DijkstraAlgorithm(data1);
    	ShortestPathSolution sol1 = bellman.doRun();
    	ShortestPathSolution test1 = dijkstra.doRun();
    	if (!sol1.isFeasible() || !test1.isFeasible())
    	{
    		assertEquals(sol1.isFeasible(),test1.isFeasible());
    	}
    	else
    	{
    		assertEquals(sol1.getPath().getLength(), test1.getPath().getLength(),0.1);
    	}
    	
    	ShortestPathData data2 = new ShortestPathData(graph,graph.get(3),graph.get(2),inspector);
    	bellman = new BellmanFordAlgorithm(data2);
    	dijkstra = new DijkstraAlgorithm(data2);
    	ShortestPathSolution sol2 = bellman.doRun();
    	ShortestPathSolution test2 = dijkstra.doRun();
    	if (!sol2.isFeasible() || !test2.isFeasible())
    	{
    		assertEquals(sol2.isFeasible(),test2.isFeasible());
    	}
    	else
    	{
    		assertEquals(sol2.getPath().getLength(), test2.getPath().getLength(),0.1);
    	}
    	
    	ShortestPathData data3 = new ShortestPathData(graph,graph.get(1),graph.get(0),inspector);
    	bellman = new BellmanFordAlgorithm(data3);
    	dijkstra = new DijkstraAlgorithm(data3);
    	ShortestPathSolution sol3 = bellman.doRun();
    	ShortestPathSolution test3 = dijkstra.doRun();
    	if (!sol3.isFeasible() || !test3.isFeasible())
    	{
    		assertEquals(sol3.isFeasible(),test3.isFeasible());
    	}
    	else
    	{
    		assertEquals(sol3.getPath().getLength(), test3.getPath().getLength(),0.1);
    	}
    	
    	ShortestPathData data4 = new ShortestPathData(graph,graph.get(4),graph.get(3),inspector);
    	bellman = new BellmanFordAlgorithm(data4);
    	dijkstra = new DijkstraAlgorithm(data4);
    	ShortestPathSolution sol4 = bellman.doRun();
    	ShortestPathSolution test4 = dijkstra.doRun();
    	if (!sol4.isFeasible() || !test4.isFeasible())
    	{
    		assertEquals(sol4.isFeasible(),test4.isFeasible());
    	}
    	else
    	{
    		assertEquals(sol4.getPath().getLength(), test4.getPath().getLength(),0.1);
    	}

    }
            

}
