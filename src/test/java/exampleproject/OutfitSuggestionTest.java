package exampleproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cluelessProjectApp.Closet;
import cluelessProjectApp.Clothing;
import cluelessProjectApp.ClothingType;
import cluelessProjectApp.ColorHSB;
import cluelessProjectApp.OutfitSuggestion;
import cluelessProjectApp.Season;
import cluelessProjectApp.Outfit;

public class OutfitSuggestionTest {
    private ColorHSB color1;
    private ColorHSB color2;
    private Clothing jacket;
    private Clothing skirt;
    private Clothing pants;
    private Closet closet;
    private Outfit outfit;
    private OutfitSuggestion outfitsuggestion;

    @BeforeEach
    void setUp(){
        color1 = new ColorHSB(40, 0.40, 1.0);
        color2 = new ColorHSB(300, 0.8, 0.75);
        jacket = new Clothing(color2, ClothingType.JACKET, Season.SUMMER, "cotton", "image.jpg");
        skirt = new Clothing(color2, ClothingType.SKIRT, Season.SUMMER, "synthetic", "image.png");
        pants = new Clothing(color1, ClothingType.PANTS, Season.ANY, "denim", "image.jpeg");
        closet = new Closet(List.of(pants, jacket));
        outfit = new Outfit();
        outfit.addClothing(skirt);
        outfitsuggestion = new OutfitSuggestion(closet);
    }

    @Test
    void testSetValues(){
        assertThrows(IllegalArgumentException.class, () -> new OutfitSuggestion(null));
    }

    @Test
    void testFilterOut(){
        closet.addClothing(skirt);
        assertEquals(outfitsuggestion.filterOut(outfit), List.of(pants, jacket));
    }

    @Test
    void testCompatibilityScore(){
        outfit.removeClothing(jacket);
        double oldScore = pants.getCompatibilityScore();
        outfitsuggestion.compatibilityScore(outfit);
        double newScore = pants.getCompatibilityScore();
        assertTrue(oldScore <= newScore);
        assertNotNull(outfitsuggestion.compatibilityScore(outfit));
    }
}
