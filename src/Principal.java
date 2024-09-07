import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class Principal extends Application {
    private Card card;
    private Divida divida;
    private VBox dividasFields;
    private Button addButton;
    private int addButtonClickCount = 0; // Variável para contar cliques no botão
    private Scene scene;
    private static final int INITIAL_HEIGHT = 350;
    private static final int HEIGHT_INCREMENT = 78;
    private static final String BUTTON_STYLE = "-fx-background-color: #0C0812; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;";
    private static final String BUTTON_FOCUSED_STYLE = BUTTON_STYLE + " -fx-alignment: center; -fx-effect: dropshadow(gaussian, white, 5, 0.01, 0, 0);";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cálculo do Cartão");

        // Inicializar classe
        card = new Card() {};
        divida = new Divida() {};

        // Criar VBox para adicionar o novo painel superior e o mainGrid
        VBox mainLayout = new VBox(10);

        // Painel superior
        HBox topPanel = createTopPanel();

        // GridPane para layout
        GridPane mainGrid = createMainGrid();

        // VBox para armazenar campos de dívidas
        dividasFields = new VBox(5);
        dividasFields.setAlignment(Pos.CENTER);
        dividasFields.setMinHeight(100);

        // Botão para adicionar dívidas
        addButton = createAddButton();

        // Cria um VBox para os campos de dívidas e o botão
        VBox dividasLayout = new VBox(10);
        dividasLayout.setAlignment(Pos.CENTER);
        dividasLayout.getChildren().addAll(dividasFields, addButton);

        // Adiciona o GridPane e o VBox ao layout principal
        mainLayout.getChildren().addAll(topPanel, mainGrid, dividasLayout);
        mainLayout.setBackground(createBackground());

        // Inicializa a Scene e a altura inicial
        scene = new Scene(mainLayout, 960, INITIAL_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        this.scene = scene;
    }

    private HBox createTopPanel() {
        HBox topPanel = new HBox();
        topPanel.setPadding(new Insets(10));
        topPanel.setSpacing(10);
        topPanel.setAlignment(Pos.CENTER);
        topPanel.setBackground(new Background(new BackgroundFill(
                Color.web("#28274B"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Título intuitivo
        UIConfig.CustomLabel titleLabel = new UIConfig.CustomLabel("Informações do Usuário");
        titleLabel.setStyle("-fx-font-size: 20px;-fx-font-weight: bold; -fx-text-fill: white;");
        topPanel.getChildren().add(titleLabel);

        return topPanel;
    }

    private GridPane createMainGrid() {
        GridPane mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(10));
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);
        mainGrid.setAlignment(Pos.CENTER);

        // Campo de texto para Limite:
        UIConfig.CustomLabel limiteCustom = new UIConfig.CustomLabel("Limite do cartão: ");
        TextField limiteField = createLimiteField();
        limiteField.setPromptText("0,00");
        GridPane.setHgrow(limiteField, Priority.NEVER);

        // Campo de vencimento
        UIConfig.CustomLabel vencimentoCustomLabel = new UIConfig.CustomLabel("Vencimento");
        ComboBox<Integer> vencimentoCombo = createVencimentoCombo();
        GridPane.setHgrow(vencimentoCombo, Priority.NEVER);

        // Adicione os elementos ao GridPane
        mainGrid.add(limiteCustom, 0, 0);
        mainGrid.add(limiteField, 1, 0);
        mainGrid.add(vencimentoCustomLabel, 2, 0);
        mainGrid.add(vencimentoCombo, 3, 0);

        return mainGrid;
    }

    private TextField createLimiteField() {
        TextField limiteField = new TextField();
        UIConfig.configureTextField(limiteField);
        limiteField.textProperty().addListener((obs, oldText, newText) -> {
            try {
                newText = newText.replace(",", ".");
                card.setLimiteCartao(newText.isEmpty() ? 0 : (int) Float.parseFloat(newText));
            } catch (NumberFormatException e) {
                card.setLimiteCartao(0);
            }
        });
        return limiteField;
    }

    private ComboBox<Integer> createVencimentoCombo() {
        ComboBox<Integer> vencimentoCombo = new ComboBox<>();
        UIConfig.configureComboBox(vencimentoCombo);
        vencimentoCombo.getItems().addAll(divida.getMesDivida());
        vencimentoCombo.getSelectionModel().selectFirst();
        vencimentoCombo.setMinWidth(100);
        vencimentoCombo.setMaxWidth(100);
        return vencimentoCombo;
    }

    private Button createAddButton() {
        Button addButton = new Button("Adicionar Dívida");
        addButton.setOnAction(e -> addDividaFields());
        addButton.setPrefWidth(250);
        addButton.setMaxWidth(250);
        addButton.setMinWidth(250);
        addButton.setAlignment(Pos.CENTER);
        addButton.setStyle(BUTTON_STYLE);
        addButton.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                addButton.setStyle(BUTTON_FOCUSED_STYLE);
            }
        });
        return addButton;
    }

    private Background createBackground() {
        return new Background(new BackgroundFill(
                new LinearGradient(
                        0, 0, 1, 1, // Posição do gradiente (do canto superior esquerdo ao inferior direito)
                        true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#5E48A3")), // Cor inicial
                        new Stop(0.5, Color.web("#7B47A3")), // Cor inicial
                        new Stop(1, Color.web("#A3487E")) // Cor final
                ),
                CornerRadii.EMPTY, Insets.EMPTY
        ));
    }

    private void addDividaFields() {
        if (this.scene != null) {
            double height = this.scene.getHeight();
        } else {
            System.out.println("A cena ainda não foi inicializada.");
        }

        HBox dividaRow = new HBox(10);
        dividaRow.setAlignment(Pos.CENTER);

        UIConfig.CustomLabel nmLabel = new UIConfig.CustomLabel("Nome da dívida: ");
        TextField nomeDividaField = new TextField();
        UIConfig.configureTextField(nomeDividaField);
        nomeDividaField.setPromptText("Dívida");

        UIConfig.CustomLabel vlLabel = new UIConfig.CustomLabel("Valor: ");
        TextField vlDivida = new TextField();
        UIConfig.configureTextField(vlDivida);
        vlDivida.setPromptText("0,00");

        UIConfig.CustomLabel pclLabel = new UIConfig.CustomLabel("Parcelas: ");
        ComboBox<Integer> parcelasCombo = new ComboBox<>();
        UIConfig.configureComboBox(parcelasCombo);
        parcelasCombo.getItems().addAll(divida.getParcelas());
        parcelasCombo.getSelectionModel().selectFirst();

        UIConfig.CustomLabel dateLabel = new UIConfig.CustomLabel("Data: ");
        ComboBox<Integer> diaDividaCombo = new ComboBox<>();
        UIConfig.configureComboBox(diaDividaCombo);
        diaDividaCombo.getItems().addAll(divida.getDiaDivida());
        diaDividaCombo.getSelectionModel().selectFirst();
        ComboBox<Integer> mesDividaCombo = new ComboBox<>();
        UIConfig.configureComboBox(mesDividaCombo);
        mesDividaCombo.getItems().addAll(divida.getMesDivida());
        mesDividaCombo.getSelectionModel().selectFirst();

        dividaRow.getChildren().addAll(nmLabel, nomeDividaField, vlLabel, vlDivida, pclLabel, parcelasCombo, dateLabel, diaDividaCombo, mesDividaCombo);
        dividasFields.getChildren().add(dividaRow);

        // Incrementa o contador e ajusta a altura da cena se necessário
        addButtonClickCount++;
        if (addButtonClickCount >= 3) {
            double newHeight = scene.getHeight() + HEIGHT_INCREMENT;
            scene.getWindow().setHeight(newHeight);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
