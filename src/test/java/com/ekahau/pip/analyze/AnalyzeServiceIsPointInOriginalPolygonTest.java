package com.ekahau.pip.analyze;

import com.ekahau.pip.common.Point;
import com.ekahau.pip.geometry.GeometryServiceTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Test class for {@link AnalyzeService#isPointInPolygon(Point)} method.
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
     * Create polygon based on the provided data.
     * @throws Exception -
     */
    @Before
    public void setUp() throws Exception {
        final List<Point> polygon = inputDataAnalyzer.readFile(POLYGON_POINTS);
        analyzeService.setPolygon(polygon);
    }

    /**
     * Tests {@link AnalyzeService#isPointInPolygon(Point)} when the points belong to the polygon.
     * @throws Exception -
     */
    @Test
    public void testPointsInsidePolygon() throws Exception {
        //SETUP SUT
        List<Point> pointsInsidePolygon = inputDataAnalyzer.readFile(INSIDE_POINTS);
        //EXERCISE & VERIFY
        for (Point pointToVerify : pointsInsidePolygon) {
            Assert.assertTrue(analyzeService.isPointInPolygon(pointToVerify));
        }
    }

    /**
     * Tests {@link AnalyzeService#isPointInPolygon(Point)} when the points do not belong to the polygon.
     * @throws Exception -
     */
    @Test
    public void testPointsOutsidePolygon() throws Exception {
        //SETUP SUT
        List<Point> pointsOutsideOfPolygon = inputDataAnalyzer.readFile(OUTSIDE_POINTS);

        //EXERCISE & VERIFY
        for (Point pointToVerify : pointsOutsideOfPolygon) {
            Assert.assertFalse(analyzeService.isPointInPolygon(pointToVerify));
        }
    }

    /**
     * Tests {@link AnalyzeService#preparePolygon(Point)} which sorts the polygon in a way
     * that main point stays on the first place in the list.
     * @throws Exception -
     */
    @Test
    public void testPreparePolygon() throws Exception {
        //EXERCISE
        analyzeService.preparePolygon(new Point(2, 2));

        //VERIFY
        Point mainPoint = analyzeService.getPolygon().get(0);
        Assert.assertEquals(2, mainPoint.getX(), GeometryServiceTest.DELTA);
        Assert.assertEquals(1, mainPoint.getY(), GeometryServiceTest.DELTA);
    }
}
