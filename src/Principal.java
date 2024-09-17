import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

    private boolean isFirstTime = true;
    private boolean labelsAdded;
    private String limiteCartaoTexto;
    private Integer vencimentoSelecionado;

    private static final String BUTTON_STYLE = "-fx-background-color: #0C0812F2; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;";
    private static final String BUTTON_FOCUSED_STYLE = BUTTON_STYLE + " -fx-alignment: center; -fx-effect: dropshadow(gaussian, white, 1, 0.1, 0, 0);";

    @Override
    public void start(Stage primaryStage) {
        card = new Card() {};
        divida = new Divida() {};

        primaryStage.setTitle("Cartão");

        // Pane principal da lateral direita (resto da janela)
        rightVBox = new VBox(10);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.setPrefSize(900, 600);
        rightVBox.setStyle("-fx-background-color: white;");
        rightVBox.setPadding(Insets.EMPTY);

        // Inicializa dividasFields
        dividasFields = new VBox(10);
        dividasFields.setPadding(new Insets(10));
        dividasFields.setAlignment(Pos.CENTER);

        // Label que será atualizada com base na opção selecionada
        Label label = new Label("Selecione uma opção");
        label.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");
        rightVBox.getChildren().add(label);

        // Inicializa o totalLabel apenas uma vez
        totalLabel = new Label("Total das Dívidas: 0.00");
        totalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        rightVBox.getChildren().add(totalLabel);

        // Ajuste a largura do totalLabel conforme a largura do rightVBox
        rightVBox.widthProperty().addListener((obs, oldWidth, newWidth) -> totalLabel.setMaxWidth((Double) newWidth));

        // VBox para armazenar os botões na lateral esquerda
        VBox leftPane = new VBox();
        leftPane.setPrefSize(270, 600);
        leftPane.setBackground(createBackground());
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setSpacing(20);

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
        Button option2 = new Button("Dívidas");
        Button option3 = new Button("Fatura e Vencimentos");

        // Configurações para cada botão
        option1.setStyle("-fx-background-color: #0000001A ; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        option1.setOnAction(e -> handleOption(createMainGrid(), shadow, option1, option2, option3));
        option2.setStyle("-fx-background-color: #0000001A; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        option2.setOnAction(e -> handleOption2(createAddButton(), shadow, option1, option2, option3));
        option3.setStyle("-fx-background-color: #0000001A; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        option3.setOnAction(e -> handleOption3(faturaEVencimentos(), shadow, option1, option2, option3));

        // Adiciona os botões na lateral esquerda
        leftPane.getChildren().addAll(option1, option2, option3);
        leftPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            option1.setMinWidth(newWidth.doubleValue());
            option2.setMinWidth(newWidth.doubleValue());
            option3.setMinWidth(newWidth.doubleValue());
        });

        // Layout principal
        BorderPane root = new BorderPane();
        root.setLeft(leftPane);  // Define a lateral esquerda
        root.setCenter(rightVBox); // Define o restante da janela

        // Configura a cena
        Scene scene = new Scene(root, 1144, 600);
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
        rightVBox.getChildren().addAll(dividasAndButtonVBox, totalLabelVBox);

        LinearGradient gradient = new LinearGradient(
                1, 0, 0, 1,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#4AAC90")),
                new Stop(1, Color.web("#31725F"))
        );

        BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
        rightVBox.setBackground(new Background(backgroundFill));
        Platform.runLater(() -> totalLabel.setMaxWidth(rightVBox.getWidth() - 40));

        option1.setEffect(null);
        option2.setEffect(shadow);
        option3.setEffect(null);

        //Iniciando no primeiro chamado
        if (isFirstTime) {
            addDividaFields();
            isFirstTime = false;
        }
    }

    private void handleOption3(Button faturaButton, DropShadow shadow, Button option1, Button option2, Button option3) {
        rightVBox.getChildren().clear();
        HBox faturasHBox = new HBox();
        faturasHBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(faturaButton, Priority.ALWAYS);
        faturasHBox.getChildren().add(faturaButton);

        LinearGradient gradient = new LinearGradient(
                1, 0, 0, 1,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#E0A24C")),
                new Stop(1, Color.web("#89632F"))
        );

        BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
        rightVBox.setBackground(new Background(backgroundFill));
        rightVBox.getChildren().add(faturasHBox);

        option1.setEffect(null);
        option2.setEffect(null);
        option3.setEffect(shadow);
        faturaButton.maxWidthProperty().bind(rightVBox.widthProperty().subtract(0));
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
        TextField limiteField = new TextField(limiteCartaoTexto);  // Define o valor salvo
        UIConfig.configureTextField(limiteField);
        limiteField.setPromptText("0,00");
        GridPane.setHgrow(limiteField, Priority.NEVER);

        limiteField.textProperty().addListener((obs, oldText, newText) -> {
            try {
                newText = newText.replace(",", ".");
                card.setLimiteCartao(newText.isEmpty() ? 0 : (int) Float.parseFloat(newText));
                limiteCartaoTexto = limiteField.getText();  // Salva o valor atual
                updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao());

            } catch (NumberFormatException e) {
                card.setLimiteCartao(0);
                updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao());
            }
        });

        UIConfig.CustomLabel vencimentoCustomLabel = new UIConfig.CustomLabel("Vencimento");

        // Incorporar a lógica do createVencimentoCombo diretamente
        ComboBox<Integer> vencimentoCombo = new ComboBox<>();
        UIConfig.configureComboBox(vencimentoCombo);
        vencimentoCombo.getItems().addAll(divida.getMesDivida());
        vencimentoCombo.getSelectionModel().selectFirst();
        vencimentoCombo.setMinWidth(100);
        vencimentoCombo.setMaxWidth(100);
        GridPane.setHgrow(vencimentoCombo, Priority.NEVER);

        vencimentoCombo.valueProperty().addListener((obs, oldValue, newValue) -> {
            vencimentoSelecionado = newValue; // Atualiza a variável de vencimento selecionado
        });
        if (vencimentoSelecionado != null) {
            vencimentoCombo.setValue(vencimentoSelecionado);
        }

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
        if (!labelsAdded) {
            UIConfig.CustomLabel tipoDividaLabel = new UIConfig.CustomLabel("Tipo");
            UIConfig.CustomLabel nmLabel = new UIConfig.CustomLabel("Nome da dívida");
            UIConfig.CustomLabel vlLabel = new UIConfig.CustomLabel("Valor");
            UIConfig.CustomLabel pclLabel = new UIConfig.CustomLabel("Parcelas");
            UIConfig.CustomLabel dateLabel = new UIConfig.CustomLabel("Data");
            Label empty = new Label("");


            GridPane labelsGrid = new GridPane();
            labelsGrid.setAlignment(Pos.CENTER);
            dividasFields.getChildren().add(labelsGrid);

            //Grid
            labelsGrid.add(tipoDividaLabel, 8, 0);
            labelsGrid.add(nmLabel, 24, 0);
            labelsGrid.add(vlLabel, 30, 0);
            labelsGrid.add(pclLabel, 41, 0);
            labelsGrid.add(dateLabel, 60, 0);
            labelsGrid.add(empty,80,0);
            labelsGrid.setHgap(5);

            labelsAdded = true;
        }

        HBox dividaRow = new HBox(10);
        dividaRow.setAlignment(Pos.CENTER);

        ComboBox<String> nichoCombo = new ComboBox<>();
        UIConfig.stringComboBox(nichoCombo);
        nichoCombo.getItems().addAll(divida.getNichos());
        nichoCombo.setPromptText("Ex: Pets");

        TextField nomeDividaField = new TextField();
        nomeDividaField.setMaxWidth(140);
        nomeDividaField.setMinWidth(140);
        nomeDividaField.setPromptText("Ex: Roupas");
        nomeDividaField.setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center;");

        TextField vlDivida = new TextField();
        UIConfig.configureTextField(vlDivida);
        vlDivida.setPromptText("0,00");

        ComboBox<Integer> parcelasCombo = new ComboBox<>();
        UIConfig.configureComboBox(parcelasCombo);
        parcelasCombo.getItems().addAll(divida.getParcelas());
        parcelasCombo.getSelectionModel().selectFirst();

        ComboBox<Integer> diaDividaCombo = new ComboBox<>();
        UIConfig.configureComboBox(diaDividaCombo);
        diaDividaCombo.setMaxWidth(80); // Define a largura máxima
        diaDividaCombo.setMinWidth(80); // Define a largura mínima
        diaDividaCombo.getItems().addAll(divida.getDiaDivida());
        diaDividaCombo.getSelectionModel().selectFirst();

        ComboBox<Integer> mesDividaCombo = new ComboBox<>();
        UIConfig.configureComboBox(mesDividaCombo);
        mesDividaCombo.setMaxWidth(80); // Define a largura máxima
        mesDividaCombo.setMinWidth(80); // Define a largura mínima
        mesDividaCombo.getItems().addAll(divida.getMesDivida());
        mesDividaCombo.getSelectionModel().selectFirst();

        // Botão Excluir
        Button excluirButton = getButton(dividaRow);

        dividaRow.getChildren().addAll(nichoCombo, nomeDividaField, vlDivida, parcelasCombo, diaDividaCombo, mesDividaCombo, excluirButton);
        dividasFields.getChildren().add(dividaRow);
        vlDivida.textProperty().addListener((obs, oldValue, newValue) -> updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao()));

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
        double total = 0.0;
        for (Node node : dividasFields.getChildren()) {
            if (node instanceof HBox hbox) {
                TextField valorField = (TextField) hbox.getChildren().get(2); // Supondo que o valor está na terceira posição
                try {
                    total += Double.parseDouble(valorField.getText().replace(",", "."));
                } catch (NumberFormatException e) {
                    // Ignora erros de formatação
                }
            }
        }
        return total;
    }

    private void updateTotalLabel(double total, boolean excedeLimite) {
        String mensagem = String.format("Total das Dívidas: %.2f", total);

        if (excedeLimite) {
            mensagem += " (Acima do limite!)";  // Adiciona a mensagem de aviso
            totalLabel.setTextFill(Color.YELLOW);   // Alerta se o limite for excedido
        } else {
            totalLabel.setTextFill(Color.WHITE);
        }

        totalLabel.setText(mensagem);
        totalLabel.setAlignment(Pos.CENTER);
        totalLabel.setMaxWidth(Double.MAX_VALUE);
        totalLabel.setBackground(null);
    }

    public Button faturaEVencimentos() {
        int vencimento = vencimentoSelecionado != null ? vencimentoSelecionado : 1;

        // Obtém o valor total da dívida e o número de parcelas selecionadas
        double valorTotal = 0.0;
        int parcelasSelecionadas = 1; // Definido por padrão; você pode obter isso a partir do UI

        for (Node node : dividasFields.getChildren()) {
            if (node instanceof HBox hbox) {
                TextField valorField = (TextField) hbox.getChildren().get(2);
                ComboBox<Integer> parcelasCombo = (ComboBox<Integer>) hbox.getChildren().get(3);

                try {
                    valorTotal += Double.parseDouble(valorField.getText().replace(",", "."));
                    parcelasSelecionadas = parcelasCombo.getValue();
                } catch (NumberFormatException ignored) {
                }
            }
        }

        // Calcula o valor de cada parcela
        double valorParcela = valorTotal / parcelasSelecionadas;
        int[] periodoFechamento = new int[7]; // Array para armazenar os 7 dias de fechamento

        for (int i = 0; i < 7; i++) {
            int diaFechamento = vencimento - (i + 1);
            if (diaFechamento < 1) {
                diaFechamento += 30; // Ajusta para o ciclo do mês
            }
            periodoFechamento[i] = diaFechamento;
        }

        System.out.print("Período de Fechamento: ");
        for (int j : periodoFechamento) {
            System.out.print(j + " ");
        }

        Button faturaButton = getButton();

        System.out.println();
        System.out.println("Valor Total: " + valorTotal);
        System.out.println("Parcelas Selecionadas: " + parcelasSelecionadas);
        System.out.println("Valor por Parcela: " + valorParcela);
        return faturaButton;
    }

    private static Button getButton() {
        Button faturaButton = new Button("Fatura do mês");
        faturaButton.setStyle(  "-fx-background-color: #E0A24C4D; " +
                "-fx-font-size: 26px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-border-color: transparent; " +
                "-fx-border-radius: 4px; " +
                "-fx-padding: 8 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0.5, 0, 2);"
        );

        faturaButton.setOnMouseEntered(event -> faturaButton.setStyle(
                "-fx-background-color: #89632F4D; " +
                        "-fx-font-size: 26px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-color: transparent; " +
                        "-fx-border-radius: 4px; " +
                        "-fx-padding: 8 16; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0.5, 0, 2);"
        ));

        // Reverter o estilo quando o mouse sair do botão
        faturaButton.setOnMouseExited(event -> faturaButton.setStyle(
                "-fx-background-color: #E0A24C4D; " +
                        "-fx-font-size: 26px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-color: transparent; " +
                        "-fx-border-radius: 4px; " +
                        "-fx-padding: 8 16; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0.5, 0, 2);"
        ));

        return faturaButton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
