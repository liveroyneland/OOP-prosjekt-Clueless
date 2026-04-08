package cluelessProjectApp;
import java.util.Comparator;

public class ComparatorInterface implements Comparator<Clothing>{
    
    @Override
    public int compare(Clothing c1, Clothing c2){
        if(c1 == null || c2 == null) return 0;

        double s1 = c1.getCompatibilityScore();
        double s2 = c2.getCompatibilityScore();

        return Double.compare(s2, s1);
    }
    
}
