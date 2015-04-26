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
     */
    @Before
    public void setUp() throws Exception {
        from = new Point(2, 1); // A
        to = new Point(3, 4); // B
    }

    /**
     * Tests {@link AnalyzeService#analyzePoint(Point, Point, Point)} when the pointToAnalyze
     * is in the left side from the direction of the vector AB (A = from, B = to).
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
}