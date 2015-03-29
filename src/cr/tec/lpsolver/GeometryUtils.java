package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import math.geom2d.Point2D;
import math.geom2d.line.AbstractLine2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.Ray2D;

/**
 * Provides some geometric functions.
 * 
 * @author jose
 */
public class GeometryUtils {
    
    /**
     * Creates a new point.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * 
     * @return  The point.
     */
    public static Point2D createPoint(double x, double y) {
        return new Point2D(x, y);
    }
    
    /**
     * Creates a new segment that goes from (x1, y1) to (x2, y2).
     * 
     * @param x1 The x coordinate of the start point.
     * @param y1 The y coordinate of the start point.
     * @param x2 The x coordinate of the end point.
     * @param y2 The y coordinate of the end point.
     * 
     * @return The segment
     */
    public static LineSegment2D createSegment(double x1, double y1, double x2, double y2) {
        Point2D start = new Point2D(x1, y1);
        Point2D end = new Point2D(x2, y2);
        return new LineSegment2D(start, end);
    }
    
    /**
     * Creates a new ray that starts in (x1, y1) and extends infinitely in direction to (x2, y2).
     * 
     * @param x1 The x coordinate of the start point.
     * @param y1 The y coordinate of the start point.
     * @param x2 The x coordinate of the direction point.
     * @param y2 The y coordinate of the direction point.
     * 
     * @return The ray.
     */
    public static Ray2D createRay(double x1, double y1, double x2, double y2) {
        Point2D start = new Point2D(x1, y1);
        Point2D direction = new Point2D(x2, y2);
        return new Ray2D(start, direction);
    }
    
    /**
     * Get all the intersection point from the cross product of two collection of lines.
     * 
     * @param l1 The first collection.
     * @param l2 The second collection.
     * 
     * @return The intersection points. 
     */
    public static Collection<Point2D> getIntersections(Collection<AbstractLine2D> l1, Collection<AbstractLine2D> l2) {
        Collection<Point2D> intersections = new HashSet<>();
        Point2D intersection;
        
        for (AbstractLine2D line1 : l1) {
            for (AbstractLine2D line2 : l2) {
                intersection = line1.intersection(line2);
                if (intersection != null) {
                    intersections.add(intersection);
                }
            }
        }
        
        return intersections;
    }
    
    /**
     * Get all the points that are contained in the given line.
     * 
     * @param line The line.
     * @param points The points.
     * 
     * @return The points that are contained in the line.
     */
    public static List<Point2D> getIntersections(AbstractLine2D line, Collection<Point2D> points) {
        Set<Point2D> intersections = new HashSet<>();

        for (Point2D p : points) {
            if (line.contains(p)) {
                intersections.add(p);
            }
        }

        return new ArrayList(intersections);
    }
    
    /**
     * Check if the given list contains certain line.
     * With a epsilon of 0.00001.
     * 
     * @param list The list.
     * @param line The line.
     * 
     * @return True if the list contains the line.
     */
    public static boolean containsLine(Collection<AbstractLine2D> list, AbstractLine2D line) {
        for (AbstractLine2D l : list) {
            if (l.almostEquals(line, 0.00001)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if the given list contains certain point.
     * With a epsilon of 0.00001.
     * 
     * @param list The list.
     * @param point The point.
     * 
     * @return True if the list contains the point.
     */
    public static boolean containsPoint(Collection<Point2D> list, Point2D point) {
        for (Point2D p : list) {
            if (p.almostEquals(point, 0.00001)) {
                return true;
            }
        }
        return false;
    }
    
}
