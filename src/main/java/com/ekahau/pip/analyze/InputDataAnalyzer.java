package com.ekahau.pip.analyze;

import com.ekahau.pip.common.Point;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class which is responsible for input point validation and parsing.
 *
 * @author vladimir.tikhomirov
 */
public class InputDataAnalyzer {

    public static final int DIMENSION_2D = 2;

    public List<Point> readFile(String filePath) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File cannot be found in " + System.getProperty("user.dir"));
            return null;
        }
        List<Point> pointList = null;
        try {
            pointList = parseFile(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pointList;
    }

    private List<Point> parseFile(BufferedReader reader) throws IOException {
        List<Point> pointList = new LinkedList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            final String[] points = line.split(";");
            for (String each : points) {
                final String[] split = each.split(",");
                if (split.length == DIMENSION_2D) {
                    final double coordinateX = Double.parseDouble(split[0].trim());
                    final double coordinateY = Double.parseDouble(split[1].trim());
                    final Point point = new Point(coordinateX, coordinateY);
                    pointList.add(point);
                }
            }
        }
        return pointList;
    }
}
