package com.ekahau.pip.analyze;

import com.ekahau.pip.common.Point;

import java.util.*;

/**
 * @author vladimir.tikhomirov
 */
public class AnalyzeServiceImpl implements AnalyzeService {

    public static final int STRAIGHT_ANGLE = 180;
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
            degrees += STRAIGHT_ANGLE * 2;
        }
        return degrees;
    }

    public Map<Point, Double> sortedAcuteAngleMap() {
        Point startPoint = polygon.get(0);
        double angleForStartPoint = angleBetweenTwoLines(startPoint, polygon.get(polygon.size() - 1),
                startPoint, polygon.get(1));
        double maxAngle = angleForStartPoint < STRAIGHT_ANGLE ? angleForStartPoint : 0;

        Map<Point, Double> pointToAngleMap = new HashMap<>();
        pointToAngleMap.put(startPoint, maxAngle);
        for (int i = 1; i < polygon.size(); i++) {
            final Point centerPoint = polygon.get(i);
            double angle;
            if (i + 1 == polygon.size()) {
                angle = angleBetweenTwoLines(centerPoint, polygon.get(i - 1), centerPoint, startPoint);
                if (maxAngle < angle && angle < STRAIGHT_ANGLE) {
                    pointToAngleMap.put(centerPoint, angle);
                    return pointToAngleMap;
                }
                break;
            }
            angle = angleBetweenTwoLines(centerPoint, polygon.get(i - 1), centerPoint, polygon.get(i + 1));
            if (maxAngle < angle && angle < STRAIGHT_ANGLE) {
                pointToAngleMap.put(centerPoint, angle);
                maxAngle = angle;
            }
        }
        return sortMapByValue(pointToAngleMap);
    }

    private Map<Point, Double> sortMapByValue(Map<Point, Double> pointToAngle) {
        List<Map.Entry<Point, Double>> list = new LinkedList<>(pointToAngle.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Point, Double>>() {
            @Override
            public int compare(Map.Entry<Point, Double> o1, Map.Entry<Point, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<Point, Double> sortedPointToDistanceMapByDistance = new LinkedHashMap<>();
        for (Map.Entry<Point, Double> entry : list) {
            sortedPointToDistanceMapByDistance.put(entry.getKey(), entry.getValue());
        }
        return sortedPointToDistanceMapByDistance;
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
    public void preparePolygon(Point pointToAnalyze) {
        Set<Point> vertexesWithAcuteAngles = sortedAcuteAngleMap().keySet();
        Point mainPoint = null;
        for (Point point : sortedDistanceMap(pointToAnalyze).keySet()) {
            if (vertexesWithAcuteAngles.contains(point)) {
                mainPoint = point;
                break;
            }
        }
        if (mainPoint != null) {
            setPolygon(reformatPolygon(mainPoint));
        }
    }

    /**
     * Reformats polygon list in a way that provided main point is the first element in the list, however,
     * the order of the points in the polygon is still the same.
     *
     * @param maintPoint main point
     * @return polygon with the main point in the first place
     */
    private List<Point> reformatPolygon(Point maintPoint) {
        int size = polygon.size();
        List<Point> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            if (maintPoint.equals(polygon.get(i))) {
                result = polygon.subList(i, size);
                result.addAll(polygon.subList(0, i));
                break;
            }
        }
        return result;
    }

    /**
     * Calculates the sorted map of distances from provided pointToAnalyze to all the polygon vertexes.
     *
     * @param pointToAnalyze point to which distances are calculated
     * @return sorted map of distances
     */
    private Map<Point, Double> sortedDistanceMap(Point pointToAnalyze) {
        Map<Point, Double> pointToDistance = new HashMap<>(polygon.size());
        for (Point point : polygon) {
            double distance = point.distance(pointToAnalyze);
            pointToDistance.put(point, distance);
        }

        return sortMapByValue(pointToDistance);
    }

    public void setPolygon(List<Point> polygon) {
        this.polygon = polygon;
    }

    public List<Point> getPolygon() {
        return polygon;
    }
}
