package exampleproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cluelessProjectApp.Clothing;
import cluelessProjectApp.ColorHSB;
import cluelessProjectApp.Outfit;
import cluelessProjectApp.Season;
import cluelessProjectApp.ClothingType;

public class OutfitTest {
    
    private Clothing pants;
    private Clothing jacket;
    private ColorHSB color;
    private Outfit outfit;

    @BeforeEach
    void setUp(){
        color = new ColorHSB(40, 0.40, 0.40);
        pants = new Clothing(color, ClothingType.PANTS, Season.ANY, "denim", "image.jpeg");
        jacket = new Clothing(color, ClothingType.JACKET, Season.WINTER, "wool", "image.jpg");
        outfit = new Outfit();
    }

    @Test
    void addAndRemoveClothing(){
        outfit.addClothing(jacket);
        assertThrows(IllegalArgumentException.class, () -> outfit.addClothing(jacket));
        assertThrows(IllegalArgumentException.class, () -> outfit.removeClothing(pants));
        assertEquals(List.of(jacket), outfit.getOutfit());
        
    }

    @Test
    void testValues(){
        assertTrue(outfit.getOutfit().isEmpty());
    }

    @Test
    void testClearOutfit(){
        outfit.addClothing(jacket);
        outfit.clearOutfit();
        assertTrue(outfit.getOutfit().isEmpty());
    }

}
