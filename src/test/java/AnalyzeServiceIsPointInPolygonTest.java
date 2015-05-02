import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Test class for {@link AnalyzeService#isPointInPolygon(Point)}.
 *
 * @author vladimir.tikhomirov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class AnalyzeServiceIsPointInPolygonTest {

    public static final String POLYGON_POINTS = "src/test/resources/polygonTestPoints.txt";
    public static final String OUTSIDE_POINTS = "src/test/resources/outsidePoints.txt";

    @Autowired
    AnalyzeServiceImpl analyzeService;
    @Autowired
    InputDataAnalyzer inputDataAnalyzer;

    /**
     * Create simple convex pentagon.
     * @throws Exception -
     */
    @Before
    public void setUp() throws Exception {
        final List<Point> polygon = inputDataAnalyzer.readFile(POLYGON_POINTS);
        analyzeService.setPolygon(polygon);
    }

    /**
     * Tests {@link AnalyzeService#isPointInPolygon(Point)} when the points do not even belong to the angle
     * of the first polygon point and it's neighbours.
     * @throws Exception -
     */
    @Test
    public void testIsPointInPolygonWhenItIsNot() throws Exception {
        //SETUP SUT
        List<Point> pointsOutsideOfPolygon = inputDataAnalyzer.readFile(OUTSIDE_POINTS);

        //EXERCISE & VERIFY
        for (Point pointToVerify : pointsOutsideOfPolygon) {
            Assert.assertFalse(analyzeService.isPointInPolygon2(pointToVerify));
        }
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
        final boolean result = analyzeService.isPointInPolygon2(new Point(2, 6));
        //VERIFY
        Assert.assertFalse(result);
    }
}
