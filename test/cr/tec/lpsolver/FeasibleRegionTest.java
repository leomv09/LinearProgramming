package cr.tec.lpsolver;

import static cr.tec.lpsolver.objects.matchers.ContainsPointMatcher.containsPoint;
import static cr.tec.lpsolver.objects.matchers.ContainsLineMatcher.containsRay;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import math.geom2d.line.AbstractLine2D;
import java.util.Collection;
import math.geom2d.Point2D;
import org.junit.Test;

/**
 *
 * @author jose
 */
public class FeasibleRegionTest {
    
    private final FeasibleRegion2DFactory factory;
    
    public FeasibleRegionTest() {
        this.factory = new FeasibleRegion2DFactory(new String[] {"x", "y"});
    }
    
    @Test
    public void testEmptyArea() {
        Linear linear = new Linear();
        linear.add(-1, "x");
        linear.add(-3, "y");
        Constraint constraint = new Constraint(linear, Relationship.LEQ, 2);
        FeasibleRegion2D region = factory.createFeasibleRegion(constraint);
        
        assertTrue("Intersection should be empty", region.isEmpty());
        assertFalse("Intersection should be unbounded", region.isBounded());
    }
    
    @Test
    public void testFullQuadrantArea() {
        Linear linear = new Linear();
        linear.add(-1, "x");
        linear.add(-3, "y");
        Constraint constraint = new Constraint(linear, Relationship.GEQ, 2);
        
        FeasibleRegion2D region = factory.createFeasibleRegion(constraint);
        Collection<Point2D> vertex = region.getVertex();
        Collection<AbstractLine2D> lines = region.getLines();
        
        assertFalse("Intersection should no be empty", region.isEmpty());
        assertFalse("Intersection should be unbounded", region.isBounded());
        
        assertThat("Have vertex (0, 0)", vertex, containsPoint(0, 0));
        assertThat("Have 1 vertex", vertex, hasSize(1));
         
        assertThat("Have ray (0, 0) - 0°", lines, containsRay(0, 0, 0));
        assertThat("Have ray (0, 0) - 90°", lines, containsRay(0, 0, 90));
        assertThat("Have 2 lines", lines, hasSize(2));
    }
    
}
