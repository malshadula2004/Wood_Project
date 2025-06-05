package org.example.ggg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/ggg/view/WoodOrder.fxml"));

        // Set up the scene and stage
        Scene scene = new Scene(root, 900, 400); // Width: 900, Height: 400
        primaryStage.setScene(scene);
        primaryStage.setTitle("Wood Hub");
        primaryStage.show();
    }
}
