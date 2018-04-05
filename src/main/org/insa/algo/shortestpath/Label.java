package org.insa.algo.shortestpath;

import org.insa.graph.Node;

public class Label implements Comparable<Label>{
	
	public Node node;
	public int cost;
	public Node father;
	public Boolean mark = false;
	
	public Label(Node node, int cost, Node father) {
		this.node = node;
		this.cost = cost;
		this.father = father;
	}
	
		public int compareTo(Object o) {
			Label objet = (Label) o;
			if (this.cost == objet.cost) return 0;
			if (this.cost < objet.cost) return -1;
			if (this.cost > objet.cost) return 1;
			
			return 0;
		}
	
}
