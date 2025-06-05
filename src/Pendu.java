import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;
import java.util.Arrays;
import java.beans.VetoableChangeListenerProxy;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;

    private Button boutonInfo;

    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
         this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");

   
    this.boutonMaison = new Button();
    this.boutonParametres = new Button();
    this.boutonInfo = new Button();

    // Tu peux aussi initialiser chrono ici si besoin
    this.chrono = new Chronometre();
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        
        BorderPane fenetre = new BorderPane();
    this.panelCentral = new BorderPane(); // toujours à faire sinon null
    fenetre.setTop(this.titre());
    fenetre.setCenter(this.panelCentral);
    return new Scene(fenetre, 800, 1000);
}

    


  private Pane fenetreAccueil() {
    return new Pane(); // version vide temporaire
}

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){ // haut
       BorderPane banniere = new BorderPane();
    banniere.setPadding(new Insets(10));
    banniere.setBackground(new Background(new BackgroundFill(Color.LAVENDER, null, null)));

    // Titre à gauche
    Text titre = new Text("Jeu du Pendu");
    titre.setFont(Font.font("Arial", FontWeight.BOLD, 30));
    banniere.setLeft(titre);

    // Icônes à droite
    HBox boutons = new HBox(10);

    ImageView iconeMaison = new ImageView(new Image("file:img/home.png"));
    iconeMaison.setFitWidth(50);
    iconeMaison.setFitHeight(30);
    iconeMaison.setPreserveRatio(true);

    ImageView iconeParam = new ImageView(new Image("file:img/parametres.png"));
    iconeParam.setFitWidth(50);
    iconeParam.setFitHeight(30);
    iconeParam.setPreserveRatio(true);

    ImageView iconeInfo = new ImageView(new Image("file:img/info.png"));
    iconeInfo.setFitWidth(50);
    iconeInfo.setFitHeight(30);
    iconeInfo.setPreserveRatio(true);

    this.boutonMaison.setGraphic(iconeMaison);
    this.boutonMaison.setOnAction(new RetourAccueil(this.modelePendu, this));

    this.boutonParametres.setGraphic(iconeParam);

    this.boutonInfo.setGraphic(iconeInfo);
    this.boutonInfo.setOnAction(new ControleurInfos(this));

    boutons.getChildren().addAll(boutonMaison, boutonParametres, boutonInfo);
    banniere.setRight(boutons);

    return banniere;
    }



 
    private VBox panneauDroite() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.TOP_CENTER);
        this.leNiveau = new Text("Niveau Difficile");
        leNiveau.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        this.chrono = new Chronometre();
        TitledPane chronoBox = new TitledPane("Chronomètre", chrono);
        chronoBox.setCollapsible(false);
        chronoBox.setPrefWidth(150);
        Button btnNouveau = new Button("Nouveau mot");
        vbox.getChildren().addAll(leNiveau, chronoBox, btnNouveau);
        return vbox;
    }

    // /**
     // * @return le panel du chronomètre
     // */
    // private TitledPane leChrono(){
        // A implementer
        // TitledPane res = new TitledPane();
        // return res;
    // }

    // /**
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier
     // */
      private Pane fenetreJeu() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        motCrypte.setFont(Font.font("Courier New", FontWeight.BOLD, 26));
        this.dessin = new ImageView(this.lesImages.get(0));
        dessin.setFitHeight(250);
        dessin.setPreserveRatio(true);
        VBox imageBox = new VBox(dessin);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(10));
        imageBox.setStyle("-fx-border-color: purple; -fx-border-width: 3;");
        this.pg = new ProgressBar(0);
        pg.setPrefWidth(150);
        String lettres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ-.";
        this.clavier = new Clavier(lettres, new ControleurLettres(this.modelePendu, this));
        vbox.getChildren().addAll(motCrypte, imageBox, pg, clavier);
        return vbox;
    }  //gère l'ensemble 
        // Pane res = new Pane();
        // return res;
    // }

    // /**
     // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     // */
    // private Pane fenetreAccueil(){
        // A implementer    
        // Pane res = new Pane();
        // return res;
    // }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
       
     this.panelCentral.setCenter(this.fenetreAccueil());
    }
    
    public void modeJeu(){
        this.panelCentral.setCenter(this.fenetreJeu());
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        // A implementer
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // A implementer
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
           return this.chrono;
       
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }


    public Alert popUpReglesDuJeu(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Règles du jeu");
        alert.setHeaderText("But du jeu :");
        alert.setContentText("Trouve le mot mystère en essayant des lettres.\n" +
                         "Attention, tu as un nombre limité d'erreurs !");
     return alert;
}

        

    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }

    

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        this.modeJeu();
        stage.show();
    }




    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}