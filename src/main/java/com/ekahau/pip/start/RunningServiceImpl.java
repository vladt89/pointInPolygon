package com.ekahau.pip.start;

import com.ekahau.pip.analyze.AnalyzeServiceImpl;
import com.ekahau.pip.analyze.InputDataAnalyzer;
import com.ekahau.pip.common.Point;

import java.util.List;

/**
 * @author vladimir.tikhomirov
 */
public class RunningServiceImpl implements RunningService {

    private InputDataAnalyzer inputDataAnalyzer;
    private AnalyzeServiceImpl analyzeService;

    @Override
    public void start() {
        System.out.println("------ com.ekahau.pip.common.Point in Polygon ------");

        final List<Point> polygonPointList = inputDataAnalyzer.readFile("src/main/resources/polygon.txt");
        analyzeService.setPolygon(polygonPointList);

        System.out.println("Polygon vertexes: ");
        for (Point point : polygonPointList) {
            System.out.println(point.getX() + " " + point.getY());
        }

        System.out.println("Points to analyze: ");
        final List<Point> pointList = inputDataAnalyzer.readFile("src/main/resources/points.txt");
        for (Point point : pointList) {
            System.out.println(point.getX() + " " + point.getY()
                    + " point in polygon: " + analyzeService.isPointInPolygon(point));
        }
    }

    public void setInputDataAnalyzer(InputDataAnalyzer inputDataAnalyzer) {
        this.inputDataAnalyzer = inputDataAnalyzer;
    }

    public void setAnalyzeService(AnalyzeServiceImpl analyzeService) {
        this.analyzeService = analyzeService;
    }
}
