package org.example.ggg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;

public class WoodPicController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox contentBox; // Main container for rows

    private int imageCount = 0; // Total images added
    private final int IMAGES_PER_ROW = 3; // Max images per row
    private final int IMAGE_SIZE = 370; // Fixed image size

    @FXML
    public void initialize() {
        scrollPane.setFitToWidth(true); // Automatically fit width to scroll pane
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Enable vertical scrolling
    }

    @FXML
    private void addWoodAcction(ActionEvent event) {
        // Open a FileChooser to select an image
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Prompt the user to enter a name for the image
            TextInputDialog nameDialog = new TextInputDialog("Wood Name");
            nameDialog.setTitle("Enter Wood Name");
            nameDialog.setHeaderText(null);
            nameDialog.setContentText("Please enter a name for the wood:");

            Optional<String> nameResult = nameDialog.showAndWait();
            String woodName = nameResult.orElse("Unnamed Wood");

            // Create ImageView for the selected image
            ImageView imageView = new ImageView();
            imageView.setFitHeight(IMAGE_SIZE); // Fixed height
            imageView.setFitWidth(IMAGE_SIZE); // Fixed width
            imageView.setPreserveRatio(true);
            imageView.setImage(new Image(selectedFile.toURI().toString()));

            // Create a VBox for the image and its name
            VBox imageContainer = new VBox(5); // Space between image and name
            imageContainer.setStyle("-fx-alignment: center; -fx-padding: 10;");
            Button woodNameButton = new Button(woodName);

            // Add Click Event for Deletion
            imageView.setOnMouseClicked(e -> confirmAndDeleteImage(imageContainer));

            imageContainer.getChildren().addAll(imageView, woodNameButton);

            // Add to the current row or create a new row if needed
            if (imageCount % IMAGES_PER_ROW == 0) {
                // Create a new row (HBox) if the current row is full
                HBox newRow = new HBox(20); // Space between images in a row
                newRow.setStyle("-fx-alignment: center; -fx-padding: 10;");
                contentBox.getChildren().add(newRow);
            }

            // Add the image container to the latest row
            HBox currentRow = (HBox) contentBox.getChildren().get(contentBox.getChildren().size() - 1);
            currentRow.getChildren().add(imageContainer);

            imageCount++;
        }
    }

    private void confirmAndDeleteImage(VBox imageContainer) {
        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Image");
        alert.setHeaderText("Are you sure you want to delete this image?");
        alert.setContentText("Click OK to delete, or Cancel to keep the image.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Remove the image container from its parent row
            HBox parentRow = (HBox) imageContainer.getParent();
            parentRow.getChildren().remove(imageContainer);

            // Check if the parent row is empty
            if (parentRow.getChildren().isEmpty()) {
                // Remove the empty row from the content box
                contentBox.getChildren().remove(parentRow);
            }

            // Decrease image count
            imageCount--;
        }
    }
}
