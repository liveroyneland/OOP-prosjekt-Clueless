package cluelessProjectApp;
import java.util.HashMap;


public class ColorHSB {
    private double hue;
    private double saturation;
    private double brightness;

    private String color;
    private HashMap<Integer, String> colors;

    public ColorHSB(double hue, double saturation, double brightness){
        this.setHue(hue);
        this.setSaturation(saturation);
        this.setBrightness(brightness);
        this.colors = new HashMap<Integer, String>();
        colors.put(0, "red");
        colors.put(1, "orange");
        colors.put(2, "yellow");
        colors.put(3, "green");
        colors.put(4, "green");
        colors.put(5, "blue");
        colors.put(6, "blue");
        colors.put(7, "purple");
        colors.put(8, "pink");
        colors.put(9, "pink");
        colors.put(10, "red");
        colors.put(11, "red");
        colors.put(12, "red");
    
        this.setColor(hue, saturation, brightness);
        }

    public double getHue(){
        return hue;
    }
    public double getSaturation(){
        return saturation;
    }
    public double getBrightness(){
        return brightness;
    }
    public String getColor(){
        return color;
    }
    public boolean isNeutral(){
        return saturation < 0.15;
    }

    public void setHue(double hue){
        if (hue < 0 || hue > 360){
            throw new IllegalArgumentException("Hue must be between 0 and 360 degrees.");
        }
        this.hue = hue;
    }

    public void setSaturation(double saturation){
        if(saturation < 0 || saturation > 1){
            throw new IllegalArgumentException("Saturation must be between 0 and 1.");
        }
        this.saturation = saturation;
    }

    public void setBrightness(double brightness){
        if(brightness < 0 || brightness > 1){
            throw new IllegalArgumentException("Brightness myst be between 0 and 1.");
        }
        this.brightness = brightness;
    }
    private void setColor(double hue, double saturation, double brightness){
        //sjekker svart
        if(brightness < 0.2){
            this.color = "black";
            return;
        }
        //sjekker hvit og grå
        if(saturation < 0.2){
            if(brightness > 0.9){
                this.color = "white";
            }
            if(brightness >= 0.2 && brightness < 0.9){
                this.color = "grey";
            }
            return;
        }

        //sjekker basert på hue
        int range = (int)(hue/30);
        this.color = colors.get(range);
    }
}
