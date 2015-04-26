/**
 * @author vladimir.tikhomirov
 */
public class AnalyzeServiceImpl implements AnalyzeService {


    @Override
    public Direction analyzePoint(Point from, Point to, Point pointToAnalyze) {
        final double result = analyzeDirection(from, to, pointToAnalyze);

        if (result > 0) {
            return Direction.RIGHT;
        } else if (result < 0) {
            return Direction.LEFT;
        }
        // case when result == 0
        return Direction.SAME;
    }

    @Override
    public boolean isSegmentIntersection(Point segmentFrom, Point segmentTo, Point anotherSegmentFrom, Point anotherSegmentTo) {

        final double result1 = analyzeDirection(segmentFrom, segmentTo, anotherSegmentFrom)
                * analyzeDirection(segmentFrom, segmentTo, anotherSegmentTo);

        final double result2 = analyzeDirection(anotherSegmentFrom, anotherSegmentTo, segmentFrom)
                * analyzeDirection(anotherSegmentFrom, anotherSegmentTo, segmentTo);

         return result1 <= 0 && result2 < 0;
    }

    private double analyzeDirection(Point from, Point to, Point pointToAnalyze) {
        return (to.getX() - from.getX()) * (pointToAnalyze.getY() - to.getY())
                - (to.getY() - from.getY()) * (pointToAnalyze.getX() - to.getX());
    }
}
