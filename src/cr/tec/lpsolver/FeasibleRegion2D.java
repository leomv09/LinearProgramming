package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import math.geom2d.Box2D;
import math.geom2d.Point2D;
import math.geom2d.line.AbstractLine2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.Ray2D;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.polygon.SimplePolygon2D;

/**
 * Represent a feasible region defined by a collection of {@link Constraint}.
 * 
 * @author Leo
 */
public class FeasibleRegion2D {
    
    /**
     * The vertex of the feasible region.
     */
    private List<Point2D> vertex;
    
    /**
     * A list of lines that defines the area of the feasible region.
     */
    private List<AbstractLine2D> lines;
    
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
     * 
     * @param variables An array that contains the name of the variables in the problem.
     * 
     * @throws IllegalArgumentException If more than 2 variables are supplied.
     */
    public FeasibleRegion2D(List<String> variables) throws IllegalArgumentException {
        if (variables.size() != 2) {
            throw new IllegalArgumentException("The plot must have 2 dimensions.");
        }
        this.lines = new ArrayList<>();
        this.vertex = new ArrayList<>();
        this.constraints = new ArrayList<>();
        this.variables = variables;
    }

    /**
     * Construct the {@code FeasibleRegion} defined by the given constraint.
     * 
     * @param variables An array that contains the name of the variables in the problem.
     * @param constraint The constraint.
     */
    public FeasibleRegion2D(List<String> variables, Constraint constraint) {
        this(variables);
        this.constraints.add(constraint);
        
        Linear linear = constraint.getLinear();
        Relationship relation = constraint.getRelationship();

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
        boolean intersectsX = intersectionX >= 0;
        boolean intersectsY = intersectionY >= 0;
        
        if (intersectsX && intersectsY) {
            if (x*y < 0) { /* Increasing. */
                addVertex(GeometryUtils.createPoint(0, 0));
                addLine(GeometryUtils.createRay(0, 0, 1, 1));
                if (relation == Relationship.LEQ) {
                    addLine(GeometryUtils.createRay(0, 0, 1, 0));
                } 
                else {
                    addLine(GeometryUtils.createRay(0, 0, 0, 1));
                }
            }
            else { /* Decreasing. */
                addVertex(GeometryUtils.createPoint(intersectionX, 0));
                addVertex(GeometryUtils.createPoint(0, intersectionY));
                addLine(GeometryUtils.createSegment(0, intersectionY, intersectionX, 0));
                if (relation == Relationship.LEQ) {
                    addVertex(GeometryUtils.createPoint(0, 0));
                    addLine(GeometryUtils.createSegment(0, 0, 0, intersectionY));
                    addLine(GeometryUtils.createSegment(0, 0, intersectionX, 0));
                }
                else {
                    addLine(GeometryUtils.createRay(0, intersectionY, 0, intersectionY+1));
                    addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX+1, 0));
                } 
            }
        }
        else if (intersectsX) {
            addVertex(GeometryUtils.createPoint(intersectionX, 0));
            if (x*y < 0) { /* Increasing. */
                if (relation == Relationship.LEQ) {
                    addLine(GeometryUtils.createRay(0, 0, 0, 1));
                    addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX+1, (c-x*(intersectionX+1))/y));
                    addLine(GeometryUtils.createSegment(0, 0, intersectionX, 0));
                }
                else {
                    addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX+1, 0));
                    addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX+1, (c-x*(intersectionX+1))/y));
                }
            }
            else { /* Constant. */
                if (relation == Relationship.LEQ) {
                    addVertex(GeometryUtils.createPoint(0, 0));
                    addLine(GeometryUtils.createRay(0, 0, 0, 1));
                    addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX, 1));
                    addLine(GeometryUtils.createSegment(0, 0, intersectionX, 0));
                }
                else {
                    addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX, 1));
                    addLine(GeometryUtils.createRay(intersectionX, 0, intersectionX+1, 0));
                }
            }
        }
        else if (intersectsY) {
            addVertex(GeometryUtils.createPoint(0, intersectionY));
            if (x*y < 0) { /* Increasing. */
                if (relation == Relationship.LEQ) {
                    addLine(GeometryUtils.createRay(0, intersectionY, 1, (c-x)/y));
                    addLine(GeometryUtils.createRay(0, 0, 1, 0));
                    addLine(GeometryUtils.createSegment(0, 0, 0, intersectionY));
                }
                else {
                    addLine(GeometryUtils.createRay(0, intersectionY, 0, intersectionY+1));
                    addLine(GeometryUtils.createRay(0, intersectionY, 1, (c-x)/y));
                }
            }
            else { /* Constant. */
                if (relation == Relationship.LEQ) {
                    addVertex(GeometryUtils.createPoint(0, 0));
                    addLine(GeometryUtils.createRay(0, 0, 1, 0));
                    addLine(GeometryUtils.createRay(0, intersectionY, 1, intersectionY));
                    addLine(GeometryUtils.createSegment(0, 0, 0, intersectionY));
                }
                else {
                    addLine(GeometryUtils.createRay(0, intersectionY, 0, intersectionY+1));
                    addLine(GeometryUtils.createRay(0, intersectionY, 1, intersectionY));
                }
            }
        }
        else {
            // Full positive quadrant.
            if (constraint.getRelationship() == Relationship.GEQ) {
                addVertex(GeometryUtils.createPoint(0, 0));
                addLine(GeometryUtils.createRay(0, 0, 0, 1));
                addLine(GeometryUtils.createRay(0, 0, 1, 0));
            }
        }
        
        sortVertex();
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
    public FeasibleRegion2D intersection(FeasibleRegion2D region) {
        FeasibleRegion2D r = new FeasibleRegion2D(this.variables);
        r.constraints.addAll(this.constraints);
        r.constraints.addAll(region.constraints);
        
        for (Point2D point : GeometryUtils.getIntersections(this.lines, region.lines)) {
            addVertex(r.vertex, point);
        }
        
        for (Point2D point : Utils.merge(this.vertex, region.vertex)) {
            if (this.contains(point) && region.contains(point)) {
                addVertex(r.vertex, point);
            }
        } 
        
        if (!r.vertex.isEmpty()) {
            List<Point2D> intersections;
            Point2D start, end;
            
            for (AbstractLine2D line : Utils.merge(this.lines, region.lines)) {
                intersections = GeometryUtils.getIntersections(line, r.vertex);
                if ( (intersections.size() == 1) && (line instanceof Ray2D) ) {
                    start = intersections.get(0);
                    addLine(r.lines, new Ray2D(start, line.direction().angle()));
                }
                else if (intersections.size() == 2) {
                    start = intersections.get(0);
                    end = intersections.get(1);
                    addLine(r.lines, new LineSegment2D(start, end));
                }
            }
        }
        
        r.sortVertex();
        return r;
    }
    
    /**
     * Get the clipped region to the box described as (0, x, 0, y).
     * 
     * @param x The width of the box.
     * @param y The height of the box.
     * 
     * @return The clipped region.
     */
    public FeasibleRegion2D clip(double x, double y) {
        FeasibleRegion2D r = new FeasibleRegion2D(this.variables);
        
        Linear linear;
        Constraint constraint;
        
        linear = new Linear();
        linear.add(1, variables.get(0));
        constraint = new Constraint(linear, Relationship.LEQ, x);
        r.constraints.add(constraint);
        
        linear = new Linear();
        linear.add(1, variables.get(1));
        constraint = new Constraint(linear, Relationship.LEQ, y);
        r.constraints.add(constraint);
        
        r.addVertex(GeometryUtils.createPoint(0, 0));
        r.addVertex(GeometryUtils.createPoint(0, y));
        r.addVertex(GeometryUtils.createPoint(x, 0));
        r.addVertex(GeometryUtils.createPoint(x, y));
        
        r.addLine(GeometryUtils.createSegment(0, 0, 0, y));
        r.addLine(GeometryUtils.createSegment(0, y, x, y));
        r.addLine(GeometryUtils.createSegment(x, y, x, 0));
        r.addLine(GeometryUtils.createSegment(x, 0, 0, 0));
        
        return intersection(r);
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
     * Sort the list of vertex by adjacency.
     * 
     * The points are sorted by the angle respect to the center of the bounding box.
     * Using the formula atan2(point.y - center.y, point.x - center.x).
     * If two points have the same angle, the one closer to the center go first.
     */
    private void sortVertex() {
        Polygon2D polygon = new SimplePolygon2D(vertex);
        Box2D box = polygon.boundingBox();
        
        double centerX = box.getMinX() + ((box.getMaxX() - box.getMinX()) / 2);
        double centerY = box.getMinY() + ((box.getMaxY() - box.getMinY()) / 2);
        Point2D center = new Point2D(centerX, centerY);

        Collections.sort(vertex, (Point2D p1, Point2D p2) -> {
            double angle1 = Math.atan2(p1.y() - center.y(), p1.x() - center.x());
            double angle2 = Math.atan2(p2.y() - center.y(), p2.x() - center.x());
            double comparation = angle1 - angle2;
            if (comparation == 0) {
                comparation = center.distance(p1) - center.distance(p2);
                if (comparation > 0) return 1;
                else if (comparation < 0) return -1;
                else return 0;
            }
            else if (comparation > 0) return 1;
            else return -1;
        });
    }
    
    /**
     * Returns true if the region is empty (Do not have any vertex).
     * 
     * @return True if the is empty.
     */
    public boolean isEmpty() {
        return vertex.isEmpty() && lines.isEmpty();
    }
    
    /**
     * Returns true if the region is bounded.
     * 
     * @return True if the is bounded.
     */
    public boolean isBounded() {
        if (!isEmpty()) {
            for (AbstractLine2D line : lines) {
                if (line instanceof Ray2D) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     * Get the vertex with the maximum X coordinate.
     * 
     * @return The vertex with the maximum X coordinate.
     */
    public double getMaxX() {
        Polygon2D polygon = new SimplePolygon2D(vertex);
        Box2D box = polygon.boundingBox();
        return box.getMaxX();
    }
    
    /**
     * Get the vertex with the maximum Y coordinate.
     * 
     * @return The vertex with the maximum Y coordinate.
     */
    public double getMaxY() {
        Polygon2D polygon = new SimplePolygon2D(vertex);
        Box2D box = polygon.boundingBox();
        return box.getMaxY();
    }
    
    /**
     * Get the vertex.
     * 
     * @return The vertex. 
     */
    public List<Point2D> getVertex() {
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
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
