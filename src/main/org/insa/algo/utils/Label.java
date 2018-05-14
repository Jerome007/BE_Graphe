package org.insa.algo.utils;

import java.util.HashMap;

import org.insa.graph.Arc;
import org.insa.graph.Node;

public class Label implements Comparable<Label> {
	
	protected double cout;
	private Node node;
	private Node prevNode;
	private Arc prevArc;
	protected static HashMap<Integer,Label> dico = new HashMap<Integer,Label>();
	
	public Label(double cout, Node n)
	{
		this.cout = cout;
		this.node = n;
		this.prevNode = null;
		this.prevArc = null;
		dico.put(n.getId(), this);
	}
	
	public void setPrevArc(Arc n)
	{
		this.prevArc = n;
	}
	
	public Arc getPrevArc()
	{
		return this.prevArc;
	}
	
	public void setPrevNode(Node n)
	{
		this.prevNode = n;
	}
	
	public Node getPrevNode()
	{
		return this.prevNode;
	}
	
	public static Label getLabel(int idNode)
	{
		return dico.get(idNode);
	}
	
	public static Label getLabel(Node node)
	{
		return dico.get(node.getId());
	}
	
	public double getCost()
	{
		return this.cout;
	}
	
	public Node getNode()
	{
		return this.node;
	}


	@Override
	public int compareTo(Label o) {
		return (int) (this.getCost() - o.getCost());
	}

	public void setCost(double d) {
		this.cout = d;
	}

}
