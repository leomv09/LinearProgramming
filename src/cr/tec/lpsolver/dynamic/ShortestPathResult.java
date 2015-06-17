/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic;

import java.util.List;

/**
 *
 * @author Leo
 */
public class ShortestPathResult {
    private final double value;// The optimum cost value.
    private final List<String> routes;// The optimum paths.
    
    /**
     * Creates a new instance of the shorest path result.
     * @param Value The optimum value.
     * @param Routes A list of optimum paths.
     */
    public ShortestPathResult(double Value, List<String> Routes)
    {
        this.value = Value;
        this.routes = Routes;
    }

    /**
     * Obtains the optimum value.
     * @return 
     */
    public double getValue() {
        return value;
    }

    /**
     * Obtains the optimum paths
     * @return 
     */
    public List<String> getRoutes() {
        return routes;
    }
    
    
}
