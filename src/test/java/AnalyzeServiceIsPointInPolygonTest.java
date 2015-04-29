import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for {@link AnalyzeService#isPointInPolygon(Point)}.
 *
 * @author vladimir.tikhomirov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class AnalyzeServiceIsPointInPolygonTest {

    @Autowired
    AnalyzeServiceImpl analyzeService;

    /**
     * Create simple convex pentagon.
     * @throws Exception -
     */
    @Before
    public void setUp() throws Exception {
        List<Point> polygon = new ArrayList<>(5);
        polygon.add(new Point(2, 1));
        polygon.add(new Point(5, 2));
        polygon.add(new Point(6, 4));
        polygon.add(new Point(4, 6));
        polygon.add(new Point(1, 4));
        analyzeService.setPolygon(polygon);
    }

    /**
     * Tests {@link AnalyzeService#isPointInPolygon(Point)} when the point doesn't even belong to the angle
     * of the first polygon point and it's neighbours.
     * @throws Exception -
     */
    @Test
    public void testIsPointInPolygonWhenItIsNot() throws Exception {
        //EXERCISE
        final boolean result = analyzeService.isPointInPolygon2(new Point(4, 1));
        //VERIFY
        Assert.assertFalse(result);
    }

    /**
     * Tests {@link AnalyzeService#isPointInPolygon(Point)} when the point belongs to the polygon.
     * @throws Exception -
     */
    @Test
    public void testIsPointInPolygonWhenItIs() throws Exception {
        //EXERCISE
        final boolean result = analyzeService.isPointInPolygon2(new Point(3, 2));
        //VERIFY
        Assert.assertTrue(result);
    }

    /**
     * Tests {@link AnalyzeService#isPointInPolygon(Point)} when the point doesn't belong to the polygon,
     * but it belongs to the angle of the first polygon point and it's neighbours.
     * @throws Exception -
     */
    @Test
    public void testIsPointInPolygonWhenItIsNot2() throws Exception {
        //EXERCISE
        //(6,6) should work
        final boolean result = analyzeService.isPointInPolygon2(new Point(2, 6));
        //VERIFY
        Assert.assertFalse(result);
    }
}
