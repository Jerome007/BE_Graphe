package org.insa.algo.shortestpath;

import java.util.HashMap;
import java.util.Map;

import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import java.util.HashMap;
import java.util.Map;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	
	public Map<Node, Label> hm = new HashMap<>();
	public int nb_marques = 0;
	
	
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
    	
        Graph graphe = data.getGraph();
        int size_graphe = graphe.size();
        Boolean path_find = false;
        Node origin = data.getOrigin();
        Node destination = data.getDestination();  
        
        Node current_node = origin;
        
        BinaryHeap<Label> tas = new BinaryHeap();
        
        Label label = new Label(current_node,0,null);
        
        tas.insert(label);
        
        Label label_current;
        
        while (nb_marques < size_graphe || path_find == true) {
        	label_current = tas.findMin();
        	current_node = label_current.node;
        	
        	if (hm.containsKey(current_node) != true) {
        		
        	}
        	
        	
        	
        }
        
        
        return solution;
    }

}
