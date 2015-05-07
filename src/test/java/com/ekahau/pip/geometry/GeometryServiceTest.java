package com.ekahau.pip.geometry;

import com.ekahau.pip.analyze.Direction;
import com.ekahau.pip.common.Point;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Class test for {@link com.ekahau.pip.geometry.GeometryService}.
 *
 * @author vladimir.tikhomirov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class GeometryServiceTest {

    public static final double DELTA = 0.01;
    @Autowired
    GeometryServiceImpl geometryService;

    Point from;
    Point to;

    /**
     * Initialize points which will be analyzed as a vector AB (A = from, B = to).
     * @throws Exception -
     */
    @Before
    public void setUp() throws Exception {
        from = new Point(2, 1); // A
        to = new Point(3, 4); // B
    }

    /**
     * Tests {@link GeometryServiceImpl#analyzePoint(Point, Point, Point)} when the pointToAnalyze
     * is in the left side from the direction of the vector AB (A = from, B = to).
     * @throws Exception -
     */
    @Test
    public void testAnalyzePointWhenPointToAnalyzeIsInTheLeft() throws Exception {
        //SETUP SUT
        Point pointToAnalyze = new Point(3, 1);

        //EXERCISE
        final Direction direction = geometryService.analyzePoint(from, to, pointToAnalyze);

        //VERIFY
        Assert.assertEquals(Direction.LEFT, direction);
    }

    /**
     * Tests {@link GeometryServiceImpl#analyzePoint(Point, Point, Point)} when the pointToAnalyze
     * is in the right side from the direction of the vector AB (A = from, B = to).
     * @throws Exception -
     */
    @Test
    public void testAnalyzePointWhenPointToAnalyzeIsInTheRight() throws Exception {
        //SETUP SUT
        Point pointToAnalyze = new Point(1, 4);

        //EXERCISE
        final Direction direction = geometryService.analyzePoint(from, to, pointToAnalyze);

        //VERIFY
        Assert.assertEquals(Direction.RIGHT, direction);
    }

    /**
     * Tests {@link GeometryServiceImpl#analyzePoint(Point, Point, Point)} when the pointToAnalyze
     * is in the right side from the direction of the vector AB (A = from, B = to).
     * @throws Exception -
     */
    @Test
    public void testAnalyzePointWhenPointToAnalyzeBelongsToTheVector() throws Exception {
        //SETUP SUT
        Point pointToAnalyze = new Point(3, 4);

        //EXERCISE
        final Direction direction = geometryService.analyzePoint(from, to, pointToAnalyze);

        //VERIFY
        Assert.assertEquals(Direction.SAME, direction);
    }

    /**
     * Tests {@link GeometryServiceImpl#isSegmentIntersection(Point, Point, Point, Point)} when two segments
     * AB and CD (C = segmentToAnalyzeFrom, D = segmentToAnalyzeTo) are intersected.
     * @throws Exception -
     */
    @Test
    public void testIsSegmentIntersectionWhenIntersectionHappened() throws Exception {
        //SETUP SUT
        Point segmentToAnalyzeFrom = new Point(3, 1);
        Point segmentToAnalyzeTo = new Point(1, 4);

        //EXERCISE
        final boolean result = geometryService.isSegmentIntersection(from, to, segmentToAnalyzeFrom, segmentToAnalyzeTo);

        //VERIFY
        Assert.assertTrue(result);
    }

    /**
     * Tests {@link GeometryServiceImpl#isSegmentIntersection(Point, Point, Point, Point)} when two segments
     * AB and CD (C = segmentToAnalyzeFrom, D = segmentToAnalyzeTo) are not intersected.
     * @throws Exception -
     */
    @Test
    public void testIsSegmentIntersectionWhenIntersectionNotHappened() throws Exception {
        //SETUP SUT
        Point segmentToAnalyzeFrom = new Point(3, 1);
        Point segmentToAnalyzeTo = new Point(4, 3);

        //EXERCISE
        final boolean result = geometryService.isSegmentIntersection(from, to, segmentToAnalyzeFrom, segmentToAnalyzeTo);

        //VERIFY
        Assert.assertFalse(result);
    }

    @Test
    public void testAngleBetweenTwoLines() throws Exception {
        Point lineStart = new Point(1, 1);
        Point lineEnd = new Point(4, 1);

        Point anotherLinesStart = new Point(1, 2);
        Point anotherLineEnd = new Point(1, 4);

        //EXERCISE
        double angle = geometryService.angleBetweenTwoLines(anotherLinesStart, anotherLineEnd, lineStart, lineEnd);

        //VERIFY
        Assert.assertEquals(90, angle, DELTA);

    }
}