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

        // 1. Read and print poligon file
        final InputDataAnalyzer inputDataAnalyzer = (InputDataAnalyzer) applicationContext.getBean("inputDataAnalyzer");
        final List<Point> polygonPointList = inputDataAnalyzer.readFile("src/main/resources/polygon.txt");

        System.out.println("Polygon vertexes: ");
        for (Point point : polygonPointList) {
            System.out.println(point.getX() + " " + point.getY());
        }

        final List<Point> pointList = inputDataAnalyzer.readFile("src/main/resources/points.txt");
        System.out.println("Points to analyze: ");
        for (Point point : pointList) {
            System.out.println(point.getX() + " " + point.getY());
        }

        // 2. Calculate  poligon area
        // 3. Read points file
        // 4. Determine if each of the point is inside poligon, out side or on the border.
        // 5. Print the result of the calculations
    }
}