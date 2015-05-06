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
public class AnalyzeServiceIsPointInOriginalPolygonTest {

    public static final String POLYGON_POINTS = "src/test/resources/original/originalPolygonTest.txt";
    public static final String OUTSIDE_POINTS = "src/test/resources/original/outsidePoints.txt";
    public static final String INSIDE_POINTS = "src/test/resources/original/insidePoints.txt";

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
        //EXERCISE
        final boolean result = analyzeService.isPointInPolygon(new Point(1, 1));
        //VERIFY
        Assert.assertFalse(result);
    }

    /**
     * Tests {@link AnalyzeService#isPointInPolygon(Point)} when the point belongs to the polygon.
     * @throws Exception -
     */
    @Test
    public void testIsPointInPolygonWhenItIs() throws Exception {
        //SETUP SUT
        List<Point> pointsInsidePolygon = inputDataAnalyzer.readFile(INSIDE_POINTS);
        //EXERCISE & VERIFY
        for (Point pointToVerify : pointsInsidePolygon) {
            Assert.assertTrue(analyzeService.isPointInPolygon(pointToVerify));
        }
    }

    /**
     * Tests {@link AnalyzeService#isPointInPolygon(Point)} when the point doesn't belong to the polygon,
     * but it belongs to the angle of the first polygon point and it's neighbours.
     * @throws Exception -
     */
    @Test
    public void testIsPointInPolygonWhenItIsNot2() throws Exception {
        //SETUP SUT
        List<Point> pointsOutsideOfPolygon = inputDataAnalyzer.readFile(OUTSIDE_POINTS);

        //EXERCISE & VERIFY
        for (Point pointToVerify : pointsOutsideOfPolygon) {
            Assert.assertFalse(analyzeService.isPointInPolygon(pointToVerify));
        }
    }

//    @Test
//    public void testFindLargestAcuteAngle() throws Exception {
//        //EXERCISE
//        final Point vertexWithTheLargestAcuteAngle = analyzeService.sortedAcuteAngleMap();
//
//        //VERIFY
//        Assert.assertEquals(1, vertexWithTheLargestAcuteAngle.getX(), AnalyzeServiceTest.DELTA);
//        Assert.assertEquals(5, vertexWithTheLargestAcuteAngle.getY(), AnalyzeServiceTest.DELTA);
//    }

//    @Test
//    public void testPreparePolygon() throws Exception {
//        //EXERCISE
//        analyzeService.preparePolygon(new Point(2, 2));
//
//        //VERIFY
//        Point mainPoint = analyzeService.getPolygon().get(0);
//        Assert.assertEquals(2, mainPoint.getX(), AnalyzeServiceTest.DELTA);
//        Assert.assertEquals(1, mainPoint.getY(), AnalyzeServiceTest.DELTA);
//    }
}
