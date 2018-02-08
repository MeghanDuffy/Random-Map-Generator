package edu.bsu.cs222.mapGenerator.map;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sector {
    private Point lowerBounds;
    private Point upperBounds;
    private Point center;
    private ArrayList<Tile> tilesInSector;
    private Biome sectorBiome;

    public Sector(Point lowerBounds, Point upperBounds) {
        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
        int sectorWidth = (int)upperBounds.getX() - (int)lowerBounds.getX();
        int sectorHeight = (int)upperBounds.getY() - (int)lowerBounds.getY();
        int centerX = (int)lowerBounds.getX() + sectorWidth/2;
        int centerY = (int)lowerBounds.getY() + sectorHeight/2;
        center = new Point(centerX, centerY);
        tilesInSector = new ArrayList<>();
    }

    public void applyBiomeColorToTiles(Random random) {
        float biomeLushness = 0.45f;
        for (Tile tile : tilesInSector) {
            float randomFloat = random.nextFloat();
            if (tile.getTileType() == Tile.TYPE_SEA) {
                tile.setTileColor(new Color(0d, 0d, 1d, 1d));
            } else if (randomFloat <= biomeLushness) {
                tile.setTileColor(sectorBiome.getBiomeColor());
            }
        }
    }

    public void fillUncoloredTiles() {
        for (Tile tile : tilesInSector) {
            tile.averageColorBasedOnNeighbors(sectorBiome.getBiomeColor());
        }
    }

    public List<Tile> getTiles() {
        return tilesInSector;
    }

    public Biome getBiome() {
        return sectorBiome;
    }

    public void addTileToSector(Tile tile) {
        tilesInSector.add(tile);
    }

    public void setBiome(Biome sectorBiome) {
        this.sectorBiome = sectorBiome;
    }

    public Point getLowerBounds() {
        return lowerBounds;
    }

    public Point getUpperBounds() {
        return upperBounds;
    }

    public Point getCenter() {
        return center;
    }
}
