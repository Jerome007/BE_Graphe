package org.insa.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;

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
    public void testConstructor() {
        assertEquals(graph, emptyPath.getGraph());
        assertEquals(graph, singleNodePath.getGraph());
        assertEquals(graph, shortPath.getGraph());
        assertEquals(graph, longPath.getGraph());
        assertEquals(graph, loopPath.getGraph());
        assertEquals(graph, longLoopPath.getGraph());
        assertEquals(graph, invalidPath.getGraph());
            
        }
    @Test
    public void testIsEmpty() {
        assertTrue(emptyPath.isEmpty());

        assertFalse(singleNodePath.isEmpty());
        assertFalse(shortPath.isEmpty());
        assertFalse(longPath.isEmpty());
        assertFalse(loopPath.isEmpty());
        assertFalse(longLoopPath.isEmpty());
        assertFalse(invalidPath.isEmpty());
    }
            

}
