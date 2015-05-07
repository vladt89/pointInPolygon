package com.ekahau.pip.analyze;

/**
 * Enumeration which contains values of the point direction regarding some line.
 *
 * @author vladimir.tikhomirov
 */
public enum Direction {
    /**
     * Shows that the point is located from the right side of the line.
     */
    RIGHT,
    /**
     * Shows that the point is located from the left side of the line.
     */
    LEFT,
    /**
     * Shows that the point belongs to the line.
     */
    SAME
}
