import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle ;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'un faire un héritié d'un TilePane
 */
public class Clavier extends TilePane{
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    /**
     * constructeur du clavier
     * @param touches une chaine de caractères qui contient les lettres à mettre sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne nombre de touches par ligne
     */
    public Clavier(String touches, EventHandler<ActionEvent> actionTouches) {
        

        this.clavier = new ArrayList<>();


/* 
     this.setPadding(new Insets(10));
        this.setHgap(5);
        this.setVgap(5);
        this.setPrefColumns(8); // 8 lettres max par ligne
 */

    for (char c : touches.toCharArray()) {
        String lettre = String.valueOf(c); 
        Button bouton = new Button(lettre);
        bouton.setOnAction(actionTouches); 

        bouton.setPrefWidth(40); // taille visuelle du bouton
        bouton.setPrefHeight(40);

        this.clavier.add(bouton); 
        this.getChildren().add(bouton);} 

    }



    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     * @param touchesDesactivees une chaine de caractères contenant la liste des touches désactivées
     */
    public void desactiveTouches(Set<String> touchesDesactivees){
           for (Button nombouton : clavier){
        String lettre = nombouton.getText();
        nombouton.setDisable(touchesDesactivees.contains(lettre));
    }
        
    }
}
