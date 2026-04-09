package cluelessProjectApp;


public class Clothing {
    private ColorHSB color;
    private ClothingType clothing;
    private Season season;
    private String material;
    private String imagepath;
    private double compatibilityScore;

    public Clothing(ColorHSB color, ClothingType clothing, Season season, String material, String imagepath){
        this.color = color;
        this.clothing = clothing;
        this.season = season;
        this.material = material;
        this.compatibilityScore = 0.0;
        this.setImagePath(imagepath);
    }

    private void setImagePath(String imagepath){
        if(imagepath == null){
            this.imagepath = null;
            return;
        }
        int lastDot = imagepath.lastIndexOf('.');
        String ext = imagepath.substring(lastDot + 1).toLowerCase();
        if(ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png")){
            this.imagepath = imagepath;
        }
        else{
            throw new IllegalArgumentException("Image must be of type jpg, jpeg or png");
        }
    }

    public void increaseScore(double score){
        if(score <= 0){
            throw new IllegalArgumentException("Score must be a number more than zero.");
        }
        compatibilityScore += score;
    }

    public void decreaseScore(double score){
        if(score <= 0){
            throw new IllegalArgumentException("Score must be a number more than zero");
        }
        compatibilityScore -= score;
    }

    public String getColor(){
        return color.getColor();
    }

    public ColorHSB getColorHSB(){
        return color;
    }

    public Season getSeason(){
        return this.season;
    }

    public ClothingType getClothing(){
        return this.clothing;
    }

    public String getMaterial(){
        return this.material;
    }

    public String getImagePath(){
        return imagepath;
    }

    public double getCompatibilityScore(){
        return compatibilityScore;
    }

    public void resetCompatibilityScore(){
        compatibilityScore = 0;
    }

}
