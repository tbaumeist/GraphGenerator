package com.tbaumeist.graphGenerator.degree;

/**
 * Degree source which provides a single constant number.
 */
public class FixedDegreeSource implements DegreeSource {
    private final int degree;

    public FixedDegreeSource(int degree) {
        this.degree = degree;
    }

    public int getDegree() {
        return degree;
    }
}
