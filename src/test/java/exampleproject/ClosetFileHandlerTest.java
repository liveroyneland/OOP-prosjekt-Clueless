package exampleproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import cluelessProjectApp.*;

public class ClosetFileHandlerTest {
     private final String testFile = "test_closet.txt";
    private ClosetFileHandler fileHandler = new ClosetFileHandler();

    /**
     * Rydder opp testfil etter hver test
     */
    @AfterEach
    public void cleanup() {
        File file = new File(testFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSaveAndLoadCloset() throws IOException {
        // Arrange
        Closet closet = new Closet();

        ColorHSB color = new ColorHSB(120, 0.5, 0.5);
        Clothing clothing = new Clothing(
            color,
            ClothingType.TOP,
            Season.SUMMER,
            "cotton",
            "image.jpg"
        );

        closet.addClothing(clothing);

        // Act
        fileHandler.saveToFile(testFile, closet);
        Closet loadedCloset = fileHandler.loadFromFile(testFile);

        // Assert
        List<Clothing> original = closet.getClothes();
        List<Clothing> loaded = loadedCloset.getClothes();

        assertEquals(original.size(), loaded.size(), "Size should be equal");

        Clothing originalClothing = original.get(0);
        Clothing loadedClothing = loaded.get(0);

        assertEquals(originalClothing.getClothing(), loadedClothing.getClothing());
        assertEquals(originalClothing.getSeason(), loadedClothing.getSeason());
        assertEquals(originalClothing.getMaterial(), loadedClothing.getMaterial());
        assertEquals(originalClothing.getImagePath(), loadedClothing.getImagePath());

        // Sjekker HSB-verdier
        assertEquals(
            originalClothing.getColorHSB().getHue(),
            loadedClothing.getColorHSB().getHue(),
            0.001
        );

        assertEquals(
            originalClothing.getColorHSB().getSaturation(),
            loadedClothing.getColorHSB().getSaturation(),
            0.001
        );

        assertEquals(
            originalClothing.getColorHSB().getBrightness(),
            loadedClothing.getColorHSB().getBrightness(),
            0.001
        );
    }
}
