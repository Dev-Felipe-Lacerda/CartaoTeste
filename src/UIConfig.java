import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class UIConfig {

    public static class CustomLabel extends Label {
        public CustomLabel(String text) {
            super(text);
            setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        }
    }

    public static void configureComboBox(ComboBox<Integer> comboBox) {
        comboBox.setPrefWidth(115);
        comboBox.setMaxWidth(115);
        comboBox.setMinWidth(115);
        comboBox.setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        comboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.toString());
                            setTextFill(Color.WHITE);
                            setStyle("-fx-background-color: #1C1C1C; -fx-font-size: 16px; -fx-font-weight: bold;");
                            setOnMouseEntered(event -> setStyle("-fx-background-color: #E0E0E0; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;"));
                            setOnMouseExited(event -> setStyle("-fx-background-color: #1C1C1C; -fx-font-size: 16px; -fx-font-weight: bold;"));
                        }
                    }
                };
            }
        });

        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    setTextFill(Color.WHITE);
                    setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold;");
                }
            }
        });

        comboBox.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                comboBox.setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black; -fx-effect: dropshadow(gaussian, white, 1, 0.01, 0, 0);");
            } else {
                comboBox.setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
            }
        });
    }

    public static void stringComboBox(ComboBox<String> comboString) {
        comboString.setPrefWidth(175);
        comboString.setMaxWidth(175);
        comboString.setMinWidth(175);
        comboString.setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
        comboString.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            setTextFill(Color.WHITE);
                            setStyle("-fx-background-color: #1C1C1C; -fx-font-size: 16px; -fx-font-weight: bold;");
                            setOnMouseEntered(event -> setStyle("-fx-background-color: #E0E0E0; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;"));
                            setOnMouseExited(event -> setStyle("-fx-background-color: #1C1C1C; -fx-font-size: 16px; -fx-font-weight: bold;"));
                        }
                    }
                };
            }
        });

        comboString.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setTextFill(Color.WHITE);
                    setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold;");
                }
            }
        });

        comboString.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                comboString.setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black; -fx-effect: dropshadow(gaussian, white, 1, 0.01, 0, 0);");
            } else {
                comboString.setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
            }
        });
    }

    public static void configureTextField(TextField textField) {
        textField.setTextFormatter(createNumberFormatter());
        textField.setPrefWidth(85);
        textField.setMaxWidth(85);
        textField.setMinWidth(85);
        textField.setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center;");

        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                textField.setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center; -fx-effect: dropshadow(gaussian, white, 1, 0.01, 0, 0);");
            } else {
                textField.setStyle("-fx-background-color: #0C0812F2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center;");
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
