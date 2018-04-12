package org.insa.algo.shortestpath;


import java.util.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.EmptyPriorityQueueException;
import org.insa.graph.Arc;
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
        
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        Label label = new Label(current_node,0,null,null);
        hm.put(current_node, label);
        
        tas.insert(label);
        
        Label label_current = null;
        Node y = null;
        Label label_y = null;
        
        float cout_y = 0;
        
        while (nb_marques < size_graphe && path_find == false) {
        	System.out.println(nb_marques);
        	
        	try {
        		label_current = tas.findMin();
        	}
        	catch (EmptyPriorityQueueException e) {
        		path_find = true;
        	}
        	
        	current_node = label_current.node;
        	label_current.mark=true;
        	
        	if (current_node == destination) {
    			System.out.println("trouvé sale tepu");
    			path_find = true;
        	}
        	tas.deleteMin();
        	nb_marques++;
        	
        	
        	for (Arc arc: current_node) {
        		y = arc.getDestination();
        		if (hm.containsKey(y) != true) {
        			hm.put(y,new Label(y,-1,current_node,arc));
        		}
        		
        		label_y = hm.get(y);
        		
           		
        		if (label_y.mark == false) {
        			cout_y = label_y.cost;
        			
        			if (label_y.cost > label_current.cost+arc.getLength() || label_y.cost == -1) {
        				label_y.cost = label_current.cost+arc.getLength();	
        			}
        			if (cout_y != -1) {
        				tas.remove(label_y); //on update le noeud dans le tas
        			}
    				tas.insert(label_y);
        		}
        	}
        	
        }
        
        ArrayList<Arc> listeSol = new ArrayList<Arc>();
		
		
		while(label_current.node != origin) {    				
			listeSol.add(label_current.father_arc);
			label_current = hm.get(label_current.father);
		}
		
		Collections.reverse(listeSol);
		
		return solution;
		
    }

}
