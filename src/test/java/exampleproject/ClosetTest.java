package exampleproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cluelessProjectApp.Closet;
import cluelessProjectApp.Clothing;
import cluelessProjectApp.ClothingType;
import cluelessProjectApp.ColorHSB;
import cluelessProjectApp.Season;

public class ClosetTest {

    private ColorHSB color;
    private Clothing jacket;
    private Clothing pants;
    private Closet closet;

    @BeforeEach
    void setUp(){
        color = new ColorHSB(40, 0.40, 0.40);
        jacket = new Clothing(color, ClothingType.JACKET, Season.SUMMER, "cotton", "image.jpg");
        pants = new Clothing(color, ClothingType.PANTS, Season.ANY, "denim", "image.jpeg");
        closet = new Closet(null);
    }

    @Test
    void testValues(){
        assertTrue(closet.getClothes().isEmpty());
    }

    void testAddClothing(){
        closet.addClothing(jacket);
        closet.addClothing(pants);
        assertEquals(List.of(jacket, pants), closet.getClothes());
        assertThrows(IllegalArgumentException.class, () -> closet.addClothing(jacket));
    }
}
