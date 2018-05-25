package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.Label;
import org.insa.algo.utils.LabelStar;
import org.insa.graph.Arc;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;

        LabelStar labelMin;
        //Initialisations
        HashMap<Integer,Node> locked = new HashMap<Integer,Node>();
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        int mode = 1;
        if (this.data.getMode().equals(Mode.TIME)) mode = (1/this.data.getMaximumSpeed());
        if(mode==-1) mode=1/100;
        
        double estimation = 0;
        
       //on met tous les labels des noeuds dans le tas
       for (Node n : data.getGraph())
       {
    	   new LabelStar(Double.POSITIVE_INFINITY,n,n.getPoint().distanceTo(data.getDestination().getPoint()) * mode);
       }
       LabelStar.getLabel(data.getOrigin().getId()).setCost(0,0);
       tas.insert(LabelStar.getLabel(data.getOrigin().getId())); 
       notifyOriginProcessed(data.getOrigin());
       
       
        //Traitement
        while ((!locked.containsKey(data.getDestination().getId())) && !tas.isEmpty())
        {
        	labelMin = (LabelStar) tas.deleteMin();
        	locked.put(labelMin.getNode().getId(),labelMin.getNode());
        	//on colorie les noeuds marqués
        	notifyNodeMarked(labelMin.getNode());


        	//pour tous les suivants du labelmin
        	for (Arc a : labelMin.getNode())
        	{
        		//Small test to check allowed roads...
				if (!data.isAllowed(a)) {
					continue;
				}
					
	
				//if(cost(y)>cost(x)+W(x,y))
        		if (labelMin.getCost()+data.getCost(a) < LabelStar.getLabel(a.getDestination()).getCost())
        		{
        			//si le cout est différent de l'infini ou si le suivant est deja locked
        			if (LabelStar.getLabel(a.getDestination()).getCost() != Double.POSITIVE_INFINITY && !locked.containsKey(a.getDestination().getId()))
        			{
        				//on le supprime du tas
        				tas.remove(LabelStar.getLabel(a.getDestination()));
        			}
        			else
        			{
        				//sinon, on indique qu'on a atteint le noeud
        				notifyNodeReached(a.getDestination());
        			}
	        			
        			//on update l'estimation de la distance restante entre le noeud et la destination
        			estimation = a.getDestination().getPoint().distanceTo(data.getDestination().getPoint()) * mode;
        			LabelStar.getLabel(a.getDestination()).setEstim(estimation);
	        			
	        			
        			//on update le label du suivant
        			LabelStar.getLabel(a.getDestination()).setCost(labelMin.getCout()+data.getCost(a));
        			LabelStar.getLabel(a.getDestination()).setPrevNode(a.getOrigin());
        			LabelStar.getLabel(a.getDestination()).setPrevArc(a);
        			
        			//et on le reinsert dans le tas
        			tas.insert(LabelStar.getLabel(a.getDestination()));
        		}
	        }
        	
        }
        
        //si la destination du shortest path est dans le hasmap Locked
        if (locked.containsKey(data.getDestination().getId()))
        {
        	//on indique qu'on a atteint la destination
        	notifyDestinationReached(data.getDestination());
        }
        
        //on va creer la liste d'arc solution
        ArrayList<Arc> listArcs = new ArrayList<Arc>();
        //on recupere la destination de shortestpath
        Node n = data.getDestination();
        //tant que il existe un precedent
        while (LabelStar.getLabel(n).getPrevNode() != null)
        {
        	//on ajoute l'arc vers le precedent(contenu dans label) a la liste
        	listArcs.add(LabelStar.getLabel(n).getPrevArc());
        	n = LabelStar.getLabel(n).getPrevNode();
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
