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
       //notifyOriginProcessed(data.getOrigin());

        //Traitement
        while ((!locked.containsKey(data.getDestination().getId())) && !tas.isEmpty())
        {
        	labelMin = tas.deleteMin();
        	locked.put(labelMin.getNode().getId(),labelMin.getNode());
        	//on colorie les noeuds marqués
        	notifyNodeMarked(labelMin.getNode());

        	//pour tous les suivants du labelmin
        	for (Arc a : labelMin.getNode())
        	{
        		// Small test to check allowed roads...
				if (!data.isAllowed(a)) {
					continue;
				}
				
        		//if(cost(y)>cost(x)+W(x,y))
        		if (labelMin.getEtiquette()+a.getLength()<Label.getLabel(a.getDestination()).getEtiquette())
        		{
        			//si le cout est différent de l'infini ou si le suivant est deja locked
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
        	//notifyDestinationReached(data.getDestination());
        }
        
        //on va creer la liste d'arc solution
        ArrayList<Arc> listArcs = new ArrayList<Arc>();
        //on recupere la destination de shortestpath
        Node n = data.getDestination();
        //tant que il existe un precedent
        while (Label.getLabel(n).getPrevNode() != null)
        {
        	//on ajoute l'arc vers le precedent(contenu dans label) a la liste
        	listArcs.add(Label.getLabel(n).getPrevArc());
        	n = Label.getLabel(n).getPrevNode();
        }
        //on inverse la liste d'arc pour mettre la destination a la fin
        Collections.reverse(listArcs);
        
        //si la liste est vide
        if(listArcs.size()== 0)
        {
        	//on va renvoyer une solution marquée infaisable
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else
        {
        	//sinon on va renvoyer une solution marquée optimale
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(),listArcs));
        }
        
        
        //on renvoit la solution
        return solution;
    }

}
