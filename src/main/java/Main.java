import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Entry point of the application.
 *
 * @author vladimir.tikhomirov
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("------ Point in Polygon ------");

        // load Spring
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");

        final InputDataAnalyzer inputDataAnalyzer = (InputDataAnalyzer) applicationContext.getBean("inputDataAnalyzer");

        final List<Point> polygonPointList = inputDataAnalyzer.readFile("src/main/resources/polygon.txt");
        final List<Point> pointList = inputDataAnalyzer.readFile("src/main/resources/points.txt");

        final AnalyzeServiceImpl analyzeService = (AnalyzeServiceImpl) applicationContext.getBean("analyzeService");
        analyzeService.setPolygon(polygonPointList);

        System.out.println("Polygon vertexes: ");
        for (Point point : polygonPointList) {
            System.out.println(point.getX() + " " + point.getY());
        }

        System.out.println("Points to analyze: ");
        for (Point point : pointList) {
            boolean result = analyzeService.isPointInPolygon(point);
            System.out.println(point.getX() + " " + point.getY() + " point in polygon: " + result);
        }
    }
}