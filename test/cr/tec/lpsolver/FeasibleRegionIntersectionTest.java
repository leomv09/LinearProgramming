package cr.tec.lpsolver;

import static cr.tec.lpsolver.objects.matchers.ContainsLineMatcher.containsSegment;
import static cr.tec.lpsolver.objects.matchers.ContainsPointMatcher.containsPoint;
import static cr.tec.lpsolver.objects.matchers.ContainsLineMatcher.containsRay;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import cr.tec.lpsolver.objects.FeasibleRegionObjects;
import math.geom2d.line.AbstractLine2D;
import java.util.Collection;
import math.geom2d.Point2D;
import org.junit.Test;

/**
 * A class for testing the method {@link cr.tec.lpsolver.FeasibleRegion#intersection(cr.tec.lpsolver.FeasibleRegion)}.
 * 
 * @author jose
 */
public class FeasibleRegionIntersectionTest {
    
    private final FeasibleRegionObjects regions;
    
    public FeasibleRegionIntersectionTest() {
        this.regions = FeasibleRegionObjects.getInstance();
    }

    @Test
    public void testIntersection_GQEDECXY_GQEINCXY() {
        FeasibleRegion2D r1 = regions.GQE_DEC_XY;
        FeasibleRegion2D r2 = regions.GQE_INC_XY;
        
        FeasibleRegion2D intersection = r1.intersection(r2);
        Collection<Point2D> vertex = intersection.getVertex();
        Collection<AbstractLine2D> lines = intersection.getLines();

        assertFalse("Intersection should be unbounded", intersection.isBounded());
        
        assertThat("Have vertex (0, 6)", vertex, containsPoint(0, 6));
        assertThat("Have vertex (2.4, 2.4)", vertex, containsPoint(2.4, 2.4));
        assertThat("Have 2 vertex", vertex, hasSize(2));
        
        assertThat("Have segment (0, 6) - (2.4, 2.4)", lines, containsSegment(0, 6, 2.4, 2.4));
        assertThat("Have ray (0, 6) - 90°", lines, containsRay(0, 6, 90));
        assertThat("Have ray (2.4, 2.4) - 45°", lines, containsRay(2.4, 2.4, 45));
        assertThat("Have 3 lines", lines, hasSize(3));
    }
    
    @Test
    public void testIntersection_LQEDECXY_LQEINCXY() {
        FeasibleRegion2D r1 = regions.LQE_DEC_XY;
        FeasibleRegion2D r2 = regions.LQE_INC_XY;
        
        FeasibleRegion2D intersection = r1.intersection(r2);
        Collection<Point2D> vertex = intersection.getVertex();
        Collection<AbstractLine2D> lines = intersection.getLines();
        
        assertTrue("Intersection should be bounded", intersection.isBounded());
        
        assertThat("Have vertex (0, 0)", vertex, containsPoint(0, 0));
        assertThat("Have vertex (2.4, 2.4)", vertex, containsPoint(2.4, 2.4));
        assertThat("Have vertex (4, 0)", vertex, containsPoint(4, 0));
        assertThat("Have 3 vertex", vertex, hasSize(3));
        
        assertThat("Have segment (0, 0) - (2.4, 2.4)", lines, containsSegment(0, 0, 2.4, 2.4));
        assertThat("Have segment (2.4, 2.4) - (4, 0)", lines, containsSegment(2.4, 2.4, 4, 0));
        assertThat("Have segment (4, 0) - (0, 0)", lines, containsSegment(4, 0, 0, 0));
        assertThat("Have 3 lines", lines, hasSize(3));
    }
    
    @Test
    public void testIntersection_LQEDECXY_GQEINCY() {
        FeasibleRegion2D r1 = regions.LQE_INC_XY;
        FeasibleRegion2D r2 = regions.GQE_INC_Y;
        
        FeasibleRegion2D intersection = r1.intersection(r2);
        Collection<Point2D> vertex = intersection.getVertex();
        Collection<AbstractLine2D> lines = intersection.getLines();
        
        assertFalse("Intersection should be unbounded", intersection.isBounded());
        assertTrue("Intersection should be empty", intersection.isEmpty());
        assertThat("Have no vertex", vertex, empty());
        assertThat("Have no lines", lines, empty());
    }
    
    @Test
    public void testIntersection_GQEDECXY_GQEINCY() {
        FeasibleRegion2D r1 = regions.GQE_INC_XY;
        FeasibleRegion2D r2 = regions.GQE_INC_Y;
        
        FeasibleRegion2D intersection = r1.intersection(r2);
        Collection<Point2D> vertex = intersection.getVertex();
        Collection<AbstractLine2D> lines = intersection.getLines();
        
        assertFalse("Intersection should be unbounded", intersection.isBounded());
        assertFalse("Intersection should not be empty", intersection.isEmpty());
        
        assertThat("Have vertex (0, 3)", vertex, containsPoint(0, 3));
        assertThat("Have 1 vertex", vertex, hasSize(1));
        
        assertThat("Have ray (0, 3) - 90°", lines, containsRay(0, 3, 90));
        assertThat("Have ray (0, 3) - 63.434948822922°", lines, containsRay(0, 3, 63.434948822922));
        assertThat("Have 2 lines", lines, hasSize(2));
    }
    
}
