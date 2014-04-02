package com.tbaumeist.graphGenerator.degree;

public interface DegreeSource {
    /**
     * @return degree conforming to the distribution.
     */
    public int getDegree();
}