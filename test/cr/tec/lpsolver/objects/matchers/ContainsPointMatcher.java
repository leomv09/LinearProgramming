package cr.tec.lpsolver.objects.matchers;

import java.util.Collection;
import math.geom2d.Point2D;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * A Hamcrest Matcher to check if a list contains a point.
 * 
 * @author jose
 */
public class ContainsPointMatcher extends BaseMatcher<Collection<Point2D>> {

    private final static double EPSILON = 0.00001;
    private final Point2D point;

    private ContainsPointMatcher(Point2D point) {
        this.point = point;
    }
    
    @Override
    public boolean matches(Object obj) {
        Collection<Point2D> list = (Collection<Point2D>) obj;
        return list.stream().anyMatch((p) -> (point.almostEquals(p, EPSILON)));
    }

    @Override
    public void describeTo(Description d) {
        d.appendText("contains point " + point);
    }
    
    @Factory
    public static <T> Matcher<Collection<Point2D>> containsPoint(Point2D point) {
      return new ContainsPointMatcher(point);
    }
    
    @Factory
    public static <T> Matcher<Collection<Point2D>> containsPoint(double x, double y) {
      return new ContainsPointMatcher(new Point2D(x, y));
    }
    
}
