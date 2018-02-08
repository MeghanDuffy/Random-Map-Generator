import edu.bsu.cs222.mapGenerator.map.Tile;
import javafx.scene.paint.Color;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TileTests {
    Tile tile;
    List<Tile> neighboringTiles;

    @Before
    public void createTile() {
        tile = new Tile(Tile.TYPE_LAND);
        neighboringTiles = new ArrayList<>();
        neighboringTiles.add(new Tile(Tile.TYPE_LAND));
    }

    @Test
    public void testGetNeighboringSeaTiles() {
        neighboringTiles.add(new Tile(Tile.TYPE_SEA));
        tile.setNeighboringTiles(neighboringTiles);
        Assert.assertEquals(1, tile.getNeighboringSeaTiles());
    }

    @Test
    public void testAverageColorBasedOnNeighbors() {
        neighboringTiles.add(new Tile(Tile.TYPE_LAND));
        neighboringTiles.get(0).setTileColor(new Color(1.0d, 1.0d, 1.0d, 1.0d));
        neighboringTiles.get(1).setTileColor(new Color(0.5d, 0.5d, 0.5d, 1.0d));
        tile.setNeighboringTiles(neighboringTiles);
        tile.averageColorBasedOnNeighbors(new Color(1d, 1d, 1d, 1d));
        Assert.assertEquals(0.75d, tile.getTileColor().getRed());
    }
}
