package org.insa.algo.utils;

import org.insa.graph.Node;

public class LabelStar extends Label implements Comparable<Label>{

	private double cout_estime_dest;
	
	public LabelStar(double etiquette, Node n, double estimation) {
		super(etiquette, n);
		this.cout_estime_dest = estimation;
	}
	
	public double getCost()
	{
		return (this.cout + this.cout_estime_dest);
	}
	
	public void setEstim(double estimation) {
		this.cout_estime_dest = estimation;
	}
	
	public static LabelStar getLabel(int idNode)
	{
		return (LabelStar) dico.get(idNode);
	}
	
	public static LabelStar getLabel(Node node)
	{
		return (LabelStar) dico.get(node.getId());
	}
	
}
