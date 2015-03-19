package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import math.geom2d.Point2D;
import math.geom2d.line.AbstractLine2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.Ray2D;

/**
 * Represent a feasible region defined by a collection of {@link Constraint}.
 * 
 * @author Leo
 */
public class FeasibleRegion {
    
    /**
     * The vertex of the feasible region.
     */
    private Set<Point2D> vertex;
    
    /**
     * A list of lines that defines the area of the feasible region.
     */
    private Set<AbstractLine2D> lines;
    
    /**
     * A list of constraints that defines the region.
     */
    private List<Constraint> constraints;
    
    /**
     * The list of variables of the problem.
     */
    private List<String> variables;
    
    /**
     * Construct a empty {@code FeasibleRegion} object.
     */
    private FeasibleRegion() {
        this.lines = new HashSet<>();
        this.vertex = new HashSet<>();
        this.constraints = new ArrayList<>();
        this.variables = new ArrayList<>();
        this.variables.add("x");
        this.variables.add("y");
    }

    /**
     * Construct the {@code FeasibleRegion} defined by the given constraint.
     * 
     * @param variables An array that contains the name of the variables in the problem.
     * @param constraint The constraint.
     * 
     * @throws IllegalArgumentException If more than 2 variables are supplied.
     */
    public FeasibleRegion(List<String> variables, Constraint constraint) throws IllegalArgumentException  {
        this();
        this.variables = variables;
        this.constraints.add(constraint);
        
        if (variables.size() != 2) {
            throw new IllegalArgumentException("The plot must have 2 dimensions.");
        }
        
        Linear linear = constraint.getLinear();

        // Get the name of the equation variables.
        String xVariable = variables.get(0);
        String yVariable = variables.get(1);
        
        // Get the equation constant and coefficients.
        double x = linear.getCoefficient(xVariable);
        double y = linear.getCoefficient(yVariable);
        double c = constraint.getConstant();

        // Intersection with the axis.
        double intersectionX = c/x;
        double intersectionY = c/y;
        
        // Flags
        boolean intersectsX = false;
        boolean intersectsY = false;
        
        // Add the intersection with the X axis to the set of points.
        if (linear.containsVariable(xVariable)) {
            Point2D xPoint = new Point2D(intersectionX, 0);
            if (GeometryUtils.isPositivePoint(xPoint)) {
                addVertex(xPoint);
                intersectsX = true;
            }
        }
        
        // Add the intersection with the Y axis to the set of points.
        if (linear.containsVariable(yVariable)) {
            Point2D yPoint = new Point2D(0, intersectionY);
            if (GeometryUtils.isPositivePoint(yPoint)) {
                addVertex(yPoint);
                intersectsY = true;
            }
        }
        
        if (constraint.getRelationship() == Relationship.LEQ) {
            // The line has a slope.
            if (linear.containsVariable(xVariable) && linear.containsVariable(yVariable)) {
                // The line has increasing slope.
                if (x/y < 0) {
                    if (intersectsX) {
                        addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX+1, 0));
                        addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX+1, (c-x*(intersectionX+1))/y));
                    }
                    else if (intersectsY) {
                        addLine(GeometryUtils.createRay(0, intersectionY, 1, (c-x)/y));
                        addLine(GeometryUtils.createRay(0, 0, 1, 0));
                        addLine(GeometryUtils.createSegment(0, 0, 0, intersectionY));
                    }
                }
                // The line has decreasing slope.
                else {
                    addVertex(GeometryUtils.createPoint(0, 0));
                    addLine(GeometryUtils.createSegment(0, 0, 0, intersectionY));
                    addLine(GeometryUtils.createSegment(0, 0, intersectionX, 0));
                    addLine(GeometryUtils.createSegment(0, intersectionY, intersectionX, 0));
                }
            }
            // The line is constant.
            else {
                if (intersectsX) {
                    addLine(GeometryUtils.createRay(0, 0, 0, 1));
                    addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX, 1));
                    addLine(GeometryUtils.createSegment(0, 0, intersectionX, 0));
                }
                else if (intersectsY) {
                    addLine(GeometryUtils.createRay(0, 0, 1, 0));
                    addLine(GeometryUtils.createRay(0, intersectionY, 1, intersectionY));
                    addLine(GeometryUtils.createSegment(0, 0, 0, intersectionY));
                }
            }
        }
        else if (constraint.getRelationship() == Relationship.GEQ) {
            // The line has a slope.
            if (linear.containsVariable(xVariable) && linear.containsVariable(yVariable)) {
                // The line has increasing slope.
                if (x/y < 0) {
                    if (intersectsX) {
                        addLine(GeometryUtils.createRay(0, 0, 0, 1));
                        addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX+1, (c-x*(intersectionX+1))/y));
                        addLine(GeometryUtils.createSegment(0, 0, intersectionX, 0));
                    }
                    else if (intersectsY) {
                        addLine(GeometryUtils.createRay(0, intersectionY, 0, intersectionY+1));
                        addLine(GeometryUtils.createRay(0, intersectionY, 1, (c-x)/y));
                    }
                }
                // The line has decreasing slope.
                else {
                    addLine(GeometryUtils.createRay(0, intersectionY, 0, intersectionY+1));
                    addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX+1, 0));
                    addLine(GeometryUtils.createSegment(0, intersectionY, intersectionX, 0));
                }
            }
            // The line is constant.
            else {
                if (intersectsX) {
                    addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX, 1));
                    addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX+1, 0));
                }
                else if (intersectsY) {
                    addLine(GeometryUtils.createRay(0, intersectionY, 0, intersectionY+1));
                    addLine(GeometryUtils.createRay(0, intersectionY, 1, intersectionY));
                }
            }
        }
    }
    
    /**
     * Check if the given point is contained in this region.
     * 
     * @param point The point.
     * 
     * @return True if the point is contained in the region.
     */
    public boolean contains(Point2D point) {
        return contains(point.x(), point.y());
    }
    
    /**
     * Check if the given point is contained in this region.
     * 
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * 
     * @return True if the point is contained in the region.
     */
    public boolean contains(double x, double y) {
        Map<String, Double> parameters = new HashMap<>();
        parameters.put(variables.get(0), x);
        parameters.put(variables.get(1), y);
        
        for (Constraint c : constraints) {
            if (!c.satisfies(parameters)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Get the intersection between this {@code FeasibleRegion} and the given {@code FeasibleRegion}.
     * 
     * @param region The feasible region.
     * 
     * @return The intersection with the given feasible region.
     */
    public FeasibleRegion intersection(FeasibleRegion region) {
        Set<AbstractLine2D> newLines = new HashSet<>();

        Set<Point2D> newVertex = new HashSet<>();
        GeometryUtils.getIntersections(this.lines, region.lines).stream().forEach((point) -> {
            addVertex(newVertex, point);
        });
        
        Collection<Point2D> vertexUnion = new HashSet<>();
        vertexUnion.addAll(this.vertex);
        vertexUnion.addAll(region.vertex);
        
        vertexUnion.stream().filter((point) -> (this.contains(point) && region.contains(point))).forEach((point) -> {
            addVertex(newVertex, point);
        });
        
        if (!newVertex.isEmpty()) {
            
            Collection<AbstractLine2D> linesUnion = new HashSet<>();
            linesUnion.addAll(this.lines);
            linesUnion.addAll(region.lines);
            
            List<Point2D> intersections;
            Point2D start, end;
            
            for (AbstractLine2D line : linesUnion) {
                intersections = GeometryUtils.getIntersections(line, newVertex);

                if ( (intersections.size() == 1) && (line instanceof Ray2D) ) {
                    
                    start = intersections.get(0);
                    addLine(newLines, new Ray2D(start, line.direction().angle()));

                }
                else if (intersections.size() == 2) {
                    
                    start = intersections.get(0);
                    end = intersections.get(1);
                    addLine(newLines, new LineSegment2D(start, end));
                    
                }
                
            }
            
        }
        
        FeasibleRegion r = new FeasibleRegion();
        
        r.lines = newLines;
        r.vertex = newVertex;
        r.constraints.addAll(this.constraints);
        r.constraints.addAll(region.constraints);
        r.variables = this.variables;
        
        return r;
    }
    
    /**
     * Add a line to a list if the line is not empty and the list doesn't already contains the line.
     * 
     * @param list The list.
     * @param line The line.
     */
    private void addLine(Collection<AbstractLine2D> list, AbstractLine2D line) {
        if (!line.isEmpty() && !GeometryUtils.containsLine(list, line)) {
            list.add(line);
        }
    }
    
    /**
     * Add a line if the line is not empty and the list doesn't already contains the line.
     * 
     * @param line The line.
     */
    private void addLine(AbstractLine2D line) {
        addLine(this.lines, line);
    }
    
    /**
     * Add a vertex if it is doesn't already in the region.
     * 
     * @param point The point.
     */
    private void addVertex(Point2D point) {
        addVertex(this.vertex, point);
    }
    
    /**
     * Add a point to a list if list doesn't already contains the point.
     * 
     * @param list The list.
     * @param point The point.
     */
    private void addVertex(Collection<Point2D> list, Point2D point) {
        if (!GeometryUtils.containsPoint(list, point)) {
            list.add(point);
        }
    }
    
    /**
     * Returns true if the region is empty (Do not have any vertex).
     * 
     * @return True if the is empty.
     */
    public boolean isEmpty() {
        return this.vertex.isEmpty() && this.lines.isEmpty();
    }
    
    /**
     * Returns true if the region is bounded.
     * 
     * @return True if the is bounded.
     */
    public boolean isBounded() {
        return !this.isEmpty() && lines.stream().noneMatch((line) -> (line instanceof Ray2D));
    }
    
    /**
     * Get the vertex.
     * 
     * @return The vertex. 
     */
    public Collection<Point2D> getVertex() {
        return vertex;
    }
    
    /**
     * Get the lines.
     * 
     * @return The lines.
     */
    public Collection<AbstractLine2D> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FeasibleRegion");
        
        if (this.isBounded()) {
            sb.append(" [BOUNDED]");
        }
        else {
            sb.append(" [UNBOUNDED]");
        }
        
        if (this.isEmpty()) {
            sb.append(" [EMPTY]");
        }
        
        sb.append("\nPoints = [");
        
        for (Point2D point : vertex) {
            sb.append(" (").append(point.x()).append(", ").append(point.y()).append(")");
        }
        
        sb.append(" ]\nLines = ").append(lines).append("\n");

        return sb.toString();
    }
}
