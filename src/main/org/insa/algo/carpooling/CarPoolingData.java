package org.insa.algo.carpooling;

import java.util.ArrayList;

import org.insa.algo.AbstractInputData;
import org.insa.algo.ArcInspector;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graphics.drawing.Drawing;

public class CarPoolingData extends AbstractInputData {

    // Origin and destination nodes.
    private final Node origin_1, origin_2, destination;
    public Drawing drawing;

    public CarPoolingData(Graph graph, Node origin_1, Node origin_2, Node destination, ArcInspector arcFilter) {
        super(graph, arcFilter);
        this.origin_1 = origin_1;
        this.origin_2 = origin_2;
        this.destination = destination;
        
    }
    
    public CarPoolingData(Graph graph, Node origin_1, Node origin_2, Node destination, ArcInspector arcFilter,Drawing drawing) {
        super(graph, arcFilter);
        this.origin_1 = origin_1;
        this.origin_2 = origin_2;
        this.destination = destination;
        this.drawing = drawing;
        
    }

    /**
     * @return Origin node for the path.
     */
    public ArrayList<Node>  getOrigin() {
    	ArrayList<Node> retour = new ArrayList<Node>();
    	retour.add(origin_1);
    	retour.add(origin_2);
    	
    	return retour;
    }

    /**
     * @return Destination node for the path.
     */
    public Node getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Carpooling-path from #" + origin_1.getId() + " and from#" + origin_2.getId() + " to #" + destination.getId() + " ["
                + this.arcInspector.toString().toLowerCase() + "]";
    }
    
}
