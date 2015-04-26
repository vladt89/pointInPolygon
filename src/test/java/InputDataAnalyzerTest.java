import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Test class for {@link InputDataAnalyzer}.
 *
 * @author vladimir.tikhomirov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class InputDataAnalyzerTest {

    @Autowired
    InputDataAnalyzer inputDataAnalyzer;

    private static final double DELTA = 0.001;

    /**
     * Tests {@link InputDataAnalyzer#readFile(String)}. Happy path.
     * @throws Exception -
     */
    @Test
    public void testReadFile() throws Exception {

        //EXERCISE
        final List<Point> pointList = inputDataAnalyzer.readFile("src/test/resources/testPolygon.txt");

        //VERIFY
        Assert.assertEquals(5, pointList.get(0).getX(), DELTA);
        Assert.assertEquals(8, pointList.get(2).getX(), DELTA);
        Assert.assertEquals(1, pointList.get(pointList.size() - 1).getY(), DELTA);
        Assert.assertEquals(1, pointList.get(pointList.size() - 2).getX(), DELTA);
    }
}