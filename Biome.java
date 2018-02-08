package edu.bsu.cs222.mapGenerator.map;

import edu.bsu.cs222.mapGenerator.util.Range;
import javafx.scene.paint.Color;

public class Biome {
    private Range rangeFromEquator;
    private Color biomeColor;

    public Biome() {

    }

    public Range getRangeFromEquator() {
        return rangeFromEquator;
    }

    public void setRangeFromEquator(Range rangeFromEquator) {
        this.rangeFromEquator = rangeFromEquator;
    }

    public Color getBiomeColor() {
        return biomeColor;
    }

    public void setBiomeColor(Color biomeColor) {
        this.biomeColor = biomeColor;
    }
}
