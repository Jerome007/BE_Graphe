package org.insa.algo.shortestpath;

import org.insa.graph.Arc;
import org.insa.graph.Node;

public class Label implements Comparable<Label>{
	
	public Node node;
	public float cost;
	public Node father;
	public Boolean mark = false;
	public Arc father_arc;
	
	public Label(Node node, float cost, Node father, Arc arc_fath) {
		this.node = node;
		this.cost = cost;
		this.father = father;
		this.father_arc = arc_fath;
	}
	

		public int compareTo(Label o) {
			Label objet = (Label) o;
			if (this.cost == objet.cost) return 0;
			if (this.cost < objet.cost) return -1;
			if (this.cost > objet.cost) return 1;
			
			return 0;
		}
	
}
