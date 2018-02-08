package edu.bsu.cs222.mapGenerator.map;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    public static final int TYPE_SEA = 0;
    public static final int TYPE_LAND = 1;

    private int tileType;
    private Color tileColor;
    private List<Tile> neighboringTiles;

    public Tile(int tileType) {
        this.tileType = tileType;
        neighboringTiles = new ArrayList<>();
    }

    public int getNeighboringSeaTiles() {
        int neighboringSeaTiles = 0;
        for (Tile tile : neighboringTiles) {
            if (tile.getTileType() == Tile.TYPE_SEA) {
                neighboringSeaTiles++;
            }
        }
        return neighboringSeaTiles;
    }

    public void averageColorBasedOnNeighbors(Color defaultColor) {
        if (tileColor == null) {
            double red = defaultColor.getRed();
            double green = defaultColor.getGreen();
            double blue = defaultColor.getBlue();
            for (Tile tile : neighboringTiles) {
                if (tile.getTileType() == Tile.TYPE_LAND && tile.getTileColor() != null) {
                    red = (red + tile.getTileColor().getRed())/2d;
                    green = (green + tile.getTileColor().getGreen())/2d;
                    blue = (blue + tile.getTileColor().getBlue())/2d;
                }
            }
            if (red == 0d && green == 0d && blue == 0d) {
                tileColor = defaultColor;
            } else {
                tileColor = new Color(red, green, blue, 1d);
            }
        }
    }

    public int getTileType() {
        return tileType;
    }

    public void setTileType(int tileId) {
        this.tileType = tileId;
    }

    public Color getTileColor() {
        return tileColor;
    }

    public void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
    }

    public void setNeighboringTiles(List<Tile> neighboringTiles) {
        this.neighboringTiles = neighboringTiles;
    }
}
