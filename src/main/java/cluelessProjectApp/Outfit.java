package cluelessProjectApp;
import java.util.ArrayList;
import java.util.List;


public class Outfit{
    private List<Clothing> outfit;

    public Outfit(){
        this.outfit = new ArrayList<>();
    }

    public void addClothing(Clothing clothing){
        if(outfit.contains(clothing)){
            throw new IllegalArgumentException("Cannot add clothing twice.");
        }
        else{
            outfit.add(clothing);
        }
    }

    public void removeClothing(Clothing clothing){
        if(outfit.contains(clothing)){
            outfit.remove(clothing);
        }
        else{
            throw new IllegalArgumentException("Cannot remove clothing not part of the outfit.");
        }
    } 

    public void clearOutfit(){
        outfit = new ArrayList<>();
    }

    public List<Clothing> getOutfit(){
        return outfit;
    }
}
