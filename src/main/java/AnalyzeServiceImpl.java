import java.util.*;

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
    public double angleBetweenTwoLines(Point lineStart, Point lineEnd, Point anotherLineStart, Point anotherLineEnd) {

        double angle1 = Math.atan2(lineStart.getY() - lineEnd.getY(), lineStart.getX() - lineEnd.getX());

        double angle2 = Math.atan2(anotherLineStart.getY() - anotherLineEnd.getY(), anotherLineStart.getX() - anotherLineEnd.getX());

        double result = angle1 - angle2;

        double degrees = Math.toDegrees(result);

        if (degrees < 0) {
            degrees += 360;
        }
        return degrees;
    }

    public int findVertexWithTheLargestAngle() {
        int vertexIndex = 0;
        double maxAngle = angleBetweenTwoLines(polygon.get(0), polygon.get(polygon.size() - 1),
                polygon.get(0), polygon.get(1));

        for (int i = 1; i < polygon.size(); i++) {
            final Point centerPoint = polygon.get(i);
            if (i + 1 == polygon.size()) {
                if (angleBetweenTwoLines(centerPoint, polygon.get(i - 1), centerPoint, polygon.get(0)) > maxAngle) {
                    return i;
                }
                break;
            }
            double angle = angleBetweenTwoLines(centerPoint, polygon.get(i - 1), centerPoint, polygon.get(i + 1));
            if (angle > maxAngle) {
                maxAngle = angle;
                vertexIndex = i;
            }
        }
        return vertexIndex;
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
    public boolean isPointInPolygon(Point pointToAnalyze) {
        preparePolygon(pointToAnalyze);

        if (polygon.contains(pointToAnalyze)) {
            return true;
        }

        final Point mainPoint = polygon.get(0);
        int length = polygon.size();
        final int lastVertex = length - 1;
        //first we check if the provided point is included in the angle which is made by mainPoint and neighbour points
        if (analyzePoint(mainPoint, polygon.get(1), pointToAnalyze) == Direction.LEFT
                || analyzePoint(mainPoint, polygon.get(lastVertex), pointToAnalyze) == Direction.RIGHT) {
            return false;
        }

        int count = 0;
        for (int i = 0; i < length; i++) {
            if (i + 1 == length) {
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
    public boolean isPointInConvexPolygon(Point pointToAnalyze) {

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

    @Override
    public void preparePolygon(Point point) {
        Point closestVertex = findClosestVertex(point);
        List<Point> points = reformatPolygon(closestVertex);
        setPolygon(points);
    }

    private List<Point> reformatPolygon(Point closestVertex) {
        List<Point> result = new ArrayList<>(polygon.size());
        for (int i = 0; i < polygon.size(); i++) {
            if (closestVertex.equals(polygon.get(i))) {
                result = polygon.subList(i, polygon.size());
                result.addAll(polygon.subList(0, i));
                break;
            }
        }
        return result;
    }

    private Point findClosestVertex(Point pointToAnalyze) {
        Map<Double, Point> distanceToPoint = new HashMap<>();
        for (Point point : polygon) {
            double distance = point.distance(pointToAnalyze);
            distanceToPoint.put(distance, point);
        }
        Double min = Collections.min(distanceToPoint.keySet());
        return distanceToPoint.get(min);
    }

    public void setPolygon(List<Point> polygon) {
        this.polygon = polygon;
    }

    public List<Point> getPolygon() {
        return polygon;
    }
}
