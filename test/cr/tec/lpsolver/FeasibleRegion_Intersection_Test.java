package cr.tec.lpsolver;

import cr.tec.lpsolver.objects.FeasibleRegionObjects;
import static cr.tec.lpsolver.objects.matchers.ContainsLineMatcher.containsRay;
import static cr.tec.lpsolver.objects.matchers.ContainsPointMatcher.containsPoint;
import static cr.tec.lpsolver.objects.matchers.ContainsLineMatcher.containsSegment;
import java.util.Collection;
import math.geom2d.Point2D;
import math.geom2d.line.AbstractLine2D;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * A class for testing the method {@link cr.tec.lpsolver.FeasibleRegion#intersection(cr.tec.lpsolver.FeasibleRegion)}.
 * 
 * @author jose
 */
public class FeasibleRegion_Intersection_Test {
    
    private final FeasibleRegionObjects regions;
    
    public FeasibleRegion_Intersection_Test() {
        this.regions = FeasibleRegionObjects.getInstance();
    }

    @Test
    public void testIntersection_GQEDECXY_GQEINCXY() {
        FeasibleRegion r1 = regions.GQE_DEC_XY;
        FeasibleRegion r2 = regions.GQE_INC_XY;
        
        FeasibleRegion intersection = r1.intersection(r2);
        Collection<Point2D> vertex = intersection.getVertex();
        Collection<AbstractLine2D> lines = intersection.getLines();

        assertThat("Intersection should be unbounded", intersection.isBounded(), is(false));
        
        assertThat("Have vertex (0, 6)", vertex, containsPoint(0, 6));
        assertThat("Have vertex (2.4, 2.4)", vertex, containsPoint(2.4, 2.4));
        assertThat("Have 2 vertex", vertex.size(), is(2));
        
        assertThat("Have segment (0, 6) - (2.4, 2.4)", lines, containsSegment(0, 6, 2.4, 2.4));
        assertThat("Have ray (0, 6) - 90°", lines, containsRay(0, 6, 90));
        assertThat("Have ray (2.4, 2.4) - 45°", lines, containsRay(2.4, 2.4, 45));
        assertThat("Have 3 lines", lines.size(), is(3));
    }
    
    @Test
    public void testIntersection_LQEDECXY_LQEINCXY() {
        FeasibleRegion r1 = regions.LQE_DEC_XY;
        FeasibleRegion r2 = regions.LQE_INC_XY;
        
        FeasibleRegion intersection = r1.intersection(r2);
        Collection<Point2D> vertex = intersection.getVertex();
        Collection<AbstractLine2D> lines = intersection.getLines();
        
        assertThat("Intersection should be bounded", intersection.isBounded(), is(true));
        
        assertThat("Have vertex (0, 0)", vertex, containsPoint(0, 0));
        assertThat("Have vertex (2.4, 2.4)", vertex, containsPoint(2.4, 2.4));
        assertThat("Have vertex (4, 0)", vertex, containsPoint(4, 0));
        assertThat("Have 3 vertex", vertex.size(), is(3));
        
        assertThat("Have segment (0, 0) - (2.4, 2.4)", lines, containsSegment(0, 0, 2.4, 2.4));
        assertThat("Have segment (2.4, 2.4) - (4, 0)", lines, containsSegment(2.4, 2.4, 4, 0));
        assertThat("Have segment (4, 0) - (0, 0)", lines, containsSegment(4, 0, 0, 0));
        assertThat("Have 3 lines", lines.size(), is(3));
    }
    
}
