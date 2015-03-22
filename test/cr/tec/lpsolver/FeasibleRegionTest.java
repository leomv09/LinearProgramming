package cr.tec.lpsolver;

import static cr.tec.lpsolver.objects.matchers.ContainsLineMatcher.containsRay;
import static cr.tec.lpsolver.objects.matchers.ContainsPointMatcher.containsPoint;
import java.util.Collection;
import math.geom2d.Point2D;
import math.geom2d.line.AbstractLine2D;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author José Andrés García <jags9415@gmail.com>
 */
public class FeasibleRegionTest {
    
    private final FeasibleRegionFactory factory;
    
    public FeasibleRegionTest() {
        this.factory = new FeasibleRegionFactory(new String[] {"x", "y"});
    }
    
    @Test
    public void testEmptyArea() {
        Linear linear = new Linear();
        linear.add(-1, "x");
        linear.add(-3, "y");
        Constraint constraint = new Constraint(linear, Relationship.LEQ, 2);
        FeasibleRegion region = factory.createFeasibleRegion(constraint);
        
        assertThat("Intersection should be empty", region.isEmpty(), is(true));
        assertThat("Intersection should be unbounded", region.isBounded(), is(false));
    }
    
    @Test
    public void testFullQuadrantArea() {
        Linear linear = new Linear();
        linear.add(-1, "x");
        linear.add(-3, "y");
        Constraint constraint = new Constraint(linear, Relationship.GEQ, 2);
        
        FeasibleRegion region = factory.createFeasibleRegion(constraint);
        Collection<Point2D> vertex = region.getVertex();
        Collection<AbstractLine2D> lines = region.getLines();
        
        assertThat("Intersection should no be empty", region.isEmpty(), is(false));
        assertThat("Intersection should be unbounded", region.isBounded(), is(false));
        
        assertThat("Have vertex (0, 0)", vertex, containsPoint(0, 0));
        assertThat("Have 1 vertex", vertex.size(), is(1));
         
        assertThat("Have ray (0, 0) - 0°", lines, containsRay(0, 0, 0));
        assertThat("Have ray (0, 0) - 90°", lines, containsRay(0, 0, 90));
        assertThat("Have 2 lines", lines.size(), is(2));
    }
    
}
