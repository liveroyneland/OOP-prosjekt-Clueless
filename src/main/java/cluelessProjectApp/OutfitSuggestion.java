package cluelessProjectApp;

import java.util.List;

public class OutfitSuggestion {
    private Closet closet;


    public OutfitSuggestion(Closet closet){
        this.setCloset(closet);
    }


    public List<Clothing> filterOut(Outfit outfit){
        List<Clothing> eligableClothing = closet.getClothes().stream()
        .filter(c -> outfit.getOutfit().stream().noneMatch(o -> c.getClothing().equals(o.getClothing())))
        //.filter(c -> outfit.getOutfit().stream().allMatch(o -> c.getSeason().equals(o.getSeason())))
        .toList();
        return eligableClothing;
    }


    public List<Clothing> compatibilityScore(Outfit outfit){
        List<Clothing> filtered = this.filterOut(outfit);
        for(Clothing clothing : filtered){
            clothing.resetCompatibilityScore();
        }

        for(Clothing outfitPiece : outfit.getOutfit()){
            ColorHSB hsbOutfit = outfitPiece.getColorHSB();
            double hueOutfit = hsbOutfit.getHue();

            for(Clothing clothing : filtered){
                ColorHSB hsbClothing = clothing.getColorHSB();
                double hueClothing = hsbClothing.getHue();
                double hueDiff = Math.abs(hueClothing-hueOutfit);

                if(hsbOutfit.isNeutral() && hsbClothing.isNeutral()){
                    clothing.increaseScore(0.9);
                }
                if(hueDiff < 15){
                    clothing.increaseScore(0.8);
                }
                else if(Math.abs(hueDiff-180) < 20){
                    clothing.increaseScore(0.85);
                }
                if(hueDiff > 50 && hueDiff < 70){
                    clothing.decreaseScore(0.5);
                }
                if(Math.abs(hsbOutfit.getBrightness() - hsbClothing.getBrightness()) > 0.4){
                    clothing.decreaseScore(0.2);
                }
                else{
                    clothing.increaseScore(0.3);
                }
                if(Math.abs(hsbOutfit.getSaturation()-hsbClothing.getSaturation()) < 0.4){
                    clothing.increaseScore(0.2);
                }
            }
        }
        return filtered;
    }

    private void setCloset(Closet closet){
        if(closet == null){
            throw new IllegalArgumentException("Arguments cannot be zero.");
        }
        this.closet = closet;
    }
    
}