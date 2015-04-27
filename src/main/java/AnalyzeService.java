/**
 * Interface which defines the methods for basic functionality for working with points in 2D.
 *
 * @author vladimir.tikhomirov
 */
public interface AnalyzeService {

    /**
     * Method determines from which side of the vector AB (A = from, B = to)
     * the provided point pointToAnalyze is situated.
     *
     * @param from beginning point A of the vector AB
     * @param to ending point B of the vector AB
     * @param pointToAnalyze point to be analyzed
     * @return the direction (RIGHT, LEFT) from where pointToAnalyze is situated,
     * SAME direction is returned when pointToAnalyze belongs to the vector AB
     */
    Direction analyzePoint(Point from, Point to, Point pointToAnalyze);

    /**
     * Determines if two segments (AB and CD) are crossing each other or not.
     *
     * @param segmentFrom beginning point A of the segment AB
     * @param segmentTo ending point B of the segment AB
     * @param anotherSegmentFrom beginning point C of the segment CD
     * @param anotherSegmentTo ending point D of the segment CD
     * @return {@code true} if segments AB and CD were intersected, {@code false} otherwise
     */
    boolean isSegmentIntersection(Point segmentFrom, Point segmentTo, Point anotherSegmentFrom, Point anotherSegmentTo);

    /**
     * Determines if the provided point belongs to polygon or not.
     *
     * @param point point to analyze
     * @return {@code true} if point belongs to polygon. {@code false} otherwise
     */
    boolean isPointInPolygon(Point point);
}
