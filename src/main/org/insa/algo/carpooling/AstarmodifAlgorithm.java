package org.insa.algo.carpooling;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathGraphicObserver;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.Label;
import org.insa.graph.Arc;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.algo.carpooling.*;


public class AstarmodifAlgorithm extends CarPoolingAlgorithm {

	public AstarmodifAlgorithm(CarPoolingData data) {
		super(data);
	}

	@Override
	   protected CarPoolingSolution doRun() {
		
		CarPoolingData data = getInputData();
		CarPoolingSolution solution = null;

		Node origine = null;
		Node origine_secondaire = null;
		Node origine_1 = data.getOrigin().get(0);
		Node origine_2 = data.getOrigin().get(1);
		
		//on execute la PCC sur l'origine la plus loingtaine de la destination
		if ((origine_1.getPoint().distanceTo(data.getDestination().getPoint())) < (origine_2.getPoint().distanceTo(data.getDestination().getPoint()))) {
			origine = origine_2;
			origine_secondaire = origine_1;
		}
		else{
			origine = origine_1;
			origine_secondaire = origine_2;
		}
     
        
       //on trie les noeuds dans le tas en fct du cout par aller jusqu'au noeud, la distance a vol d'oiseau jusqu'a la destination et la distance a vol d'oiseau jusqu'a l'origine secondaire
       double distance_origine_secondaire_pt_rencontre = Double.POSITIVE_INFINITY;
       double distance = Double.POSITIVE_INFINITY;
       Node meeting = null;
       
       //teste tous les noeuds
       for (Node n : data.getGraph())
       {
    	   distance = n.getPoint().distanceTo(data.getDestination().getPoint())+n.getPoint().distanceTo(origine.getPoint())+n.getPoint().distanceTo(origine_secondaire.getPoint());
    	   
    	   if (distance <= distance_origine_secondaire_pt_rencontre) {
    		   distance_origine_secondaire_pt_rencontre = distance;
    		   meeting=n;
    	   }
       }
       
       //Calcul des PCC
       ShortestPathData data1 = new ShortestPathData(data.getGraph(),origine,meeting,data.getInspector());
       ShortestPathData data2 = new ShortestPathData(data.getGraph(),meeting,data.getDestination(),data.getInspector());
       ShortestPathData data3 = new ShortestPathData(data.getGraph(),origine_secondaire,meeting,data.getInspector());
       
       DijkstraAlgorithm AStar1 = new DijkstraAlgorithm(data1);
       AStar1.addObserver(new ShortestPathGraphicObserver(data.drawing));
       DijkstraAlgorithm AStar2 = new DijkstraAlgorithm(data2);
       AStar2.addObserver(new ShortestPathGraphicObserver(data.drawing));
       DijkstraAlgorithm AStar3 = new DijkstraAlgorithm(data3);
       AStar3.addObserver(new ShortestPathGraphicObserver(data.drawing));
       
       ShortestPathSolution solution1 = AStar1.run();
       ShortestPathSolution solution2 = AStar2.run();
       ShortestPathSolution solution3 = AStar3.run();
       

       
        //une des pcc est impossible
        if(solution1.getStatus()==Status.INFEASIBLE || solution2.getStatus()==Status.INFEASIBLE || solution3.getStatus()==Status.INFEASIBLE)
        {
        	//on va renvoyer une solution marquée infaisable
        	solution = new CarPoolingSolution(data, Status.INFEASIBLE);
        }
        else
        {
			//sinon on va renvoyer une solution marquée optimale
        	solution = new CarPoolingSolution(data, Status.OPTIMAL, solution1.getPath(), solution2.getPath(), solution3.getPath());
        }
           
        //on renvoit la solution
        return solution;
	}
}
