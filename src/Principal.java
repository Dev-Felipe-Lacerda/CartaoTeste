import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class Principal extends Application {
    private Card card;
    private float totalDividas;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cálculo do Cartão");

        // Inicializar classe
        card = new Card() {};

        // GridPane para layout
        GridPane mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(10));
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);
        mainGrid.setAlignment(Pos.CENTER);

        // Efeito do Fundo
        StackPane mainLayout = new StackPane(mainGrid);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setAlignment(Pos.CENTER);

        // Background Degradê com três cores
        BackgroundFill backgroundFill = new BackgroundFill(
                new LinearGradient(
                        0, 0, 1, 1, // Posição do gradiente (do canto superior esquerdo ao inferior direito)
                        true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#5E48A3")), // Cor inicial
                        new Stop(0.5, Color.web("#7B47A3")), // Cor inicial
                        new Stop(1, Color.web("#A3487E")) // Cor final
                ),
                CornerRadii.EMPTY, Insets.EMPTY
        );
        //Campo de texto para Limite:
        UIConfig.CustomLabel limiteCustom = new UIConfig.CustomLabel("Limite do cartão: ");
        TextField limiteField = new TextField("Limite");
        UIConfig.configureTextField(limiteField);
        card.setLimiteCartao(Integer.getInteger(String.valueOf(limiteField)));
        GridPane.setHgrow(limiteField, Priority.NEVER);

        mainLayout.setBackground(new Background(backgroundFill));
        Scene scene = new Scene(mainLayout, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Inicia a aplicação JavaFX
    }
}
