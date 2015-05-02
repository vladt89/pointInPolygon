import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point of the application.
 *
 * @author vladimir.tikhomirov
 */
public class Main {
    public static void main(String[] args) {
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        final RunningService runningService = (RunningService) applicationContext.getBean("runningService");
        runningService.start();
    }
}