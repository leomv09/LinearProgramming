package cr.tec.lpsolver.objects.matchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import math.geom2d.line.AbstractLine2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.Ray2D;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * A Hamcrest Matcher to check if a list contains a line.
 * 
 * @author jose
 */
public class ContainsLineMatcher extends BaseMatcher<Collection<AbstractLine2D>> {
    
    private final static double EPSILON = 0.00001;
    private final List<AbstractLine2D> lines;

    private ContainsLineMatcher(AbstractLine2D... lines) {
        this.lines = new ArrayList<>();
        this.lines.addAll(Arrays.asList(lines));
    }

    @Override
    public boolean matches(Object obj) {
        Collection<AbstractLine2D> list = (Collection<AbstractLine2D>) obj;
        return list.stream().anyMatch((l1) -> (lines.stream().anyMatch((l2) -> (l1.almostEquals(l2, EPSILON)))));
    }

    @Override
    public void describeTo(Description d) {
        d.appendText("contains line " + lines.get(0));
    }
    
    @Factory
    public static <T> Matcher<Collection<AbstractLine2D>> containsLine(AbstractLine2D line) {
      return new ContainsLineMatcher(line);
    }
    
    @Factory
    public static <T> Matcher<Collection<AbstractLine2D>> containsSegment(double x1, double y1, double x2, double y2) {
      return new ContainsLineMatcher(new LineSegment2D(x1, y1, x2, y2), new LineSegment2D(x2, y2, x1, y1));
    }
    
    @Factory
    public static <T> Matcher<Collection<AbstractLine2D>> containsRay(double x, double y, double angle) {
      return new ContainsLineMatcher(new Ray2D(x, y, Math.toRadians(angle)));
    }
    
}
