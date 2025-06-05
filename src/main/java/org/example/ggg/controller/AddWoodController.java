package org.example.ggg.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddWoodController {

    @FXML
    private ImageView woodImageView;

    @FXML
    private TextField woodNameField;

    private File selectedFile;

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            woodImageView.setImage(new Image(selectedFile.toURI().toString()));
        }
    }

    @FXML
    private void addWood() {
        if (selectedFile == null || woodNameField.getText().isEmpty()) {
            System.out.println("Please select an image and enter a name.");
            return;
        }

        String woodName = woodNameField.getText();
        System.out.println("Wood Added: " + woodName + " (" + selectedFile.getName() + ")");

        // Close the window after adding
        Stage stage = (Stage) woodNameField.getScene().getWindow();
        stage.close();
    }
}
