package edu.bsu.cs222.mapGenerator.util;

public class Range {
    private float rangeMinimum;
    private float rangeMaximum;

    public Range(float rangeMinimum, float rangeMaximum) {
        this.rangeMinimum = rangeMinimum;
        this.rangeMaximum = rangeMaximum;
    }

    public boolean isInRange(float point) {
        if(point >= rangeMinimum && point <= rangeMaximum) {
            return true;
        }
        return false;
    }
}