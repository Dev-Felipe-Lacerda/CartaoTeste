import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Principal extends Application {
    private Card card;
    private Text statusText;
    private float totalDividas;

    @Override

    public void start(Stage primaryStage){
        primaryStage.setTitle("Cálculo do Cartão");

        //Inicializar classe
        card = new Card() {};

        //GridPane para layout
        GridPane mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(10));
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);
        mainGrid.setAlignment(Pos.CENTER);

        //Efeito do Fundo
        StackPane mainLayout = new StackPane(mainGrid);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setAlignment(Pos.CENTER);

        //Backgroud Degradê
        BackgroundFill backgroundFill = new BackgroundFill(
                new LinearGradient(
                        0,0,1,1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#A241A8")),
                        new Stop(0, Color.web("#65A538"))
                ),
                CornerRadii.EMPTY, Insets.EMPTY
        );
        mainLayout.setBackground(new Background(backgroundFill));
    }
}
