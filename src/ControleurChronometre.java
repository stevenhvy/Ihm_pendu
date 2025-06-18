import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur du chronomètre
 */
public class ControleurChronometre implements EventHandler<ActionEvent> {
    /**
     * temps enregistré lors du dernier événement
     */
    private long tempsPrec;
    /**
     * temps écoulé depuis le début de la mesure
     */
    private long tempsEcoule;
    /**
     * Vue du chronomètre
     */
    private Chronometre chrono;

    /**
     * Constructeur du contrôleur du chronomètre
     * noter que le modèle du chronomètre est tellement simple
     * qu'il est inclus dans le contrôleur
     * @param chrono Vue du chronomètre
     */
    public ControleurChronometre (Chronometre chrono){
            this.chrono = chrono;
         this.tempsPrec = System.currentTimeMillis(); // On mémorise le temps de départ
    this.tempsEcoule = 0; // Rien n’a été écoulé au départ

    }

    /**
     * Actions à effectuer tous les pas de temps
     * essentiellement mesurer le temps écoulé depuis la dernière mesure
     * et mise à jour de la durée totale
     * @param actionEvent événement Action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
          long maintenant = System.currentTimeMillis(); // Temps actuel
             long delta = maintenant - tempsPrec;          // Temps depuis le dernier tick
             tempsEcoule += delta;                         // On ajoute au temps total
          tempsPrec = maintenant;                       // On met à jour le point de référence
             chrono.setTime(tempsEcoule);      
    }

    /**
     * Remet la durée à 0
     */
    public void reset(){
        this.tempsEcoule = 0;
    this.tempsPrec = System.currentTimeMillis(); // on redémarre proprement
    }


    public BorderPane menuMonPanier() {
    BorderPane root = new BorderPane();

    // 🔠 Titre
    Label titre = new Label("Mon panier");
    titre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    titre.setPadding(new Insets(10));
    titre.setAlignment(Pos.CENTER);

    // 🧱 TableView
    TableView<Livre> table = new TableView<>();

    TableColumn<Livre, String> colNom = new TableColumn<>("Nom livre");
    colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

    TableColumn<Livre, String> colAuteur = new TableColumn<>("Auteur");
    colAuteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));

    TableColumn<Livre, Integer> colQte = new TableColumn<>("Quantité");
    colQte.setCellValueFactory(new PropertyValueFactory<>("quantite"));

    TableColumn<Livre, Double> colPrix = new TableColumn<>("Prix unitaire");
    colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));

    TableColumn<Livre, Void> colSupprimer = new TableColumn<>("Supprimer");
    colSupprimer.setCellFactory(param -> new TableCell<>() {
        private final Button btn = new Button("❌");

        {
            btn.setOnAction(e -> {
                Livre livre = getTableView().getItems().get(getIndex());
                table.getItems().remove(livre);
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(btn);
            }
        }
    });
}
