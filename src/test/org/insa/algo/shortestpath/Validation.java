package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.RoadInformation.RoadType;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.Label;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;

import org.insa.algo.shortestpath.*;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;





public class Validation {


	    static GraphReader reader;
	    static Graph graph;
	    static ArrayList<int[]> liste_couple = new ArrayList<int[]>();
	    static int nb_couple = 50;
	 
	    
	    public static void initAll() throws IOException {
	        // on importe la carte et on la lit a l'aide couple'un reader
	        String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";

	        reader =  new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
	        graph = reader.read();
	        
	        
	        //on va créer une liste de couple de depart et couple'arrivée
	        for (int i = 0; i<nb_couple ; i++)
	        {
	        	//on génère aléatoirement des id de node appartenant au graphe pour le depart et l'arrivée
	            int depart = ThreadLocalRandom.current().nextInt(0, graph.size());
	            int arrivee = ThreadLocalRandom.current().nextInt(0, graph.size());
	            
	            //on ajoute ensuite les couples
	            int[] list = {depart,arrivee};
                liste_couple.add(list);
	            }	                
	    }


	    @Test
	    public void test_validation() {
	    	
	    	System.out.println("On entre dans le test");
	    	
	    	//on initialise la liste des couples depart-arrivée generés aleatoirement
	    	try {
				initAll();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	        ArrayList<ArrayList<Float>> results = new ArrayList<ArrayList<Float>>(); //Arraylist couple'arraylist des résultats
	        
	        ShortestPathData data;
	        DijkstraAlgorithm dijkstra;
	        AStarAlgorithm astar;
	        ShortestPathSolution sol_dijkstra;
	        ShortestPathSolution sol_astar;
	        
	        int i=0;

	        System.out.println("Calcul solutions liste couple");
	        
	        //On parcourt la liste des couples pour generer les solutions
	        for (int[] couple : liste_couple) 
	        {	        	
	        	//On créé la liste des résultats
	            results.add(new ArrayList<Float>());
	            
	            //on initialise les données du graphe
	            data = new ShortestPathData(graph, graph.get(couple[0]), graph.get(couple[1]), ArcInspectorFactory.getAllFilters().get(4));
	            
	            //on initialise dijkstra et astar
	            dijkstra = new DijkstraAlgorithm(data); //Initialisation Djikstra
	            astar = new AStarAlgorithm(data); //Initialisation A*

	            //on run dijkstra et astar
	            sol_dijkstra = dijkstra.run();
	            sol_astar = astar.run();
	            
	            //Si faisable par dijkstra
	            if (sol_dijkstra.isFeasible())
	            {
	                results.get(i).add(sol_dijkstra.getPath().getLength()); //On ajoute la distance du trajet
	                results.get(i).add((float)sol_dijkstra.getPath().getMinimumTravelTime()); //On ajoute le temps de trajet
	            }
	            else { //Sinon on ajoute 0
	                results.get(i).add((float)0);
	                results.get(i).add((float)0);
	            }
	            
	            
	            //Si faisable par astar
	            if (sol_astar.isFeasible())
	            {
	                results.get(i).add(sol_astar.getPath().getLength());
	                results.get(i).add((float)sol_astar.getPath().getMinimumTravelTime());
	            }
	            else
	            {
	                results.get(i).add((float)0);
	                results.get(i).add((float)0);
	            }
	            i++;
	        }
	        
	        //on reinitialise i
	        i=0;
	        
	        
	        
	        System.out.println("Parcours des resultats");
	        //Parcours des résultats
	        for (ArrayList<Float> couple_node: results)
	        {
	        	//get(0) => distance dijkstra
	        	//get(1) => temps dijkstra
	        	
	        	//get(2) => distance astar
	        	//get(3) => temps astar
	        	
	        	//on calcul un delta de 3% de marge d'erreur entre A* et Dijkstra
	        	double delta_distance = 0.03 * couple_node.get(0).doubleValue();
	        	double delta_temps = 0.03 * couple_node.get(0).doubleValue();
	        	
	            //On verifie si les résultats de Djikstra et A* sont égaux (3% de marge)
	        	System.out.println("Depart: " + liste_couple.get(i)[0] + " Arrivée: " + liste_couple.get(i)[1] + " Distance Dijkstra: " + couple_node.get(0).doubleValue() + " Distance Astar: " + couple_node.get(2).doubleValue());
	        	System.out.println("Depart: " + liste_couple.get(i)[0] + " Arrivée: " + liste_couple.get(i)[1] + " Temps Dijkstra: " + couple_node.get(1).doubleValue() + " Temps Astar: " + couple_node.get(3).doubleValue() + "\n");
	        	
	        	//en distance
	        	assertEquals(couple_node.get(0).doubleValue(), couple_node.get(2).doubleValue(), delta_distance);
	        	
	        	//en temps
	        	assertEquals(couple_node.get(0).doubleValue(), couple_node.get(2).doubleValue(), delta_temps);
	        	
	        	//on incremente i
	        	i++;
	        }     
	        System.out.println("Fin du test");
	    }
}
