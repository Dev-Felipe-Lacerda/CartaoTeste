import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class Principal extends Application {
    private Card card;
    private Divida divida;
    private VBox dividasFields;
    private Button addButton;
    private Button calcularButton;
    private Label totalLabel;
    private int addButtonClickCount = 0;
    private Scene scene;
    private static final int INITIAL_HEIGHT = 450;
    private static final int HEIGHT_INCREMENT = 36;
    private static final String BUTTON_STYLE = "-fx-background-color: #0C0812; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;";
    private static final String BUTTON_FOCUSED_STYLE = BUTTON_STYLE + " -fx-alignment: center; -fx-effect: dropshadow(gaussian, white, 5, 0.01, 0, 0);";
    private static final String TOTAL_LABEL_STYLE = "-fx-font-size: 16px; -fx-font-weight: bold;";
    private static final String TOTAL_LABEL_NORMAL_STYLE = TOTAL_LABEL_STYLE + "-fx-text-fill: white;";
    private static final String TOTAL_LABEL_ALERT_STYLE = TOTAL_LABEL_STYLE + "-fx-text-fill: yellow;";

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

        // Botão para calcular
        calcularButton = createCalcularButton();

        // Label para mostrar total e mensagem de alerta
        totalLabel = new Label();
        totalLabel.setStyle(TOTAL_LABEL_NORMAL_STYLE);
        updateTotalLabel(0, false); // Inicializa o LabelTotal

        // Cria um VBox para os campos de dívidas, o botão e o LabelTotal
        VBox dividasLayout = new VBox(10);
        dividasLayout.setAlignment(Pos.CENTER);
        dividasLayout.getChildren().addAll(dividasFields, addButton, totalLabel, calcularButton);

        // Adiciona o GridPane e o VBox ao layout principal
        mainLayout.getChildren().addAll(topPanel, mainGrid, dividasLayout);
        mainLayout.setBackground(createBackground());

        // Inicializa a Scene e a altura inicial
        scene = new Scene(mainLayout, 1280, INITIAL_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
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
                updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao());
            } catch (NumberFormatException e) {
                card.setLimiteCartao(0);
                updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao());
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

    private Button createCalcularButton() {
        Button calcularButton = new Button("Faturas");
        calcularButton.setOnAction(e -> openCalculationWindow());
        calcularButton.setPrefWidth(250);
        calcularButton.setMaxWidth(250);
        calcularButton.setMinWidth(250);
        calcularButton.setAlignment(Pos.CENTER);
        calcularButton.setStyle(BUTTON_STYLE);
        calcularButton.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                calcularButton.setStyle(BUTTON_FOCUSED_STYLE);
            }
        });
        return calcularButton;
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
        HBox dividaRow = new HBox(10);
        dividaRow.setAlignment(Pos.CENTER);

        UIConfig.CustomLabel tipoDividaLabel = new UIConfig.CustomLabel("Tipo: ");
        ComboBox<String> nichoCombo = new ComboBox<>();
        UIConfig.stringComboBox(nichoCombo);
        nichoCombo.getItems().addAll(divida.getNichos());

        UIConfig.CustomLabel nmLabel = new UIConfig.CustomLabel("Nome da dívida: ");
        TextField nomeDividaField = new TextField();
        nomeDividaField.setPrefWidth(100);
        nomeDividaField.setMaxWidth(100);
        nomeDividaField.setMinWidth(100);
        nomeDividaField.setPromptText("Ex: Roupas");
        nomeDividaField.setStyle("-fx-background-color: #0C0812; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center;");
        nomeDividaField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                nomeDividaField.setStyle("-fx-background-color: #0C0812; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center; -fx-effect: dropshadow(gaussian, white, 5, 0.01, 0, 0);");
            } else {
                nomeDividaField.setStyle("-fx-background-color: #0C0812; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center;");
            }
        });

        UIConfig.CustomLabel vlLabel = new UIConfig.CustomLabel("Valor: ");
        TextField vlDivida = new TextField();
        UIConfig.configureTextField(vlDivida);
        vlDivida.setPromptText("0,00");
        vlDivida.textProperty().addListener((obs, oldText, newText) -> {
            updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao());
        });

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

        // Botão Excluir
        Button excluirButton = new Button("Excluir");
        excluirButton.setStyle("-fx-background-color:#921710 ; -fx-text-fill: white; -fx-font-weight: bold;");
        excluirButton.setMinHeight(35);
        excluirButton.setOnAction(e -> {
            dividasFields.getChildren().remove(dividaRow);
            addButtonClickCount--; // Reduz o contador de cliques

            // Ajustar a altura ao remover
            adjustWindowHeight();
            updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao());
        });
        dividaRow.getChildren().addAll(tipoDividaLabel, nichoCombo, nmLabel, nomeDividaField, vlLabel, vlDivida, pclLabel, parcelasCombo, dateLabel, diaDividaCombo, mesDividaCombo, excluirButton);
        dividasFields.getChildren().add(dividaRow);
        addButtonClickCount++;

        // Ajusta a altura ao adicionar
        adjustWindowHeight();
        updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao());
    }

    private void adjustWindowHeight() {
        double newHeight = INITIAL_HEIGHT + (addButtonClickCount * HEIGHT_INCREMENT);
        if (newHeight < INITIAL_HEIGHT) {
            newHeight = INITIAL_HEIGHT;
        }
        scene.getWindow().setHeight(newHeight);
    }

    private double calculateTotalDividas() {
        double total = 0;
        for (int i = 0; i < dividasFields.getChildren().size(); i++) {
            HBox dividaRow = (HBox) dividasFields.getChildren().get(i);
            TextField valorField = (TextField) dividaRow.getChildren().get(5); //
            try {
                String text = valorField.getText().replace(",", ".");
                total += text.isEmpty() ? 0 : Double.parseDouble(text);
            } catch (NumberFormatException e) {
                // Ignorar valores inválidos
            }
        }
        return total;
    }

    private void updateTotalLabel(double total, boolean exceedLimit) {
        totalLabel.setText(String.format("Total das Dívidas: %.2f%s", total, exceedLimit ? " (Acima do limite!)" : ""));
        totalLabel.setStyle(exceedLimit ? TOTAL_LABEL_ALERT_STYLE : TOTAL_LABEL_NORMAL_STYLE);
    }

    private void openCalculationWindow() {
        Stage calculationStage = new Stage();
        calculationStage.setTitle("Calculadora de Dívidas");

        // Criar um layout semelhante ao da janela principal
        VBox calculationLayout = new VBox(10);
        calculationLayout.setAlignment(Pos.CENTER);
        calculationLayout.setBackground(createBackground());

        // Adicionar o título
        Label titleLabel = new Label("Label das faturas");
        titleLabel.setStyle("-fx-font-size: 20px;-fx-font-weight: bold; -fx-text-fill: white;");
        calculationLayout.getChildren().add(titleLabel);

        // Defina o tamanho da nova janela
        Scene calculationScene = new Scene(calculationLayout, 400, 300);
        calculationStage.setScene(calculationScene);
        calculationStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
