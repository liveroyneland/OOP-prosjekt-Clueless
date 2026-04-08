package cluelessProjectApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class cluelessController {

    private Closet closet;
    private ClosetFileHandler fileHandler = new ClosetFileHandler();
    private Outfit outfit = new Outfit();
    private OutfitSuggestion outfitSuggestion;
    private ClosetFilter closetFilter = new ClosetFilter(closet);

    private String selectedImagePath = null;

    //Definerer alle variablene
    @FXML private TilePane closetTilePane; 
    @FXML private TilePane outfitTilePane; 
    @FXML private TilePane suggestionTilePane; 
    
    @FXML private ChoiceBox<ClothingType> filterClothing; 
    @FXML private ChoiceBox<String> filterColor; 
    @FXML private ChoiceBox<String> filterMaterial; 
    @FXML private ChoiceBox<Season> filterSeason; 
    
    @FXML private ChoiceBox<ClothingType> newClothing; 
    @FXML private ColorPicker newColor; 
    @FXML private ChoiceBox<String> newMaterial; 
    @FXML private ChoiceBox<Season> newSeason; 
    
    @FXML private Button closetAddPhoto;
    @FXML private Button outfitClear;
    @FXML private Button filterApply;
    @FXML private Button filterClear;
    @FXML private Button closetAdd;
    
    //Initialiserer kontrolløren
    @FXML public void initialize(){
        loadCloset();

        outfitSuggestion = new OutfitSuggestion(closet);

        filterClothing.getItems().addAll(ClothingType.values());
        filterSeason.getItems().addAll(Season.values());
        filterMaterial.getItems().addAll("wool", "leather", "denim", "cotton", "silk", "suede", "other");
        filterColor.getItems().addAll("red", "orange", "yellow", "green", "blue", "purple", "pink", "white", "black", "grey");

        newClothing.getItems().addAll(ClothingType.values());
        newSeason.getItems().addAll(Season.values());
        newMaterial.getItems().addAll("wool", "leather", "denim", "cotton", "silk", "suede", "other");
        
        refreshClosetFX();
    }
    
    //laster inn filer
    public void loadCloset() {
        try {
            closet = fileHandler.loadFromFile("closet.txt");
        } catch (IOException e) {
            System.out.println("No saved closet found.");
            closet = new Closet();
        }
    }

    //lagrer filer
    public void saveCloset() {
    try {
        fileHandler.saveToFile("closet.txt", closet);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    //legger til ny klesnode
    @FXML public void addNew(){//metode som legger inn nytt klesplagg
        Color fxColor = newColor.getValue(); //får inn verdiene fra colorPicker og deler opp i hue, saturation og brightness
        ColorHSB color = new ColorHSB(fxColor.getHue(), fxColor.getSaturation(), fxColor.getBrightness()); //lager nytt objekt

        ClothingType clothing = newClothing.getValue(); //skaffer restena v variablene fra choiceBox
        Season season = newSeason.getValue();
        String material = newMaterial.getValue();

        if(clothing == null || season == null || material == null){ //sjekker om noen av verdiene er null
            System.out.println("Cannot add clothing before all fields are entered.");
            return;
        }
        
        Clothing newObjectclothing = new Clothing(color, clothing, season, material, selectedImagePath); //lager nytt objekt
        closet.addClothing(newObjectclothing); //legger til objekt i klesskapet og legger det til i tilePane i FX
        saveCloset();
        refreshClosetFX();
        
        newColor.setValue(Color.WHITE); //resetter alle verdier
        newClothing.setValue(null);
        newMaterial.setValue(null);
        newSeason.setValue(null);
        closetAddPhoto.setText("Add photo");
        selectedImagePath = "test";

    } 

    //når knappen for bildet trykkes så skal nytt bilde legges inn
    @FXML public void handleNewImage(){ 
        FileChooser fileChooser = new FileChooser(); //lager en ny filechooser, som lar brukeren åpne filene sine på pc-en
        fileChooser.setTitle("Choose photo");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Bilder", "*.png", "*.jpg", "*.jpeg" ));
        //filtrerer slik at brukeren kun kan bruke png, jpg og jpeg filer, der filteret har navnet bilder.
        File file = fileChooser.showOpenDialog(null); //åpner filene og lagrer filen i variabelen file. 

        if(file != null){ //hvis brukeren har lagt inn en fil
            selectedImagePath = file.getAbsolutePath(); //path som skal brukes for å lage/vise elementet i FX senere
            closetAddPhoto.setText("Photo chosen"); //endrer teksten på knappen sånn at brukeren kan se at de har klart å legge inn bilde.
        } 
    }

    //metode som legger til filtre og oppdaterer klesskapet
    @FXML public void applyFilters(){

        closetFilter.setClothingFilter(filterClothing.getValue());
        closetFilter.setSeasonFilter(filterSeason.getValue());
        closetFilter.setMaterialFilter(filterMaterial.getValue());
        closetFilter.setColorFilter(filterColor.getValue());

        List<Clothing> filtered = closetFilter.filterOut();

        closetTilePane.getChildren().clear();
        for(Clothing clothing : filtered){
            closetTilePane.getChildren().add(createClosetNode(clothing));
        }
    }

    //metode som fjerner alle filtre og refresher klesskapet
    @FXML public void clearFilters(){
        filterClothing.setValue(null);
        filterColor.setValue(null);
        filterSeason.setValue(null);
        filterMaterial.setValue(null);

        closetFilter.clearFilters();
        refreshClosetFX();
    }

    //metode som legger til klesplagg i antrekket og oppdaterer outfit og suggestions
    @FXML private void addToOutfit(Clothing clothing){ //metode for å legge til plagg i outfit + oppdatere suggestions
        outfit.addClothing(clothing); //legger til outfit med metode
        refreshOutfitFX();
        refreshSuggestionsFX();
    }

    //metode som fjerne klesplagg fra antrekket og oppdaterer outfit og suggestions
    @FXML private void removeFromOutfit(Clothing clothing){ //metode for å fjerne plagg i outfit + oppdatere suggestions
        outfit.removeClothing(clothing); //fjerner fra outfit med metode
        refreshOutfitFX();
        refreshSuggestionsFX();
    }
    @FXML
    public void clearOutfit() {
        outfit.clearOutfit();
        refreshOutfitFX();
        refreshSuggestionsFX();
    }

    private Node createBaseNode(Clothing clothing){ //lager node som skal ligge i tilePane
        ImageView imageview = new ImageView(); //noden skal være bildet + hva slags plagg, så derfor lager jeg et nytt imageView
        imageview.setFitWidth(40); 
        imageview.setPreserveRatio(true);

        if(clothing.getImagePath() != null){ //om de har lagt inn et bilde skal det brukes.
            imageview.setImage(new Image("file:" + clothing.getImagePath()));
        }

        Label clothingTypeLabel = new Label(clothing.getClothing().toString()); 
        VBox box = new VBox(5, imageview, clothingTypeLabel);//lager en Vbox med 5 i spacing, som har bildet og label i seg. 
        box.setAlignment(Pos.CENTER); //alt skal være sentrert
        box.setStyle("-fx-border-color: lightgray; -fx-padding: 10;"); //kanten skal være grå og det skal være 10 pixler mellomrom mellom alt annet og objektet.

        return box;
    }

    private Node createClosetNode(Clothing clothing){
        Node box = createBaseNode(clothing);
        box.setOnMouseClicked(e -> {
            System.out.println("CLICK");
            addToOutfit(clothing);
        });
        return box;
    }

    private Node createOutfitNode(Clothing clothing){
        Node box = createBaseNode(clothing);
        box.setOnMouseClicked(e -> removeFromOutfit(clothing));
        return box;
    }

    @FXML private void refreshOutfitFX(){
        outfitTilePane.getChildren().clear();

        for(Clothing c : outfit.getOutfit()){
            outfitTilePane.getChildren().add(createOutfitNode(c));
        }
    }

    @FXML private void refreshClosetFX(){
        closetTilePane.getChildren().clear();

        for(Clothing clothing : closet.getClothes()){
            closetTilePane.getChildren().add(createClosetNode(clothing));
        }
    }

    @FXML private void refreshSuggestionsFX(){
        List<Clothing> list = new ArrayList<>(outfitSuggestion.compatibilityScore(outfit));
        list.sort(new ComparatorInterface());
    
        suggestionTilePane.getChildren().clear();
        for (Clothing clothing : list){
            suggestionTilePane.getChildren().add(createClosetNode(clothing));
        }
    }

    public void shutdown(){
        saveCloset();
    }
}
