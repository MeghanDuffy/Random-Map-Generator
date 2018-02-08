import edu.bsu.cs222.mapGenerator.map.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.List;

public class MapTests {
    private Map map;

    @Before
    public void createMap() {
        map = new Map();
        map.generateNewMap();
    }

    @Test
    public void testGenerateTiles() {
        Assert.assertEquals(map.getMapWidthInTiles()*map.getMapHeightInTiles(),
                map.getMapTilesList().size());
    }

    @Test
    public void testCreateNewTile() {
        Tile tile = map.createNewTile(64);
        Assert.assertNotNull(tile);
    }

    @Test
    public void testIsTileOnEdgeOfMap() {
        Point mapEdgeCoords = new Point(0, 16);
        Assert.assertTrue(map.isTileOnEdgeOfMap(mapEdgeCoords));
    }

    @Test
    public void testGetTileCoords() {
        int xCoord = 64;
        int yCoord = 32;
        Assert.assertEquals(xCoord, map.getTileCoords(yCoord*map.getMapWidthInTiles()+xCoord).getX());
    }

    @Test
    public void testGetListOfNeighboringTiles() {
        List<Tile> neighboringTilesList = map.getListOfNeighboringTiles(new Point(64, 32));
        Assert.assertEquals(8, neighboringTilesList.size());
    }

    @Test
    public void testGenerateSectors() {
        Assert.assertNotNull(map.getMapSectorsList());
    }

    @Test
    public void testCreateSector() {
        Sector sector = map.createSector(new Point(0, 0));
        Assert.assertNotNull(sector);
    }

    @Test
    public void testTrimUpperBounds() {
        Point upperBounds = new Point(map.getMapWidthInTiles()*2, map.getMapHeightInTiles()*2);
        Point trimmedUpperBounds = map.trimUpperBounds(upperBounds);
        Assert.assertEquals(map.getMapWidthInTiles(), trimmedUpperBounds.getX());
    }

    @Test
    public void testAssignTilesToSector() {
        Assert.assertNotNull(map.getMapSectorsList().get(0).getTiles().get(0));
    }

    @Test
    public void testAssignBiomeToSector() {
        Assert.assertNotNull(map.getMapSectorsList().get(0).getBiome());
    }

    @Test
    public void testGetBiomesWithinRange() {
        Assert.assertNotEquals(0, map.getBiomesWithinRange(new Point(64, 32)).size());
    }

    @Test
    public void testTileColors() {
        Assert.assertNotNull(map.getMapTilesList().get(0).getTileColor());
    }

    @Test
    public void testGetMapWritableImage() {
        Assert.assertNotNull(map.getMapWritableImage());
    }
}
