/**
 * @author vladimir.tikhomirov
 */
public class AnalyzeServiceImpl implements AnalyzeService {


    @Override
    public Direction analyzePoint(Point from, Point to, Point pointToAnalyze) {
        final double result = (to.getX() - from.getX()) * (pointToAnalyze.getY() - to.getY())
                - (to.getY() - from.getY()) * (pointToAnalyze.getX() - to.getX());

        if (result > 0) {
            return Direction.RIGHT;
        } else if (result < 0) {
            return Direction.LEFT;
        }
        // case when result == 0
        return Direction.SAME;
    }
}
