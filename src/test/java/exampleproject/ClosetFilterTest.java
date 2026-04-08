package exampleproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cluelessProjectApp.Closet;
import cluelessProjectApp.ClosetFilter;
import cluelessProjectApp.Clothing;
import cluelessProjectApp.ClothingType;
import cluelessProjectApp.ColorHSB;
import cluelessProjectApp.Season;

public class ClosetFilterTest {
    private ClosetFilter closetfilter;
    private ColorHSB color;
    private Clothing jacket;
    private Clothing pants;
    private Closet closet;

    @BeforeEach
    void setUp(){
        color = new ColorHSB(40, 0.40, 1.0);
        jacket = new Clothing(color, ClothingType.JACKET, Season.SUMMER, "cotton", "image.jpg");
        pants = new Clothing(color, ClothingType.PANTS, Season.ANY, "denim", "image.jpeg");
        closet = new Closet(List.of(pants, jacket));
        closetfilter = new ClosetFilter(closet);
    }

    @Test
    void testFiltering(){
        assertEquals(closetfilter.filterOut(), List.of(pants, jacket));

        closetfilter.setColorFilter("yellow");
        closetfilter.setSeasonFilter(Season.ANY);
        assertEquals(closetfilter.filterOut(), List.of(pants, jacket));

        closetfilter.setClothingFilter(ClothingType.JACKET);
        assertEquals(closetfilter.filterOut(), List.of(jacket));

        closetfilter.setMaterialFilter("wool");
        assertTrue(closetfilter.filterOut().isEmpty());

        closetfilter.clearFilters();
        assertEquals(closetfilter.filterOut(), List.of(pants, jacket));
    }

}
