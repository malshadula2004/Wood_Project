package org.example.ggg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class tableController {

    @FXML
    private VBox contentBox;

    @FXML
    private Button addTableBtn;

    private HBox currentRow;
    private int imageCount = 0;

    private final String SAVE_FILE = "table_image_paths.txt"; // File to save table image paths
    private final int IMAGES_PER_ROW = 3;
    private final double IMAGE_SPACING = 28.0; // Approximately 1 cm in pixels

    @FXML
    public void initialize() {
        currentRow = new HBox(IMAGE_SPACING); // Initialize first row with spacing
        contentBox.getChildren().add(currentRow);
        loadImages(); // Load images from file on startup
    }

    @FXML
    public void addTableAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = (Stage) addTableBtn.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            addImage(selectedFile.toURI().toString(), selectedFile.getName(), true);
        }
    }

    private void addImage(String imagePath, String imageName, boolean save) {
        // Create ImageView
        Image image = new Image(imagePath, 300, 300, true, true);
        ImageView imageView = new ImageView(image);

        // Renameable TextField for Image Name
        TextField imageNameField = new TextField(imageName);
        imageNameField.setStyle("-fx-alignment: center;");
        imageNameField.setOnAction(e -> updateImageName(imagePath, imageNameField.getText()));

        // Delete Button
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> {
            VBox imageContainer = (VBox) deleteBtn.getParent();
            HBox parentRow = (HBox) imageContainer.getParent();

            // Remove the image container from the current row
            parentRow.getChildren().remove(imageContainer);

            // If the row is empty, remove it from the VBox
            if (parentRow.getChildren().isEmpty()) {
                contentBox.getChildren().remove(parentRow);
            }

            // Update image count
            imageCount--;

            // Remove image path from storage
            removeImagePath(imagePath);
        });

        // Image container
        VBox imageContainer = new VBox(5);
        imageContainer.getChildren().addAll(imageNameField, imageView, deleteBtn);

        // Center align the container
        imageContainer.setStyle("-fx-alignment: center;");

        // Add to current row
        currentRow.getChildren().add(imageContainer);
        imageCount++;

        // Create new row if necessary
        if (imageCount == IMAGES_PER_ROW) {
            currentRow = new HBox(IMAGE_SPACING);
            contentBox.getChildren().add(currentRow);
            imageCount = 0;
        }

        // Save image path
        if (save) {
            saveImagePath(imagePath);
        }
    }

    private void updateImageName(String imagePath, String newName) {
        // Placeholder: You can implement saving the new name in a more robust way if needed.
        System.out.println("Updated name for " + imagePath + " to " + newName);
    }

    private void saveImagePath(String imagePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE, true))) {
            writer.write(imagePath);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImages() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    File imageFile = new File(line);
                    addImage(line, imageFile.getName(), false); // Load images with name
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeImagePath(String imagePath) {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            try {
                List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));
                lines.remove(imagePath); // Remove the specific image path
                Files.write(file.toPath(), lines); // Write updated paths back to file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
