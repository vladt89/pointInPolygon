package com.ekahau.pip.analyze;

import com.ekahau.pip.common.Point;
import com.ekahau.pip.geometry.GeometryService;
import com.ekahau.pip.geometry.GeometryServiceImpl;

import java.util.*;

/**
 * Class is responsible for polygon analysis and point localization.
 *
 * @author vladimir.tikhomirov
 */
public class AnalyzeServiceImpl implements AnalyzeService {

    private GeometryService geometryService;

    List<Point> polygon = new LinkedList<>();

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
        if (geometryService.analyzePoint(mainPoint, polygon.get(1), pointToAnalyze) == Direction.LEFT
                || geometryService.analyzePoint(mainPoint, polygon.get(lastVertex), pointToAnalyze) == Direction.RIGHT) {
            return false;
        }

        int count = 0;
        for (int i = 0; i < length; i++) {
            if (i + 1 == length) {
                if (geometryService.isSegmentIntersection(mainPoint, pointToAnalyze, polygon.get(i), polygon.get(0))) {
                    count++;
                }
                break;
            }

            if (geometryService.isSegmentIntersection(mainPoint, pointToAnalyze, polygon.get(i), polygon.get(i + 1))) {
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
        if (geometryService.analyzePoint(mainPoint, polygon.get(1), pointToAnalyze) == Direction.LEFT
                || geometryService.analyzePoint(mainPoint, polygon.get(lastVertex), pointToAnalyze) == Direction.RIGHT) {
            return false;
        }

        int p = 1;
        int r = lastVertex;
        while (r - p > 1) {
            //find middle vertex
            int q = (p + r) / 2;
            if (geometryService.analyzePoint(mainPoint, polygon.get(q), pointToAnalyze) == Direction.LEFT) {
                r = q;
            } else {
                p = q;
            }
        }

        //if segments are not intersected then the point is inside polygon
        return !geometryService.isSegmentIntersection(mainPoint, pointToAnalyze, polygon.get(p), polygon.get(r));
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

    private Map<Point, Double> sortedAcuteAngleMap() {
        Point startPoint = polygon.get(0);
        double angleForStartPoint = geometryService.angleBetweenTwoLines(startPoint, polygon.get(polygon.size() - 1),
                startPoint, polygon.get(1));
        double maxAngle = angleForStartPoint < GeometryServiceImpl.STRAIGHT_ANGLE ? angleForStartPoint : 0;

        Map<Point, Double> pointToAngleMap = new HashMap<>();
        pointToAngleMap.put(startPoint, maxAngle);
        for (int i = 1; i < polygon.size(); i++) {
            final Point centerPoint = polygon.get(i);
            double angle;
            if (i + 1 == polygon.size()) {
                angle = geometryService.angleBetweenTwoLines(centerPoint, polygon.get(i - 1), centerPoint, startPoint);
                if (maxAngle < angle && angle < GeometryServiceImpl.STRAIGHT_ANGLE) {
                    pointToAngleMap.put(centerPoint, angle);
                    return pointToAngleMap;
                }
                break;
            }
            angle = geometryService.angleBetweenTwoLines(centerPoint, polygon.get(i - 1), centerPoint, polygon.get(i + 1));
            if (maxAngle < angle && angle < GeometryServiceImpl.STRAIGHT_ANGLE) {
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

    public void setPolygon(List<Point> polygon) {
        this.polygon = polygon;
    }

    public List<Point> getPolygon() {
        return polygon;
    }

    public void setGeometryService(GeometryServiceImpl geometryService) {
        this.geometryService = geometryService;
    }
}
