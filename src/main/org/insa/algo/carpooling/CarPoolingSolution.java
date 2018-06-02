package org.insa.algo.carpooling;

import org.insa.algo.AbstractSolution;
import org.insa.algo.AbstractInputData;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.Arc;
import org.insa.graph.Path;

import org.insa.graph.Node;

public class CarPoolingSolution extends AbstractSolution {

	// Optimal solution.
    private Path path;
    private Path path2;
    private Path path3;
    
    /**
     * {@inheritDoc}
     */
    protected CarPoolingSolution(CarPoolingData data, Status status) {
        super(data, status);
    }

    /**
     * Create a new infeasible shortest-path solution for the given input and
     * status.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (UNKNOWN / INFEASIBLE).
     */
    public CarPoolingSolution(AbstractInputData data, Status status) {
        super(data, status);
    }

    /**
     * Create a new shortest-path solution.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (FEASIBLE / OPTIMAL).
     * @param path Path corresponding to the solution.
     */
    public CarPoolingSolution(CarPoolingData data, Status status, Path path, Path path2, Path path3) {
        super(data, status);
        this.path = path;
        this.path2 = path2;
        this.path3 = path3;
    }

    @Override
    public CarPoolingData getInputData() {
        return (CarPoolingData) super.getInputData();
    }

    /**
     * @return The path of this solution, if any.
     */
    public Path getPath() {
        return path;
    }
    
    public Path getPath2() {
        return path2;
    }
    
    public Path getPath3() {
        return path3;
    }

    @Override
    public String toString() {
        String info = null;
        if (!isFeasible()) {
            info = String.format("No path found from node #%d and node #%d to node #%d",
                    getInputData().getOrigin().get(0).getId(), getInputData().getOrigin().get(1).getId(), getInputData().getDestination().getId());
        }
        else {
            double cost = 0;
            for (Arc arc: getPath().getArcs()) {
                cost += getInputData().getCost(arc);
            }
            info = String.format("Found a path from node #%d and node #%d to node #%d",
            		getInputData().getOrigin().get(0).getId(), getInputData().getOrigin().get(1).getId(), getInputData().getDestination().getId());
            if (getInputData().getMode() == Mode.LENGTH) {
                info = String.format("Origin to Destination: %s, %.4f kilometers", info, cost / 1000.0);
            }
            else {
                info = String.format("%s, %.4f minutes", info, cost / 60.0);
            }
        }
        info += " in " + getSolvingTime().getSeconds() + " seconds.";
        return info;
    }
    
    

}
