package com.ekahau.pip.common;

/**
 * Entity class for the point on the plane.
 *
 * @author vladimir.tikhomirov
 */
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Point)) {
            return false;
        }
        Point point = (Point) object;
        return this.x == point.getX() && this.y == point.getY();
    }

    public double distance(Point pointToAnalyze) {
        return Math.sqrt(Math.pow((pointToAnalyze.getX() - this.x), 2)
                       + Math.pow((pointToAnalyze.getY() - this.y), 2));
    }
}
