package cluelessProjectApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ClosetFileHandler {
 /**
     * Lagrer hele closet til fil.
     * Hver Clothing lagres som én linje med ; som separator.
     */
    public void saveToFile(String filename, Closet closet) throws IOException {
        if (closet == null) {
            throw new IllegalArgumentException("Closet cannot be null.");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Clothing c : closet.getClothes()) {

                // Sikrer at imagePath ikke er null
                String imagePath = c.getImagePath() == null ? "null" : c.getImagePath();

                writer.println(
                    c.getColorHSB().getHue() + ";" +
                    c.getColorHSB().getSaturation() + ";" +
                    c.getColorHSB().getBrightness() + ";" +
                    c.getClothing().name() + ";" +
                    c.getSeason().name() + ";" +
                    c.getMaterial() + ";" +
                    imagePath
                );
            }
        }
    }

    /**
     * Leser closet fra fil og bygger opp objekter på nytt.
     */
    public Closet loadFromFile(String filename) throws IOException {
        Closet closet = new Closet();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    Clothing clothing = parseClothing(line);
                    closet.addClothing(clothing);
                } catch (Exception e) {
                    // Hopper over linjer som er feilformatert
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        }

        return closet;
    }

    /**
     * Hjelpemetode som gjør én tekstlinje om til et Clothing-objekt.
     */
    private Clothing parseClothing(String line) {
        String[] parts = line.split(";");

        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid file format.");
        }

        double hue = Double.parseDouble(parts[0]);
        double saturation = Double.parseDouble(parts[1]);
        double brightness = Double.parseDouble(parts[2]);

        ColorHSB color = new ColorHSB(hue, saturation, brightness);

        ClothingType type = ClothingType.valueOf(parts[3]);
        Season season = Season.valueOf(parts[4]);
        String material = parts[5];

        String imagePath = parts[6];
        if (imagePath.equals("null")) {
            imagePath = null;
        }

        return new Clothing(color, type, season, material, imagePath);
    }
}
