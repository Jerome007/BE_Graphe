package org.insa.algo.utils;

import java.util.HashMap;

import org.insa.graph.Arc;
import org.insa.graph.Node;

public class Label implements Comparable<Label> {
	
	private double etiquette;
	private Node node;
	private Node prevNode;
	private Arc prevArc;
	private static HashMap<Integer,Label> dico = new HashMap<Integer,Label>();
	
	public Label(double etiquette, Node n)
	{
		this.etiquette = etiquette;
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
	
	public double getEtiquette()
	{
		return this.etiquette;
	}
	
	public Node getNode()
	{
		return this.node;
	}


	@Override
	public int compareTo(Label o) {
		return (int) (this.etiquette - o.getEtiquette());
	}

	public void setEtiquette(double d) {
		this.etiquette = d;
	}

}
