import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Class test for {@link AnalyzeService}.
 *
 * @author vladimir.tikhomirov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class AnalyzeServiceTest {

    @Autowired
    AnalyzeServiceImpl analyzeService;

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
     * Tests {@link AnalyzeService#analyzePoint(Point, Point, Point)} when the pointToAnalyze
     * is in the left side from the direction of the vector AB (A = from, B = to).
     * @throws Exception -
     */
    @Test
    public void testAnalyzePointWhenPointToAnalyzeIsInTheLeft() throws Exception {
        //SETUP SUT
        Point pointToAnalyze = new Point(3, 1);

        //EXERCISE
        final Direction direction = analyzeService.analyzePoint(from, to, pointToAnalyze);

        //VERIFY
        Assert.assertEquals(Direction.LEFT, direction);
    }

    /**
     * Tests {@link AnalyzeService#analyzePoint(Point, Point, Point)} when the pointToAnalyze
     * is in the right side from the direction of the vector AB (A = from, B = to).
     * @throws Exception -
     */
    @Test
    public void testAnalyzePointWhenPointToAnalyzeIsInTheRight() throws Exception {
        //SETUP SUT
        Point pointToAnalyze = new Point(1, 4);

        //EXERCISE
        final Direction direction = analyzeService.analyzePoint(from, to, pointToAnalyze);

        //VERIFY
        Assert.assertEquals(Direction.RIGHT, direction);
    }

    /**
     * Tests {@link AnalyzeService#analyzePoint(Point, Point, Point)} when the pointToAnalyze
     * is in the right side from the direction of the vector AB (A = from, B = to).
     * @throws Exception -
     */
    @Test
    public void testAnalyzePointWhenPointToAnalyzeBelongsToTheVector() throws Exception {
        //SETUP SUT
        Point pointToAnalyze = new Point(3, 4);

        //EXERCISE
        final Direction direction = analyzeService.analyzePoint(from, to, pointToAnalyze);

        //VERIFY
        Assert.assertEquals(Direction.SAME, direction);
    }

    /**
     * Tests {@link AnalyzeService#isSegmentIntersection(Point, Point, Point, Point)} when two segments
     * AB and CD (C = segmentToAnalyzeFrom, D = segmentToAnalyzeTo) are intersected.
     * @throws Exception -
     */
    @Test
    public void testIsSegmentIntersectionWhenIntersectionHappened() throws Exception {
        //SETUP SUT
        Point segmentToAnalyzeFrom = new Point(3, 1);
        Point segmentToAnalyzeTo = new Point(1, 4);

        //EXERCISE
        final boolean result = analyzeService.isSegmentIntersection(from, to, segmentToAnalyzeFrom, segmentToAnalyzeTo);

        //VERIFY
        Assert.assertTrue(result);
    }

    /**
     * Tests {@link AnalyzeService#isSegmentIntersection(Point, Point, Point, Point)} when two segments
     * AB and CD (C = segmentToAnalyzeFrom, D = segmentToAnalyzeTo) are not intersected.
     * @throws Exception -
     */
    @Test
    public void testIsSegmentIntersectionWhenIntersectionNotHappened() throws Exception {
        //SETUP SUT
        Point segmentToAnalyzeFrom = new Point(3, 1);
        Point segmentToAnalyzeTo = new Point(4, 3);

        //EXERCISE
        final boolean result = analyzeService.isSegmentIntersection(from, to, segmentToAnalyzeFrom, segmentToAnalyzeTo);

        //VERIFY
        Assert.assertFalse(result);
    }
}