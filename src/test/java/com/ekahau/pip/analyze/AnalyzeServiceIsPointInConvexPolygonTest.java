package com.ekahau.pip.analyze;

import com.ekahau.pip.common.Point;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Test class for {@link AnalyzeService#isPointInConvexPolygon(Point)} method.
 *
 * @author vladimir.tikhomirov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class AnalyzeServiceIsPointInConvexPolygonTest {

    private static final String POLYGON_POINTS = "src/test/resources/convex/convexPolygonTest.txt";
    private static final String OUTSIDE_POINTS = "src/test/resources/convex/outsidePoints.txt";
    private static final String INSIDE_POINTS = "src/test/resources/convex/insidePoints.txt";

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
     * Tests {@link AnalyzeService#isPointInConvexPolygon(Point)} when the points do not even belong to the polygon.
     * @throws Exception -
     */
    @Test
    public void testIsPointInPolygonWhenItIsNot() throws Exception {
        //SETUP SUT
        List<Point> pointsOutsideOfPolygon = inputDataAnalyzer.readFile(OUTSIDE_POINTS);

        //EXERCISE & VERIFY
        for (Point pointToVerify : pointsOutsideOfPolygon) {
            Assert.assertFalse(analyzeService.isPointInConvexPolygon(pointToVerify));
        }
    }

    /**
     * Tests {@link AnalyzeService#isPointInConvexPolygon(Point)} when the points belong to the polygon.
     * @throws Exception -
     */
    @Test
    public void testIsPointInPolygonWhenItIs() throws Exception {
        //SETUP SUT
        List<Point> pointsOutsideOfPolygon = inputDataAnalyzer.readFile(INSIDE_POINTS);

        //EXERCISE & VERIFY
        for (Point pointToVerify : pointsOutsideOfPolygon) {
            Assert.assertTrue(analyzeService.isPointInConvexPolygon(pointToVerify));
        }
    }
}
