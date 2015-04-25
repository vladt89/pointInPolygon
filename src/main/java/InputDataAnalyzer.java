import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class which is responsible for input data validation and parsing.
 *
 * @author vladimir.tikhomirov
 */
public class InputDataAnalyzer {

    public String[] readFile(String filePath) {
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File cannot be found in " + System.getProperty("user.dir"));
            return null;
        }
        String str;
        try {
            String[] array = null;
            while ((str = in.readLine()) != null) {
                array = str.split(";");
            }
            if (array != null) {
                for (String s : array) {
                    //TODO fix that we print only last line
                    System.out.print(s + " ");
                }
            }
            return array;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
