import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.util.*;

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
    private int rowcount=0;

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
        rightVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #8A2BE2, #FF69B4);");
        rightVBox.setPadding(Insets.EMPTY);

        // Inicializa dividasFields
        dividasFields = new VBox(10);
        dividasFields.setPadding(new Insets(10));
        dividasFields.setAlignment(Pos.CENTER);

        // Label que será atualizada com base na opção selecionada
        Label label = new Label("Informe seus dados da fatura!");
        label.setStyle("-fx-font-size: 38px;-fx-font-weight: bold; -fx-text-fill: white;");
        rightVBox.getChildren().add(label);

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
        totalLabel.setVisible(false);
        rightVBox.widthProperty().addListener((obs, oldWidth, newWidth) -> totalLabel.setMaxWidth((Double) newWidth));

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

        VBox contentVBox = new VBox(10);
        contentVBox.getChildren().addAll(dividasAndButtonVBox, totalLabelVBox);

        // ScrollPane transparente
        ScrollPane scrollPane = new ScrollPane(contentVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        rightVBox.getChildren().add(scrollPane);

        // Aplicando o background
        LinearGradient gradient = new LinearGradient(
                1, 0, 0, 1,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#4AAC90")),
                new Stop(1, Color.web("#31725F"))
        );
        BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
        rightVBox.setBackground(new Background(backgroundFill));

        option1.setEffect(null);
        option2.setEffect(shadow);
        option3.setEffect(null);

        if (isFirstTime) {
            addDividaFields();
            isFirstTime = false;
        }
        totalLabel.setVisible(true);
    }

    private void handleOption3(Button faturaButton, DropShadow shadow, Button option1, Button option2, Button option3) {
        rightVBox.getChildren().clear();
        HBox faturasHBox = new HBox();
        faturasHBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(faturaButton, Priority.ALWAYS);
        faturasHBox.getChildren().add(faturaButton);

        VBox contentVBox = new VBox(10);
        contentVBox.getChildren().add(faturasHBox);

        ScrollPane scrollPane = new ScrollPane(contentVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        rightVBox.getChildren().add(scrollPane);

        LinearGradient gradient = new LinearGradient(
                1, 0, 0, 1,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#E0A24C")),
                new Stop(1, Color.web("#89632F"))
        );
        BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
        rightVBox.setBackground(new Background(backgroundFill));

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
        vencimentoCombo.getItems().addAll(divida.getDiaDivida());
        vencimentoCombo.setPromptText("Dia");
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
        rowcount++;
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
        diaDividaCombo.setMaxWidth(90);
        diaDividaCombo.setMinWidth(90);
        diaDividaCombo.getItems().addAll(divida.getDiaDivida());
        diaDividaCombo.setPromptText("Dia");

        ComboBox<Integer> mesDividaCombo = new ComboBox<>();
        UIConfig.configureComboBox(mesDividaCombo);
        mesDividaCombo.setMaxWidth(90);
        mesDividaCombo.setMinWidth(90);
        mesDividaCombo.getItems().addAll(divida.getMesDivida());
        mesDividaCombo.setPromptText("Mês");

        Button excluirButton = getButton(dividaRow);

        dividaRow.getChildren().addAll(nichoCombo, nomeDividaField, vlDivida, parcelasCombo, diaDividaCombo, mesDividaCombo, excluirButton);
        dividasFields.getChildren().add(dividaRow);
        vlDivida.textProperty().addListener((obs, oldValue, newValue) -> updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao()));
        if (rowcount == 0 && labelsAdded) {
            for (Node node : dividasFields.getChildren()) {
                if (node instanceof GridPane) {
                    dividasFields.getChildren().remove(node);
                    labelsAdded = false;
                    break;
                }
            }
        }
    }

    private Button getButton(HBox dividaRow) {
        Button excluirButton = new Button("Excluir");
        excluirButton.setStyle("-fx-background-color:#921710 ; -fx-text-fill: white; -fx-font-weight: bold;");
        excluirButton.setMinHeight(35);
        excluirButton.setOnAction(e -> {
            rowcount--;
            dividasFields.getChildren().remove(dividaRow);
            updateTotalLabel(calculateTotalDividas(), calculateTotalDividas() > card.getLimiteCartao());
            if (rowcount == 0 && labelsAdded) {
                // Remove o GridPane dos labels
                for (Node node : dividasFields.getChildren()) {
                    if (node instanceof GridPane) {
                        dividasFields.getChildren().remove(node);
                        labelsAdded = false;
                        break;
                    }
                }
            }
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
        totalLabel.setTextFill(Color.WHITE);

        if (excedeLimite) {
            double diferencaDouble = total - card.getLimiteCartao();
            mensagem += String.format(" | Limite excedido em: %.2f", diferencaDouble);
            totalLabel.setTextFill(Color.YELLOW);
        }

        totalLabel.setText(mensagem);
        totalLabel.setAlignment(Pos.CENTER);
        totalLabel.setMaxWidth(Double.MAX_VALUE);
        totalLabel.setBackground(null);
    }

    public Button faturaEVencimentos() {
        int vencimento = vencimentoSelecionado != null ? vencimentoSelecionado : 1;

        // Armazena os meses e as informações de cada dívida
        Map<Integer, List<String>> mesesDividas = new TreeMap<>();
        double valorTotal = 0.0;
        List<Integer> periodoFechamentoList = new ArrayList<>();

        // Calcula o período de pagamento
        int[] periodoFechamento = new int[7]; // Array para armazenar os 7 dias de fechamento
        for (int i = 0; i < 7; i++) {
            int diaFechamento = vencimento - i; //Para calcular dias anteriores ao vencimento
            if (diaFechamento < 1) {
                // Ajusta se o dia ficar menor que 1
                diaFechamento = 30 + diaFechamento;
            }
            periodoFechamento[i] = diaFechamento;
        }

        // Encontrar o menor e maior valor do período de fechamento
        int menorDiaFechamento = periodoFechamento[6];
        int maiorDiaFechamento = periodoFechamento[0];

        for (Node node : dividasFields.getChildren()) {
            if (node instanceof HBox hbox) {
                ComboBox<String> nichoCombo = (ComboBox<String>) hbox.getChildren().get(0);
                TextField nomeField = (TextField) hbox.getChildren().get(1);
                TextField valorField = (TextField) hbox.getChildren().get(2);
                ComboBox<Integer> parcelasCombo = (ComboBox<Integer>) hbox.getChildren().get(3);
                ComboBox<Integer> diaDividaCombo = (ComboBox<Integer>) hbox.getChildren().get(4);
                ComboBox<Integer> mesDividaCombo = (ComboBox<Integer>) hbox.getChildren().get(5);

                try {
                    String tipoDivida = nichoCombo.getValue();
                    String nomeDivida = nomeField.getText();
                    double valor = Double.parseDouble(valorField.getText().replace(",", "."));
                    int parcelas = parcelasCombo.getValue();
                    int diaDivida = diaDividaCombo.getValue();
                    int mesDivida = mesDividaCombo.getValue();

                    double valorParcela = valor / parcelas;

                    // Verificação se -> Compra está dentro do período de pagamento
                    boolean dentroPeriodoPagamento = diaDivida >= menorDiaFechamento && diaDivida <= maiorDiaFechamento;

                    // Ajusta o mês inicial da dívida se estiver dentro do período de pagamento
                    if (dentroPeriodoPagamento) {
                        mesDivida = (mesDivida % 12) + 1; // Move para o próximo mês
                    }

                    // Caso esteja, então mesDivida+++
                    for (int i = 0; i < parcelas; i++) {
                        int mesAtual = (mesDivida + i - 1) % 12 + 1;
                        int parcelaAtual = i + 1;

                        String infoDivida = String.format(
                                "Dívida: %s, Tipo: %s, Valor: %.2f, Parcelas: %d, Parcela Atual: %d/%d, Data da Compra: %d/%d",
                                nomeDivida, tipoDivida, valorParcela, parcelas, parcelaAtual, parcelas, diaDivida, mesDivida
                        );

                        // Adiciona a dívida ao mês correspondente
                        mesesDividas.computeIfAbsent(mesAtual, k -> new ArrayList<>()).add(infoDivida);
                    }

                    valorTotal += valor;
                    periodoFechamentoList.add(vencimento);

                } catch (NumberFormatException ignored) {
                    // Ignora dívidas com valores incorretos
                }
            }
        }

        // Exibe os detalhes de cada dívida
        System.out.println("Faturas por Mês:");
        for (Map.Entry<Integer, List<String>> entry : mesesDividas.entrySet()) {
            int mes = entry.getKey();
            List<String> dividas = entry.getValue();
            double totalFaturaMes = dividas.stream()
                    .mapToDouble(d -> Double.parseDouble(d.split(", Valor: ")[1].split(",")[0]))
                    .sum();

            System.out.println("Mês " + mes + ":");
            for (String divida : dividas) {
                System.out.println("  " + divida);
            }
            System.out.println("  Valor Total da Fatura: " + String.format("%.2f", totalFaturaMes));
        }

        // Imprime o período de fechamento e outras informações
        System.out.print("Período de Fechamento: ");
        for (int j : periodoFechamento) {
            System.out.print(j + " ");
        }

        System.out.println();
        System.out.println("Período de Pagamento: do dia " + menorDiaFechamento + " até " + maiorDiaFechamento);
        System.out.println("Valor Total: " + String.format("%.2f", valorTotal));
        System.out.println("Meses Distintos: " + mesesDividas.size());
        System.out.println("Total de Meses: " + mesesDividas.size());
        System.out.println("Valor por Parcela: " + String.format("%.2f", (valorTotal / mesesDividas.size())));

        return getButton();
    }

    private Button getButton() {
        Button faturaButton = new Button("Fatura do mês");
        boolean[] isExpanded = {false};

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

        faturaButton.setOnAction(event -> {
            if (isExpanded[0]) {
                faturaButton.setPrefHeight(faturaButton.getHeight() / 1.5);
                faturaButton.setText("Fatura do mês");
            }
            if (!isExpanded[0]) {
                faturaButton.setPrefHeight(faturaButton.getHeight() * 1.5);
                faturaButton.setText("Expandido");
            }
            isExpanded[0] = !isExpanded[0];
        });

        return faturaButton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
