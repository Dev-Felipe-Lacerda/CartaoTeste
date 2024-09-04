import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
                        new Stop(0, Color.web("#A241A8")), // Cor inicial
                        new Stop(0.4, Color.web("#BD6EC1")), // Cor intermediária
                        new Stop(0.8, Color.web("#66DA14")), // Cor intermediária
                        new Stop(1, Color.web("#65A538")) // Cor final65A538
                ),
                CornerRadii.EMPTY, Insets.EMPTY
        );
        mainLayout.setBackground(new Background(backgroundFill));

        Scene scene = new Scene(mainLayout, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Inicia a aplicação JavaFX
    }
}
