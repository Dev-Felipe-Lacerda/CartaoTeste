import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class Principal extends Application {
    private Card card;
    private Divida divida;
    private VBox dividasFields;
    private Label totalLabel;
    private VBox rightVBox;

    private static final String BUTTON_STYLE = "-fx-background-color: #0C0812; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;";
    private static final String BUTTON_FOCUSED_STYLE = BUTTON_STYLE + " -fx-alignment: center; -fx-effect: dropshadow(gaussian, white, 5, 0.01, 0, 0);";

    @Override
    public void start(Stage primaryStage) {
        card = new Card() {};
        divida = new Divida() {};

        primaryStage.setTitle("Cartão");

        // Pane principal da lateral direita (resto da janela)
        rightVBox = new VBox(10);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.setPrefSize(1480, 600);
        rightVBox.setStyle("-fx-background-color: white;");
        rightVBox.setPadding(new Insets(20));

        // Inicializa dividasFields
        dividasFields = new VBox(10);
        dividasFields.setPadding(new Insets(10));
        dividasFields.setAlignment(Pos.CENTER);

        // Label que será atualizada com base na opção selecionada
        Label label = new Label("Selecione uma opção");
        label.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");
        rightVBox.getChildren().add(label);

        // Inicializa o totalLabel
        totalLabel = new Label("Total das Dívidas: 0.00");
        totalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        rightVBox.getChildren().add(totalLabel);  // Adiciona o totalLabel ao rightVBox

        // Ajuste a largura do totalLabel conforme a largura do rightVBox
        rightVBox.widthProperty().addListener((obs, oldWidth, newWidth) -> totalLabel.setMaxWidth((Double) newWidth));

        // VBox para armazenar os botões na lateral esquerda
        VBox leftPane = new VBox();
        leftPane.setPrefSize(200, 600);
        leftPane.setBackground(createBackground()); // Aplica o gradiente
        leftPane.setAlignment(Pos.CENTER); // Centraliza os botões verticalmente
        leftPane.setSpacing(20); // Espaçamento entre os botões

        // Inicializa o totalLabel
        totalLabel = new Label("Total das Dívidas: 0.00");
        totalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        totalLabel.setAlignment(Pos.CENTER);

        rightVBox.getChildren().add(totalLabel);

        rightVBox.widthProperty().addListener((obs, oldWidth, newWidth) -> totalLabel.setMaxWidth(newWidth.doubleValue()));

        // Sombra branca
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#FFFFFF80"));
        shadow.setRadius(20);
        shadow.setSpread(0.001);

        // Declaração dos botões
        Button option1 = new Button("Cartão");
        option1.setMinWidth(200);
        Button option2 = new Button("Dívidas");
        option2.setMinWidth(200);
        Button option3 = new Button("Fatura e Vencimentos");
        option3.setMinWidth(200);

        // Configurações para cada botão
        option1.setStyle("-fx-background-color: #0000001A ; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        option1.setOnAction(e -> handleOption(createMainGrid(), shadow, option1, option2, option3));
        option2.setStyle("-fx-background-color: #0000001A; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        option2.setOnAction(e -> handleOption2(createAddButton(), shadow, option1, option2, option3));

        // Adiciona os botões na lateral esquerda
        leftPane.getChildren().addAll(option1, option2);

        // Layout principal
        BorderPane root = new BorderPane();
        root.setLeft(leftPane);  // Define a lateral esquerda
        root.setCenter(rightVBox); // Define o restante da janela

        // Configura a cena
        Scene scene = new Scene(root, 1680, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleOption(GridPane mainGrid, DropShadow shadow, Button option1, Button option2, Button option3) {
        GridPane mainGrid1 = mainGrid;
        if (mainGrid1 == null) {
            mainGrid1 = createMainGrid();  // Só cria o GridPane uma vez
        }

        rightVBox.getChildren().clear();
        rightVBox.getChildren().addAll(mainGrid1);

        LinearGradient gradient = new LinearGradient(
                1, 0, 0, 1,
                true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#3E718F")),
                new Stop(1, Color.web("#244355"))
        );

        BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
        rightVBox.setBackground(new Background(backgroundFill));

        // Adiciona o DropShadow aos botões
        option1.setEffect(shadow);
        option2.setEffect(null);
        option3.setEffect(null);
    }

    private void handleOption2(Button addButton, DropShadow shadow, Button option1, Button option2, Button option3) {
        rightVBox.getChildren().clear();
        VBox dividasAndButtonVBox = new VBox(10);
        dividasAndButtonVBox.setAlignment(Pos.CENTER);
        dividasAndButtonVBox.getChildren().addAll(dividasFields, addButton);

        VBox totalLabelVBox = new VBox();
        totalLabelVBox.setAlignment(Pos.BOTTOM_CENTER);
        totalLabelVBox.getChildren().add(totalLabel);

        LinearGradient gradient = new LinearGradient(
                1, 0, 0, 1,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#4AAC90")),
                new Stop(1, Color.web("#31725F"))
        );

        BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
        rightVBox.setBackground(new Background(backgroundFill));
        rightVBox.getChildren().addAll(dividasAndButtonVBox, totalLabel);
        Platform.runLater(() -> totalLabel.setMaxWidth(rightVBox.getWidth() - 40));

        // Adiciona o DropShadow aos botões
        option1.setEffect(null);
        option2.setEffect(shadow);
        option3.setEffect(null);
    }

    private GridPane createMainGrid() {
        GridPane mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(10));
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);
        mainGrid.setAlignment(Pos.CENTER);

        // Campo de texto para Limite:
        UIConfig.CustomLabel limiteCustom = new UIConfig.CustomLabel("Limite do cartão: ");

        // Incorporar a lógica do createLimiteField diretamente
        TextField limiteField = new TextField();
        UIConfig.configureTextField(limiteField);
        limiteField.setPromptText("0,00");
        GridPane.setHgrow(limiteField, Priority.NEVER);

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

        // Campo de vencimento
        UIConfig.CustomLabel vencimentoCustomLabel = new UIConfig.CustomLabel("Vencimento");

        // Incorporar a lógica do createVencimentoCombo diretamente
        ComboBox<Integer> vencimentoCombo = new ComboBox<>();
        UIConfig.configureComboBox(vencimentoCombo);
        vencimentoCombo.getItems().addAll(divida.getMesDivida());
        vencimentoCombo.getSelectionModel().selectFirst();
        vencimentoCombo.setMinWidth(100);
        vencimentoCombo.setMaxWidth(100);
        GridPane.setHgrow(vencimentoCombo, Priority.NEVER);

        // Adicionar os elementos ao GridPane
        mainGrid.add(limiteCustom, 0, 0);
        mainGrid.add(limiteField, 1, 0);
        mainGrid.add(vencimentoCustomLabel, 2, 0);
        mainGrid.add(vencimentoCombo, 3, 0);

        return mainGrid;
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
                        0, 0, 1, 1,
                        true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#3D8E77")), // Cor inicial
                        new Stop(0.5, Color.web("#3D708E")), // Cor inicial
                        new Stop(1, Color.web("#3D578E")) // Cor final
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

        // Botão Excluir
        Button excluirButton = getButton(dividaRow);
        dividaRow.getChildren().addAll(tipoDividaLabel, nichoCombo, nmLabel, nomeDividaField, vlLabel, vlDivida, pclLabel, parcelasCombo, dateLabel, diaDividaCombo, mesDividaCombo, excluirButton);
        dividasFields.getChildren().add(dividaRow);

        // Adiciona um listener ao campo de valor para atualizar o totalLabel em tempo real
        vlDivida.textProperty().addListener((obs, oldText, newText) -> {
            try {
                updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao());
            } catch (NumberFormatException e) {
                // Ignorar valores inválidos
            }
        });
    }

    private Button getButton(HBox dividaRow) {
        Button excluirButton = new Button("Excluir");
        excluirButton.setStyle("-fx-background-color:#921710 ; -fx-text-fill: white; -fx-font-weight: bold;");
        excluirButton.setMinHeight(35);
        excluirButton.setOnAction(e -> {
            dividasFields.getChildren().remove(dividaRow);
            updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao());
        });
        return excluirButton;
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
        String message = String.format("Total das Dívidas: %.2f%s", total, exceedLimit ? " (Acima do limite!)" : "");
        totalLabel.setText(message);

        // Define o estilo do Label
        if (exceedLimit) {
            totalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: yellow;");
        } else {
            totalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        }

        // Centraliza o texto no Label
        totalLabel.setAlignment(Pos.CENTER);
        totalLabel.setMaxWidth(Double.MAX_VALUE);
        totalLabel.setBackground(null);
    }

    public void faturaEVencimentos() {
        Integer[] vencimentos = card.getVencimento();
        int vencimento = vencimentos[0];

        // Valor do período de fechamento
        int[] periodoFechamento = new int[7]; // Array para armazenar os 7 dias de fechamento

        for (int i = 0; i < 7; i++) {
            // Calcula o valor do fechamento considerando o ciclo do mês
            int diaFechamento = vencimento - (i + 1);
            if (diaFechamento < 1) {
                diaFechamento += 30; // Ajusta para o ciclo do mês
            }
            periodoFechamento[i] = diaFechamento;
        }

        // Exibe o período de fechamento para teste
        System.out.print("Período de Fechamento: ");
        for (int j : periodoFechamento) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
