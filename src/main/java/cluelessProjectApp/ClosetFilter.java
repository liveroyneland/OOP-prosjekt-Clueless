package cluelessProjectApp;

import java.util.List;

public class ClosetFilter {
    private String colorFilter;
    private ClothingType clothingFilter;
    private String materialFilter;
    private Season seasonFilter;
    private Closet closet;

    public ClosetFilter(Closet closet){
        this.colorFilter = null;
        this.clothingFilter = null;
        this.materialFilter = null;
        this.seasonFilter = null;
        this.closet = closet;
    }

    public void setColorFilter(String color){
        this.colorFilter = color;
    }
    public void setClothingFilter(ClothingType clothing){
        this.clothingFilter = clothing;
    }
    public void setMaterialFilter(String material){
        this.materialFilter = material;
    }
    public void setSeasonFilter(Season season){
        this.seasonFilter = season;
    }

    public List<Clothing> filterOut(){
        List<Clothing> eligableClothing = closet.getClothes().stream()
        .filter(c -> colorFilter == null || colorFilter.equals(c.getColor()))
        .filter(c -> clothingFilter == null || clothingFilter.equals(c.getClothing()))
        .filter(c -> materialFilter == null || materialFilter.equals(c.getMaterial()))
        .filter(c -> seasonFilter == null || seasonFilter.equals(c.getSeason()))
        .toList();
        return eligableClothing;
    }

    public void clearFilters(){
        this.clothingFilter = null;
        this.colorFilter = null;
        this.materialFilter = null;
        this.seasonFilter = null;
    }
}
