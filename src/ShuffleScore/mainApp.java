package ShuffleScore;

// Imported libraries

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*

    PROGRAM REQUIREMENTS - DELETE ONCE COMPLETE

    i.   Never clear screen. [~]
    ii.  Complete source code - Make sure the code can be compiled and run. []
    iii. Implement Comparable or Comparator interface for sorting. []
    iv.  Use different data structures : Stack / Queue & Set / Map []
    v.   JavaFX GUI for the whole program. []
    vi.  A file named Group10.txt - The file shall list down the group members’ ID, name, and contribution. [~]

*/


public class mainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("shuffleScore.fxml"));
        primaryStage.setTitle("WADDUP DAWGS!");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);

    }

}
