/**
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

    boolean isSegmentIntersection(Point segmentFrom, Point segmentTo, Point anotherSegmentFrom, Point anotherSegmentTo);
}
