package exampleproject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cluelessProjectApp.Clothing;
import cluelessProjectApp.ClothingType;
import cluelessProjectApp.Season;
import cluelessProjectApp.ColorHSB;

public class ClothingTest {

    private ColorHSB color;
    private Clothing pants;

    @BeforeEach
    void setUp(){
        ColorHSB color = new ColorHSB(30, 0.40, 0.90);
        pants = new Clothing(color, ClothingType.PANTS, Season.SPRING, "denim", "image.jpg");
    }

    @Test
    void testValues(){
        assertEquals("denim", pants.getMaterial());
        assertEquals(Season.SPRING, pants.getSeason());
        assertEquals(ClothingType.PANTS, pants.getClothing());
        assertEquals("image.jpg", pants.getImagePath());
        assertEquals(0, pants.getCompatibilityScore());
    }

    @Test
    void testChangeScore(){
        pants.increaseScore(2);
        pants.decreaseScore(1);
        assertEquals(1, pants.getCompatibilityScore());
        pants.resetCompatibilityScore();
        assertEquals(0, pants.getCompatibilityScore());
        assertThrows(IllegalArgumentException.class, () -> pants.decreaseScore(-1));
        assertThrows(IllegalArgumentException.class, () -> pants.increaseScore(-2));
    }

    @Test
    void testSetImagePath(){
        assertThrows(IllegalArgumentException.class, 
            () -> new Clothing(color,ClothingType.JACKET, Season.SPRING, "leather", "null.haha"));
    }
}
