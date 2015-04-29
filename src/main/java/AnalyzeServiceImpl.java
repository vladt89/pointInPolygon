import java.util.LinkedList;
import java.util.List;

/**
 * @author vladimir.tikhomirov
 */
public class AnalyzeServiceImpl implements AnalyzeService {

    List<Point> polygon = new LinkedList<>();

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

    @Override
    public boolean isPointInPolygon2(Point pointToAnalyze) {
        final Point mainPoint = polygon.get(0);
        final int lastVertex = polygon.size() - 1;
        //first we check if the provided point is included in the angle which is made by mainPoint and neighbour points
        if (analyzePoint(mainPoint, polygon.get(1), pointToAnalyze) == Direction.LEFT
                || analyzePoint(mainPoint, polygon.get(lastVertex), pointToAnalyze) == Direction.RIGHT) {
            return false;
        }

        int count = 0;
        for (int i = 0; i < polygon.size(); i++) {
            if (i + 1 == polygon.size()) {
                if (isSegmentIntersection(mainPoint, pointToAnalyze, polygon.get(i), polygon.get(0))) {
                    count++;
                }
                break;
            }

            if (isSegmentIntersection(mainPoint, pointToAnalyze, polygon.get(i), polygon.get(i + 1))) {
                count++;
            }
        }
        return count % 2 == 0;
    }

    @Override
    public boolean isPointInPolygon(Point pointToAnalyze) {

//        TODO fix vertex problem
//        for (Point point : polygon) {
//            if (point.equals(pointToAnalyze)) {
//                return true;
//            }
//        }
//        if (polygon.contains(pointToAnalyze)) {
//            return true;
//        }

        //the point from which we start to determine if provided point belongs to the polygon or not
        final Point mainPoint = polygon.get(0);
        final int lastVertex = polygon.size() - 1;

        //first we check if the provided point is included in the angle which is made by mainPoint and neighbour points
        if (analyzePoint(mainPoint, polygon.get(1), pointToAnalyze) == Direction.LEFT
                || analyzePoint(mainPoint, polygon.get(lastVertex), pointToAnalyze) == Direction.RIGHT) {
            return false;
        }

        int p = 1;
        int r = lastVertex;
        while (r - p > 1) {
            //find middle vertex
            int q = (p + r) / 2;
            if (analyzePoint(mainPoint, polygon.get(q), pointToAnalyze) == Direction.LEFT) {
                r = q;
            } else {
                p = q;
            }
        }

        //if segments are not intersected then the point is inside polygon
        return !isSegmentIntersection(mainPoint, pointToAnalyze, polygon.get(p), polygon.get(r));
    }

    public void setPolygon(List<Point> polygon) {
        this.polygon = polygon;
    }
}
