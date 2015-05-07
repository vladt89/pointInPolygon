package com.ekahau.pip.analyze;

import com.ekahau.pip.common.Point;

/**
 * Interface which defines the methods of basic functionality for polygon analysis.
 *
 * @author vladimir.tikhomirov
 */
public interface AnalyzeService {

    /**
     * Determines if the provided point belongs to polygon or not.
     *
     * To achieve the goal this method is using binary search algorithm by searching between
     * which vertexes the point is located. Then it checks if the segment made by these vertexes
     * intersects segment which starts in the main point and ends in the point which we try to localize.
     * The main point is chosen just by first provided point from polygon set.
     *
     * The method works only for convex polygons, that is why it is not used in the application.
     *
     * @param point point to analyze
     * @return {@code true} if point belongs to polygon. {@code false} otherwise
     */
    boolean isPointInConvexPolygon(Point point);

    /**
     * Determines if the provided point belongs to polygon or not.
     *
     * To achieve the goal this method calculates the amount of intersections made by segment which
     * starts in the main point and ends in the point which we try to localize and other segments of
     * the polygon.
     *
     * @param point point to analyze
     * @return {@code true} if point belongs to polygon. {@code false} otherwise
     */
    boolean isPointInPolygon(Point point);

    /**
     * Prepare the polygon. Means that it searches for the main point for localization algorithm
     * based on the distances and angles from the vertexes of the polygon to the provided point.
     *
     * @param point point to analyze
     */
    void preparePolygon(Point point);
}
