import edu.bsu.cs222.mapGenerator.map.Biome;
import edu.bsu.cs222.mapGenerator.map.Sector;
import edu.bsu.cs222.mapGenerator.map.Tile;
import javafx.scene.paint.Color;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Random;

public class SectorTests {
    private Sector sector;

    @Before
    public void createSector() {
        sector = new Sector(new Point(0, 0),
                new Point(32, 32));
        Biome biome = new Biome();
        biome.setBiomeColor(new Color(1d, 1d, 1d, 1d));
        sector.setBiome(biome);
        sector.addTileToSector(new Tile(Tile.TYPE_SEA));
        sector.addTileToSector(new Tile(Tile.TYPE_LAND));
    }

    @Test
    public void testApplyBiomeColorToTiles() {
        sector.applyBiomeColorToTiles(new Random());
        Assert.assertNotNull(sector.getTiles().get(0).getTileColor());
    }

    @Test
    public void testFillUncoloredTiles() {
        sector.fillUncoloredTiles();
        Assert.assertNotNull(sector.getTiles().get(1).getTileColor());
    }
}