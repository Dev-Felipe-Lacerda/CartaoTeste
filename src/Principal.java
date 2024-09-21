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

    private static final String BUTTON_STYLE = "-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-background-radius: 10; -fx-border-radius: 10; -fx-font-weight: bold; -fx-text-fill: white;";
    private static final String BUTTON_FOCUSED_STYLE = BUTTON_STYLE + "-fx-background-color: #0C0812F2; -fx-font-size: 16px;" +
            "-fx-font-weight: bold; -fx-background-radius: 10; -fx-border-radius: 10;-fx-effect: dropshadow(gaussian, #1AFFFFFF, 1, 1, 0, 0); ";

    @Override
    public void start(Stage primaryStage) {
        card = new Card() {};
        divida = new Divida() {};

        primaryStage.setTitle("Cartão");

        // Pane principal da lateral direita (resto da janela)
        rightVBox = new VBox(10);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.setPrefSize(900, 600);
        rightVBox.setStyle("-fx-background-color: #1A1A1A");
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
        leftPane.setPrefSize(220, 600);
        leftPane.setStyle("-fx-background-color: #1A1A1A");
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setSpacing(40);

        // Inicializa o totalLabel
        totalLabel = new Label("Total das Dívidas: 0.00");
        totalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        totalLabel.setAlignment(Pos.CENTER);
        totalLabel.setVisible(false);
        rightVBox.widthProperty().addListener((obs, oldWidth, newWidth) -> totalLabel.setMaxWidth((Double) newWidth));

        // Sombra branca
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#FFFFFF80"));
        shadow.setRadius(1);
        shadow.setSpread(1);

        // Declaração dos botões
        Button option1 = new Button("Cartão");
        UIConfig.configureButton(option1);
        Button option2 = new Button("Dívidas");
        UIConfig.configureButton(option2);
        Button option3 = new Button("Fatura");
        UIConfig.configureButton(option3);

        // Configurações para cada botão
        option1.setOnAction(e -> handleOption(createMainGrid(), shadow, option1, option2, option3));
        option2.setOnAction(e -> handleOption2(createAddButton(), shadow, option1, option2, option3));
        option3.setOnAction(e -> handleOption3(faturaEVencimentos(), shadow, option1, option2, option3));

        // Adiciona os botões na lateral esquerda
        leftPane.getChildren().addAll(option1, option2, option3);

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
        rightVBox.setBackground(Background.fill(Color.web("#1A1A1A")));

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
        rightVBox.setBackground(Background.fill(Color.web("#1A1A1A")));

        option1.setEffect(null);
        option2.setEffect(shadow);
        option3.setEffect(null);

        if (isFirstTime) {
            addDividaFields();
            isFirstTime = false;
        }
        totalLabel.setVisible(true);
    }


    private void handleOption3(List<Button> faturaButtons, DropShadow shadow, Button option1, Button option2, Button option3) {
        rightVBox.getChildren().clear();

        // Cria um VBox para organizar os botões de fatura
        VBox faturasVBox = new VBox(10); // 10 é o espaçamento entre os botões
        faturasVBox.setAlignment(Pos.CENTER);

        // Adiciona cada botão de fatura ao VBox
        for (Button faturaButton : faturaButtons) {
            faturaButton.maxWidthProperty().bind(rightVBox.widthProperty().subtract(10));
            faturasVBox.getChildren().add(faturaButton);
        }

        ScrollPane scrollPane = new ScrollPane(faturasVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        rightVBox.getChildren().add(scrollPane);
        rightVBox.setBackground(Background.fill(Color.web("#1A1A1A")));

        // Gerencia os efeitos visuais dos botões de opções
        option1.setEffect(null);
        option2.setEffect(null);
        option3.setEffect(shadow);
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
        nomeDividaField.setAlignment(Pos.CENTER);
        nomeDividaField.setStyle("-fx-background-color: #0C0812F2; "
                + "-fx-font-size: 16px; "
                + "-fx-font-weight: bold; "
                + "-fx-text-fill: white; "
                + "-fx-background-radius: 10; "
                + "-fx-alignment: center;");

        nomeDividaField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                nomeDividaField.setStyle("-fx-background-color: #0C0812F2; "
                        + "-fx-font-size: 16px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: white; "
                        + "-fx-background-radius: 10; "
                        + "-fx-effect: dropshadow(gaussian, #1AFFFFFF, 1, 1, 0, 0);");
            } else {
                nomeDividaField.setStyle("-fx-background-color: #0C0812F2; "
                        + "-fx-font-size: 16px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: white; "
                        + "-fx-background-radius: 10; "
                        + "-fx-alignment: center;");
            }
        });

    TextField vlDivida = new TextField();
        UIConfig.configureTextField(vlDivida);
        vlDivida.setPromptText("0,00");

        ComboBox<Integer> parcelasCombo = new ComboBox<>();
        UIConfig.configureComboBox(parcelasCombo);
        parcelasCombo.getItems().addAll(divida.getParcelas());
        parcelasCombo.setPromptText("Ex: 3x");

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
        excluirButton.setStyle("-fx-background-color:#921710 ; -fx-background-radius: 10; -fx-border-radius: 10; -fx-text-fill: white; -fx-font-weight: bold;");
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

    public List<Button> faturaEVencimentos() {
        int vencimento = vencimentoSelecionado != null ? vencimentoSelecionado : 1;
        Map<Integer, List<String>> mesesDividas = new TreeMap<>();
        double valorTotal = 0.0;
        List<Button> faturaButtons = new ArrayList<>();

        // Calcula o período de pagamento
        int[] periodoFechamento = new int[7];
        for (int i = 0; i < 7; i++) {
            int diaFechamento = vencimento - i;
            if (diaFechamento < 1) {
                diaFechamento = 30 + diaFechamento;
            }
            periodoFechamento[i] = diaFechamento;
        }

        int menorDiaFechamento = periodoFechamento[6];
        int maiorDiaFechamento = periodoFechamento[0];

        // Processa as dívidas
        for (Node node : dividasFields.getChildren()) {
            if (node instanceof HBox hbox) {
                ComboBox<String> nichoCombo = (ComboBox<String>) hbox.getChildren().get(0);
                TextField nomeField = (TextField) hbox.getChildren().get(1);
                TextField valorField = (TextField) hbox.getChildren().get(2);
                ComboBox<Integer> parcelasCombo = (ComboBox<Integer>) hbox.getChildren().get(3);
                ComboBox<Integer> diaDividaCombo = (ComboBox<Integer>) hbox.getChildren().get(4);
                ComboBox<Integer> mesDividaCombo = (ComboBox<Integer>) hbox.getChildren().get(5);

                try {
                    String nomeDivida = nomeField.getText();
                    double valor = Double.parseDouble(valorField.getText().replace(",", "."));
                    int parcelas = parcelasCombo.getValue();
                    int diaDivida = diaDividaCombo.getValue();
                    int mesDivida = mesDividaCombo.getValue();

                    double valorParcela = valor / parcelas;
                    boolean dentroPeriodoPagamento = diaDivida >= menorDiaFechamento && diaDivida <= maiorDiaFechamento;

                    if (dentroPeriodoPagamento) {
                        mesDivida = (mesDivida % 12) + 1;  // Se estiver no período de pagamento, muda para o próximo mês
                    }

                    for (int i = 0; i < parcelas; i++) {
                        int mesAtual = (mesDivida + i - 1) % 12 + 1;
                        int parcelaAtual = i + 1;

                        String infoDivida = String.format(
                                "Dívida: %s, Valor da Parcela: %.2f, Parcela Atual: %d/%d, Data da Compra: %d/%d",
                                nomeDivida, valorParcela, parcelaAtual, parcelas, diaDivida, mesDivida
                        );

                        // Agrupa as dívidas por mês
                        mesesDividas.computeIfAbsent(mesAtual, k -> new ArrayList<>()).add(infoDivida);
                    }

                    valorTotal += valor;

                } catch (NumberFormatException ignored) {}
            }
        }

        // Cria botões de fatura para cada mês distinto
        for (Map.Entry<Integer, List<String>> entry : mesesDividas.entrySet()) {
            int mesDivida = entry.getKey();
            List<String> dividas = entry.getValue();
            double totalFaturaMes = dividas.stream()
                    .mapToDouble(d -> Double.parseDouble(d.split(", Valor da Parcela: ")[1].split(",")[0]))
                    .sum();

            // Texto do botão
            String textoBotao = String.format(
                    "Fatura do mês %d: %.2f | Período de Pagamento: %d até %d",
                    mesDivida, totalFaturaMes, menorDiaFechamento, maiorDiaFechamento
            );

            // Configurações do botão
            Button faturaButton = getButton(textoBotao);
            faturaButtons.add(faturaButton);
        }
        return faturaButtons;
    }

    private static Button getButton(String textoBotao) {
        Button faturaButton = new Button(textoBotao);
        faturaButton.setStyle(
                "-fx-background-color: #E0A24C4D; " +
                        "-fx-font-size: 26px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-color: transparent; " +
                        "-fx-border-radius: 4px; " +
                        "-fx-padding: 8 16; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0.5, 0, 2);"
        );

        boolean[] isExpanded = {false};

        faturaButton.setOnAction(event -> {
            if (isExpanded[0]) {
                faturaButton.setPrefHeight(faturaButton.getHeight() / 1.5);
                faturaButton.setText(textoBotao); // Retorna ao texto original
            } else {
                faturaButton.setPrefHeight(faturaButton.getHeight() * 1.5);
                faturaButton.setText("Expandido"); // Texto alterado quando expandido
            }
            isExpanded[0] = !isExpanded[0]; // Inverte o estado
        });
        return faturaButton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}