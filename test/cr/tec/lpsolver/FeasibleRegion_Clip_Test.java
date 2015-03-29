package cr.tec.lpsolver;

import cr.tec.lpsolver.objects.FeasibleRegionObjects;
import static cr.tec.lpsolver.objects.matchers.ContainsPointMatcher.containsPoint;
import static cr.tec.lpsolver.objects.matchers.ContainsLineMatcher.containsSegment;
import java.util.Collection;
import math.geom2d.Point2D;
import math.geom2d.line.AbstractLine2D;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author José Andrés García <jags9415@gmail.com>
 */
public class FeasibleRegion_Clip_Test {
    
    private final FeasibleRegionObjects regions;
    
    public FeasibleRegion_Clip_Test() {
        this.regions = FeasibleRegionObjects.getInstance();
    }
    
    @Test
    public void testClip_GQE_INC_XY() {
        FeasibleRegion2D region = regions.GQE_INC_XY.clip(6, 8);
        Collection<Point2D> vertex = region.getVertex();
        Collection<AbstractLine2D> lines = region.getLines();
        
        assertThat("Have vertex (0, 0)", vertex, containsPoint(0, 0));
        assertThat("Have vertex (6, 6)", vertex, containsPoint(6, 6));
        assertThat("Have vertex (6, 8)", vertex, containsPoint(6, 8));
        assertThat("Have vertex (0, 8)", vertex, containsPoint(0, 8));
        assertThat("Have 4 vertex", vertex.size(), is(4));
        
        assertThat("Have segment (0, 0) - (0, 8)", lines, containsSegment(0, 0, 0, 8));
        assertThat("Have segment (0, 8) - (6, 8)", lines, containsSegment(0, 8, 6, 8));
        assertThat("Have segment (6, 8) - (6, 6)", lines, containsSegment(6, 8, 6, 6));
        assertThat("Have segment (6, 6) - (0, 0)", lines, containsSegment(6, 6, 0, 0));
        assertThat("Have 4 lines", lines.size(), is(4));
    }
    
}
