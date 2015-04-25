import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        inputDataAnalyzer.readFile("src/main/resources/polygon.txt");

        // 2. Calculate  poligon area
        // 3. Read points file
        // 4. Determine if each of the point is inside poligon, out side or on the border.
        // 5. Print the result of the calculations
    }
}