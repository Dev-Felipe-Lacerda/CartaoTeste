import javafx.geometry.Pos;
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
        comboBox.setStyle("-fx-background-color: #0C0812F2; "
                + "-fx-font-size: 16px; "
                + "-fx-font-weight: bold; "
                + "-fx-text-fill: white; "
                + "-fx-background-radius: 10; "
                + "-fx-border-radius: 10;");

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
                            setStyle("-fx-background-color: #1C1C1C; "
                                    + "-fx-font-size: 16px; "
                                    + "-fx-font-weight: bold; "
                                    + "-fx-border-radius: 10;");
                            setOnMouseEntered(event -> setStyle("-fx-background-color: #E0E0E0; "
                                    + "-fx-font-size: 16px; "
                                    + "-fx-font-weight: bold; "
                                    + "-fx-text-fill: black; "
                                    + "-fx-border-radius: 10;"));
                            setOnMouseExited(event -> setStyle("-fx-background-color: #1C1C1C; "
                                    + "-fx-font-size: 16px; "
                                    + "-fx-font-weight: bold; "
                                    + "-fx-border-radius: 10;"));
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
                    setStyle("-fx-background-color: #0C0812F2; "
                            + "-fx-font-size: 16px; "
                            + "-fx-font-weight: bold; "
                            + "-fx-background-radius: 10; "
                            + "-fx-border-radius: 10;");
                }
            }
        });

        comboBox.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                comboBox.setStyle("-fx-background-color: #0C0812F2; "
                        + "-fx-font-size: 16px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: black; "
                        + "-fx-effect: dropshadow(gaussian, #1AFFFFFF, 1, 1, 0, 0); "
                        + "-fx-background-radius: 10; "
                        + "-fx-border-radius: 10;");
            } else {
                comboBox.setStyle("-fx-background-color: #0C0812F2; "
                        + "-fx-font-size: 16px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: black; "
                        + "-fx-background-radius: 10; "
                        + "-fx-border-radius: 10;");
            }
        });
    }

    public static void stringComboBox(ComboBox<String> comboString) {
        comboString.setPrefWidth(175);
        comboString.setMaxWidth(175);
        comboString.setMinWidth(175);
        comboString.setStyle("-fx-background-color: #0C0812F2; "
                + "-fx-font-size: 16px; "
                + "-fx-font-weight: bold; "
                + "-fx-text-fill: black; "
                + "-fx-background-radius: 10; "
                + "-fx-border-radius: 10;");

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
                            setStyle("-fx-background-color: #1C1C1C; "
                                    + "-fx-font-size: 16px; "
                                    + "-fx-font-weight: bold; "
                                    + "-fx-border-radius: 10;");
                            setOnMouseEntered(event -> setStyle("-fx-background-color: #E0E0E0; "
                                    + "-fx-font-size: 16px; "
                                    + "-fx-font-weight: bold; "
                                    + "-fx-text-fill: black; "
                                    + "-fx-border-radius: 10;"));
                            setOnMouseExited(event -> setStyle("-fx-background-color: #1C1C1C; "
                                    + "-fx-font-size: 16px; "
                                    + "-fx-font-weight: bold; "
                                    + "-fx-border-radius: 10;"));
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
                    setStyle("-fx-background-color: #0C0812F2; "
                            + "-fx-font-size: 16px; "
                            + "-fx-font-weight: bold; "
                            + "-fx-background-radius: 10; "
                            + "-fx-border-radius: 10;");
                }
            }
        });

        comboString.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                comboString.setStyle("-fx-background-color: #0C0812F2; "
                        + "-fx-font-size: 16px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: black; "
                        + "-fx-effect: dropshadow(gaussian, #1AFFFFFF, 1, 1, 0, 0); "
                        + "-fx-background-radius: 10; "
                        + "-fx-border-radius: 10;");
            } else {
                comboString.setStyle("-fx-background-color: #0C0812F2; "
                        + "-fx-font-size: 16px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: black; "
                        + "-fx-background-radius: 10; "
                        + "-fx-border-radius: 10;");
            }
        });
    }

    public static void configureTextField(TextField textField) {
        textField.setTextFormatter(createNumberFormatter());
        textField.setPrefWidth(85);
        textField.setMaxWidth(85);
        textField.setMinWidth(85);
        textField.setStyle("-fx-background-color: #0C0812F2; "
                + "-fx-font-size: 16px; "
                + "-fx-font-weight: bold; "
                + "-fx-text-fill: white; "
                + "-fx-background-radius: 10; "
                + "-fx-alignment: center;");

        textField.setAlignment(Pos.CENTER);

        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                textField.setStyle("-fx-background-color: #0C0812F2; "
                        + "-fx-font-size: 16px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: white; "
                        + "-fx-background-radius: 10; "
                        + "-fx-effect: dropshadow(gaussian, #1AFFFFFF, 1, 1, 0, 0);");
            } else {
                textField.setStyle("-fx-background-color: #0C0812F2; "
                        + "-fx-font-size: 16px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-text-fill: white; "
                        + "-fx-background-radius: 10; "
                        + "-fx-alignment: center;");
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
    public static void configureButton(Button bttn){
        bttn.setMinWidth(140);
        bttn.setMinHeight(60);
        bttn.setMaxWidth(140);
        bttn.setMaxHeight(60);
        bttn.setStyle("-fx-background-color: #0000001A; "
                + "-fx-text-fill: white; "
                + "-fx-font-size: 22px; "
                + "-fx-font-weight: bold; "
                + "-fx-background-radius: 30; "
                + "-fx-border-radius: 30;");
    }
}
