package cluelessProjectApp;
import java.util.ArrayList;
import java.util.List;

public class Closet implements ClosetInterface{
    private List<Clothing> clothes;

    public Closet(List<Clothing> clothes){
        this.setClothes(clothes);
    }

    public Closet(){
        this.clothes = new ArrayList<>();
    }

    public void addClothing(Clothing clothing){
        if(clothes.contains(clothing)){
            throw new IllegalArgumentException("Cannot add clothing which already exists in list.");
        }
        else{
            clothes.add(clothing);
        }
    }

    public List<Clothing> getClothes(){
        return clothes;
    }

    private void setClothes(List<Clothing> clothes){
        if(clothes == null){
            this.clothes = new ArrayList<>();
        }
        else{
            this.clothes = clothes;
        }
    }
}
