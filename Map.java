package edu.bsu.cs222.mapGenerator.map;

import edu.bsu.cs222.mapGenerator.util.Range;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private Random random = new Random();
    private List<Tile> mapTilesList;
    private List<Sector> mapSectorsList;
    private List<Biome> biomesList;
    private int mapWidthInTiles = 256;
    private int mapHeightInTiles = 128;
    private float initialLandSpawnPercentage = 0.45f;
    private int landBirthThreshold = 4;
    private int landDeathThreshold = 5;
    private int biomeRadiusInTiles = 1;

    public Map() {
        loadBiomes();
    }

    private void loadBiomes() {
        biomesList = new ArrayList<>();
        Biome desert = new Biome();
        desert.setRangeFromEquator(new Range(0.0f, 0.20f));
        desert.setBiomeColor(new Color(0.98d, 0.98d, 0.59d, 1d));
        biomesList.add(desert);
        Biome arctic = new Biome();
        arctic.setRangeFromEquator(new Range(0.80f, 1.00f));
        arctic.setBiomeColor(new Color(1.0d, 1.0d, 1.0d, 1.0d));
        biomesList.add(arctic);
        Biome tundra = new Biome();
        tundra.setRangeFromEquator(new Range(0.6f, 0.9f));
        tundra.setBiomeColor(new Color(0.0d, 0.57d, 0.53d, 1.0d));
        biomesList.add(tundra);
        Biome plains = new Biome();
        plains.setRangeFromEquator(new Range(0.2f, 0.6f));
        plains.setBiomeColor(new Color(0.53d, 0.78d, 0.26d, 1.0d));
        biomesList.add(plains);
        Biome forest = new Biome();
        forest.setRangeFromEquator(new Range(0.3f, 0.8f));
        forest.setBiomeColor(new Color(0.51d, 0.79d, 0.59d, 1.0d));
        biomesList.add(forest);
        Biome savanna = new Biome();
        savanna.setRangeFromEquator(new Range(0.1f, 0.3f));
        savanna.setBiomeColor(new Color(0.78d, 0.76d, 0.11d, 1.0d));
        biomesList.add(savanna);
        Biome rainforest = new Biome();
        rainforest.setRangeFromEquator(new Range(0.0f, 0.25f));
        rainforest.setBiomeColor(new Color(0.0d, 0.51d, 0.18d, 1.0d));
        biomesList.add(rainforest);
        Biome mountains = new Biome();
        mountains.setRangeFromEquator(new Range(0.0f, 0.8f));
        mountains.setBiomeColor(new Color(0.58d, 0.52d, 0.45d, 1.0d));
        biomesList.add(mountains);
    }

    public void generateNewMap() {
        generateTiles();
        assignNeighborsToTiles();
        smoothLand();
        generateSectors();
        applyBiomeColorsToTiles();
        smoothBiomeColors();
    }

    public void generateTiles() {
        mapTilesList = new ArrayList<>();
        for (int tileIndex = 0; tileIndex < mapWidthInTiles*mapHeightInTiles; tileIndex++) {
            mapTilesList.add(createNewTile(tileIndex));
        }
    }

    public Tile createNewTile(int tileIndex) {
        Point tileCoords = getTileCoords(tileIndex);
        if (isTileOnEdgeOfMap(tileCoords)) {
            return new Tile(Tile.TYPE_SEA);
        } else {
            float randomFloat = random.nextFloat();
            if (randomFloat <= initialLandSpawnPercentage) {
                return new Tile(Tile.TYPE_LAND);
            } else {
                return new Tile(Tile.TYPE_SEA);
            }
        }
    }

    public boolean isTileOnEdgeOfMap(Point tileCoords) {
        return (tileCoords.getX() == 0 || tileCoords.getX() == (mapWidthInTiles - 1) ||
                tileCoords.getY() == 0 || tileCoords.getY() == (mapHeightInTiles - 1));
    }

    public Point getTileCoords(int tileIndex) {
        int tileXCoord = tileIndex%mapWidthInTiles;
        int tileYCoord = tileIndex/mapWidthInTiles;
        return new Point(tileXCoord, tileYCoord);
    }

    public void assignNeighborsToTiles() {
        for (int tileIndex = 0; tileIndex < mapTilesList.size(); tileIndex++) {
            Point currentTileCoords = getTileCoords(tileIndex);
            Tile currentTile = mapTilesList.get(tileIndex);
            currentTile.setNeighboringTiles(getListOfNeighboringTiles(currentTileCoords));
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public List getListOfNeighboringTiles(Point tileCoords) {
        List<Tile> neighboringTilesList = new ArrayList<>();
        Point firstNeighbor = new Point((int)tileCoords.getX() - 1, (int)tileCoords.getY() - 1);
        for (int neighborTileIndex = 0; neighborTileIndex < 9; neighborTileIndex++) {
            Point currentNeighbor = new Point((int)firstNeighbor.getX() + neighborTileIndex%3,
                    (int)firstNeighbor.getY() + neighborTileIndex/3);
            if (currentNeighbor.getX() == tileCoords.getX() && currentNeighbor.getY() == tileCoords.getY()) {

            } else if (currentNeighbor.getX() >= 0 && currentNeighbor.getX() < mapWidthInTiles &&
                    currentNeighbor.getY() >= 0 && currentNeighbor.getY() < mapHeightInTiles) {
                neighboringTilesList.add(mapTilesList.get((int)currentNeighbor.getY() * mapWidthInTiles + (int)currentNeighbor.getX()));
            } else {
                neighboringTilesList.add(new Tile(Tile.TYPE_SEA));
            }
        }
        return neighboringTilesList;
    }

    public void smoothLand() {
        int landSmoothingIterations = 2;
        for (int i = 0; i < landSmoothingIterations; i++) {
            for (int tileIndex = 0; tileIndex < mapTilesList.size(); tileIndex++) {
                applyLandSmoothingRules(tileIndex);
            }
        }
    }

    public void applyLandSmoothingRules(int tileIndex) {
        int borderingSeaTiles = mapTilesList.get(tileIndex).getNeighboringSeaTiles();
        if (mapTilesList.get(tileIndex).getTileType() == Tile.TYPE_SEA) {
            if (borderingSeaTiles < landBirthThreshold) {
                mapTilesList.get(tileIndex).setTileType(Tile.TYPE_LAND);
            }
        } else {
            if (borderingSeaTiles > landDeathThreshold) {
                mapTilesList.get(tileIndex).setTileType(Tile.TYPE_SEA);
            }
        }
    }

    public void generateSectors() {
        mapSectorsList = new ArrayList<>();
        int biomeDiameter = biomeRadiusInTiles*2;
        for (int yCoord = 0; yCoord < mapHeightInTiles; yCoord += biomeDiameter) {
            for (int xCoord = 0; xCoord < mapWidthInTiles; xCoord += biomeDiameter) {
                Sector currentSector = createSector(new Point(xCoord, yCoord));
                assignTilesToSector(currentSector);
                assignBiomeToSector(currentSector);
                mapSectorsList.add(currentSector);
            }
        }
    }

    public Sector createSector(Point sectorLowerBounds) {
        int biomeDiameter = biomeRadiusInTiles*2;
        Point sectorUpperBounds = new Point((int)sectorLowerBounds.getX() + biomeDiameter,
                (int)sectorLowerBounds.getY() + biomeDiameter);
        return new Sector(sectorLowerBounds, trimUpperBounds(sectorUpperBounds));
    }

    public Point trimUpperBounds(Point upperBounds) {
        Point trimmedSectorBounds = new Point((int)upperBounds.getX(), (int)upperBounds.getY());
        if (upperBounds.getX() > mapWidthInTiles) {
            trimmedSectorBounds.setLocation(mapWidthInTiles, trimmedSectorBounds.getY());
        }
        if (upperBounds.getY() > mapHeightInTiles) {
            trimmedSectorBounds.setLocation(trimmedSectorBounds.getX(), mapHeightInTiles);
        }
        return trimmedSectorBounds;
    }

    public void assignTilesToSector(Sector sector) {
        for (int yTileCoord = (int)sector.getLowerBounds().getY(); yTileCoord < sector.getUpperBounds().getY(); yTileCoord++) {
            for (int xTileCoord = (int)sector.getLowerBounds().getX(); xTileCoord < sector.getUpperBounds().getX(); xTileCoord++) {
                sector.addTileToSector(mapTilesList.get(yTileCoord * mapWidthInTiles + xTileCoord));
            }
        }
    }

    public void assignBiomeToSector(Sector sector) {
        ArrayList<Biome> biomesWithinRange = getBiomesWithinRange(sector.getCenter());
        int randomBiomeIndex = random.nextInt(biomesWithinRange.size());
        sector.setBiome(biomesWithinRange.get(randomBiomeIndex));
    }

    public ArrayList<Biome> getBiomesWithinRange(Point sectorCenter) {
        ArrayList<Biome> biomesWithinRange = new ArrayList<>();
        float equatorYPosition = mapHeightInTiles/2;
        float sectorCenterToEquatorRange = Math.abs((float)sectorCenter.getY() - equatorYPosition)/equatorYPosition;
        for(Biome biome : biomesList) {
            if(biome.getRangeFromEquator().isInRange(sectorCenterToEquatorRange)) {
                biomesWithinRange.add(biome);
            }
        }
        return biomesWithinRange;
    }

    public void applyBiomeColorsToTiles() {
        for(Sector sector : mapSectorsList) {
            sector.applyBiomeColorToTiles(random);
        }
    }

    public void smoothBiomeColors() {
        mapSectorsList.forEach(Sector::fillUncoloredTiles);
    }

    public WritableImage getMapWritableImage() {
        final int tileSizeInPixels = 8;
        int imageWidth = mapWidthInTiles*tileSizeInPixels;
        int imageHeight = mapHeightInTiles*tileSizeInPixels;
        WritableImage mapWritableImage = new WritableImage(imageWidth, imageHeight);
        for(int yTile = 0; yTile < mapHeightInTiles; yTile++) {
            for(int xTile = 0; xTile < mapWidthInTiles; xTile++) {
                Tile currentTile = mapTilesList.get(xTile + yTile*mapWidthInTiles);
                for(int yPixel = 0; yPixel < tileSizeInPixels; yPixel++) {
                    int currentPixelY = yPixel + yTile * tileSizeInPixels;
                    for(int xPixel = 0; xPixel < tileSizeInPixels; xPixel++) {
                        int currentPixelX = xPixel + xTile * tileSizeInPixels;
                        mapWritableImage.getPixelWriter().setColor(currentPixelX, currentPixelY, currentTile.getTileColor());
                    }
                }
            }
        }
        return mapWritableImage;
    }

    public List<Tile> getMapTilesList() {
        return mapTilesList;
    }

    public List<Sector> getMapSectorsList() {
        return mapSectorsList;
    }

    public int getMapHeightInTiles() {
        return mapHeightInTiles;
    }

    public int getMapWidthInTiles() {
        return mapWidthInTiles;
    }

    public void setMapWidthInTiles(int mapWidthInTiles) {
        this.mapWidthInTiles = mapWidthInTiles;
    }

    public void setMapHeightInTiles(int mapHeightInTiles) {
        this.mapHeightInTiles = mapHeightInTiles;
    }

    public void setInitialLandSpawnPercentage(float initialLandSpawnPercentage) {
        this.initialLandSpawnPercentage = initialLandSpawnPercentage;
    }

    public void setLandBirthThreshold(int landBirthThreshold) {
        this.landBirthThreshold = landBirthThreshold;
    }

    public void setLandDeathThreshold(int landDeathThreshold) {
        this.landDeathThreshold = landDeathThreshold;
    }
}