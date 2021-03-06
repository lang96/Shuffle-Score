package ShuffleScore;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class test extends Application {
    private final static int CARDS_PER_ROW = 3;
    private Image[] images = new Image[6]; // 6 for example
    private ImageView[] imageViewsRow1 = new ImageView[CARDS_PER_ROW];
    private ImageView[] imageViewsRow2 = new ImageView[CARDS_PER_ROW];

    @Override // Override the init method in the Application class
    public void init() {
        // Initialize the data fields
        // Create Images objects for the card pngs
        images[0] = new Image("../../res/cards/AC.png", 50, 0, true, true);
        images[1] = new Image("../../res/cards/2C.png", 50, 0, true, true);
        images[2] = new Image("../../res/cards/3C.png", 50, 0, true, true);
        images[3] = new Image("../../res/cards/JD.png", 50, 0, true, true);
        images[4] = new Image("../../res/cards/QD.png", 50, 0, true, true);
        images[5] = new Image("../../res/cards/KD.png", 50, 0, true, true);

        // Create ImageView objects for displaying images
        for (int i = 0; i < CARDS_PER_ROW; i++) {
            imageViewsRow1[i] = new ImageView();
            imageViewsRow2[i] = new ImageView();
        }
    }

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {

        imageViewsRow1[0].setImage (images[0]);
        imageViewsRow1[1].setImage (images[1]);
        imageViewsRow1[2].setImage (images[2]);

        imageViewsRow2[0].setImage (images[3]);
        imageViewsRow2[1].setImage (images[4]);
        imageViewsRow2[2].setImage (images[5]);

        // Create a horizontal box for row 1
        HBox hBoxRow1 = new HBox();
        hBoxRow1.getChildren().addAll(imageViewsRow1);

        // Create a horizontal box for row 2
        HBox hBoxRow2 = new HBox();
        hBoxRow2.setAlignment(Pos.CENTER);
        hBoxRow2.getChildren().addAll(imageViewsRow2);

        // Create a horizontal box for row 3
        HBox hBoxRow3 = new HBox();
        Button btShowCard = new Button("Show Cards");
        Label lblStatus = new Label();
        hBoxRow3.getChildren().addAll(btShowCard, lblStatus);

        // Create a vertical box for arranging the 3 rows
        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(hBoxRow1, hBoxRow2, hBoxRow3);

        // Create a scene and place it in the stage
        Scene scene = new Scene(vBox, 900, 600);
        primaryStage.setTitle("Card Demo"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        btShowCard.setOnAction(e -> lblStatus.setText("Hello world"));
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */

    public static void main(String[] args) {
        launch(args);
    }
}