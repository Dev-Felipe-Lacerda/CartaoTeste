import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class UIConfig {

    public static class CustomLabel extends Label {

        public CustomLabel(String text) {
            super(text);
            // Configurações padrão para as labels
            setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        }
    }

    public static void configureComboBox(ComboBox<Integer> comboBox) {
        comboBox.setPrefWidth(115); // Define um tamanho preferido
        comboBox.setMaxWidth(115); // Define a largura máxima
        comboBox.setMinWidth(115); // Define a largura mínima
        comboBox.setStyle("-fx-background-color: #0C0812; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Configura o estilo dos itens dentro do ComboBox
        comboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.toString());
                            setTextFill(Color.WHITE); // Define a cor do texto como branca
                            setStyle("-fx-background-color: #0C0812; -fx-font-size: 16px; -fx-font-weight: bold;");
                        }
                    }
                };
            }
        });

        // Configura o estilo do item selecionado
        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setTextFill(Color.WHITE); // Define a cor do texto como branca
                    setStyle("-fx-background-color: #0C0812; -fx-font-size: 16px; -fx-font-weight: bold;");
                }
            }
        });

        // Adiciona o contorno difuso branco quando focado
        comboBox.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                comboBox.setStyle("-fx-background-color: #0C0812; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, white, 5, 0.01, 0, 0);");
            } else {
                comboBox.setStyle("-fx-background-color: #0C0812; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
            }
        });
    }

    public static void configureTextField(TextField textField) {
        textField.setTextFormatter(createNumberFormatter());
        textField.setPrefWidth(85); // Define um tamanho preferido
        textField.setMaxWidth(85); // Define a largura máxima
        textField.setMinWidth(85); // Define a largura mínima
        textField.setStyle("-fx-background-color: #0C0812; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center;");

        // Adiciona o contorno difuso branco quando focado
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                textField.setStyle("-fx-background-color: #0C0812; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center; -fx-effect: dropshadow(gaussian, white, 5, 0.01, 0, 0);");
            } else {
                textField.setStyle("-fx-background-color: #0C0812; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center;");
            }
        });
    }

    private static TextFormatter<String> createNumberFormatter() {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*([,.][0-9]*)?")) {
                return change;
            } else {
                return null;
            }
        });
    }
}
