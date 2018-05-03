package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.Label;
import org.insa.graph.Arc;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	public DijkstraAlgorithm(ShortestPathData data) {
		super(data);
	}

	@Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        int i = 0;
        Label labelMin;
        //Initialisations
        HashMap<Integer,Node> locked = new HashMap<Integer,Node>();
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
     
       //on met tous les labels des noeuds dans le tas
       for (Node n : data.getGraph())
       {
    	   new Label(Double.POSITIVE_INFINITY,n);
       }
       Label.getLabel(data.getOrigin().getId()).setEtiquette(0);
       tas.insert(Label.getLabel(data.getOrigin().getId())); 
       notifyOriginProcessed(data.getOrigin());

        //Traitement
        while ((!locked.containsKey(data.getDestination().getId())) && !tas.isEmpty())
        {
        	labelMin = tas.deleteMin();
        	locked.put(labelMin.getNode().getId(),labelMin.getNode());
        	notifyNodeMarked(labelMin.getNode());

        	//pour tous les suivants du labelmin
        	for (Arc a : labelMin.getNode())
        	{
        		//if(cost(y)>cost(x)+W(x,y))
        		if (labelMin.getEtiquette()+a.getLength()<Label.getLabel(a.getDestination()).getEtiquette())
        		{
        			//si le cout est diff�rent de l'infini ou si le suivant est deja locked
        			if (Label.getLabel(a.getDestination()).getEtiquette() != Double.POSITIVE_INFINITY && !locked.containsKey(a.getDestination().getId()))
        			{
        				//on le supprime du tas
        				tas.remove(Label.getLabel(a.getDestination()));
        			}
        			else
        			{
        				//sinon, on indique qu'on a atteint le noeud
        				notifyNodeReached(a.getDestination());
        			}
        			
        			//on update le label du suivant
        			Label.getLabel(a.getDestination()).setEtiquette(labelMin.getEtiquette()+a.getLength());
        			Label.getLabel(a.getDestination()).setPrevNode(a.getOrigin());
        			Label.getLabel(a.getDestination()).setPrevArc(a);
        			
        			//et on le reinsert dans le tas
        			tas.insert(Label.getLabel(a.getDestination()));
        		}
        	}
        }
        //si la destination du shortest path est dans le hasmap Locked
        if (locked.containsKey(data.getDestination().getId()))
        {
        	//on indique qu'on a atteint la destination
        	notifyDestinationReached(data.getDestination());
        }
        ArrayList<Arc> listArcs = new ArrayList<Arc>();
        Node n = data.getDestination();
        while (Label.getLabel(n).getPrevNode() != null)
        {
        	listArcs.add(Label.getLabel(n).getPrevArc());
        	n = Label.getLabel(n).getPrevNode();
        }
        Collections.reverse(listArcs);
        
        if(listArcs.size()== 0)
        {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else
        {
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(),listArcs));
        }
        
        
        
        return solution;
    }

}
