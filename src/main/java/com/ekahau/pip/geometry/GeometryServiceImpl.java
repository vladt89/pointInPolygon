package com.ekahau.pip.geometry;

import com.ekahau.pip.analyze.Direction;
import com.ekahau.pip.common.Point;

/**
 * @author vladimir.tikhomirov
 */
public class GeometryServiceImpl implements GeometryService {

    public static final int STRAIGHT_ANGLE = 180;

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

    @Override
    public SegmentStatus isSegmentIntersection(Point segmentFrom, Point segmentTo, Point anotherSegmentFrom, Point anotherSegmentTo) {

        final double result1 = analyzeDirection(segmentFrom, segmentTo, anotherSegmentFrom)
                * analyzeDirection(segmentFrom, segmentTo, anotherSegmentTo);

        final double result2 = analyzeDirection(anotherSegmentFrom, anotherSegmentTo, segmentFrom)
                * analyzeDirection(anotherSegmentFrom, anotherSegmentTo, segmentTo);

        boolean result = result1 <= 0 && result2 < 0;
        if (result) {
            return SegmentStatus.INTERSECTED;
        } else {
            return SegmentStatus.NON_INTERSECTED;
        }
    }

    private double analyzeDirection(Point from, Point to, Point pointToAnalyze) {
        return (to.getX() - from.getX()) * (pointToAnalyze.getY() - to.getY())
                - (to.getY() - from.getY()) * (pointToAnalyze.getX() - to.getX());
    }
}
