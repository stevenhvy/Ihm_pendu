import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurNiveau implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;


    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurNiveau(MotMystere modelePendu) {
        this.modelePendu = modelePendu;
    }

    /**
     * gère le changement de niveau
     * @param actionEvent
     */
 @Override
    public void handle(ActionEvent actionEvent) {
    RadioButton radiobouton = (RadioButton) actionEvent.getTarget();
    String nomDuRadiobouton = radiobouton.getText();
    System.out.println(nomDuRadiobouton);

    if (nomDuRadiobouton.equals("Facile")) {
        modelePendu.setNiveau(MotMystere.FACILE);
    } else if (nomDuRadiobouton.equals("Moyen")) {
        modelePendu.setNiveau(MotMystere.MOYEN);
    } else if (nomDuRadiobouton.equals("Difficile")) {
        modelePendu.setNiveau(MotMystere.DIFFICILE);
    } else if (nomDuRadiobouton.equals("Expert")) {
        modelePendu.setNiveau(MotMystere.EXPERT);
    }
}

}
