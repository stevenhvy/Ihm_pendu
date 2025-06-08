


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controleur du clavier
 */
public class ControleurLettres implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     */
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    ControleurLettres(MotMystere modelePendu, Pendu vuePendu){
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * Actions à effectuer lors du clic sur une touche du clavier
     * Il faut donc: Essayer la lettre, mettre à jour l'affichage et vérifier si la partie est finie
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button boutonClique = (Button) actionEvent.getSource(); // get source stock le bouton du clavier 
        String texteButton = boutonClique.getText(); // get text permet d'afficher quel bouton et utiliser 
        char lettre = texteButton.charAt(0);  // convertir String en char 

        modelePendu.essaiLettre(lettre); // constructeur 
        vuePendu.majAffichage(); // contructeur 

        if (modelePendu.gagne()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Victoire !");
             alert.setHeaderText(null); // pas obliger 
             alert.setContentText("C'étais facile t'a juste de la chance ;)");
             alert.showAndWait(); // attend que l'utilisateur click sur ok pas obliger 

        }else if (modelePendu.perdu()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Défaite");
            alert.setHeaderText(null);
            alert.setContentText("t'es une merde mec recommence");
            alert.showAndWait();

        }


    }
}
