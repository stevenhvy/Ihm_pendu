import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


/**
 * Permet de gérer un Text associé à une Timeline pour afficher un temps écoulé
 */
public class Chronometre extends Text{
    /**
     * timeline qui va gérer le temps
     */
    private Timeline timeline;
    /**
     * la fenêtre de temps
     */
    private KeyFrame keyFrame;


    /**
     * le contrôleur associé au chronomètre
     */
    private ControleurChronometre actionTemps;

    /**
     * Constructeur permettant de créer le chronomètre
     * avec un label initialisé à "0:0:0"
     * Ce constructeur créer la Timeline, la KeyFrame et le contrôleur
     */
    public Chronometre(){
            
        this.actionTemps = new ControleurChronometre(this); //Crée un assistant (ControleurChronometre)
        this.setText("0:0"); 
        this.keyFrame = new KeyFrame(Duration.seconds(1), this.actionTemps); // appeler toute les seconde
        this.timeline = new Timeline(this.keyFrame);
         this.timeline.setCycleCount(Animation.INDEFINITE); // Lance une boucle infinie qui le fait

         

        
    }

    

    /**
     * Permet au controleur de mettre à jour le text
     * la durée est affichée sous la forme m:s
     * @param tempsMillisec la durée depuis à afficher
     */
    public void setTime(long tempsMillisec){
        long secondes = tempsMillisec / 1000;
        long minute = secondes / 60;
        long resteSecondes = secondes % 60;
        
        
        this.setText(minute + ":" + resteSecondes);


    }

    /**
     * Permet de démarrer le chronomètre
     */


    public void start(){
        this.timeline.play();
    }

    /**
     * Permet d'arrêter le chronomètre
     */
    public void stop(){
         this.timeline.stop();
    }

    /**
     * Permet de remettre le chronomètre à 0
     */
    public void resetTime(){
        this.stop();
        setText("0:0");
    }
}
