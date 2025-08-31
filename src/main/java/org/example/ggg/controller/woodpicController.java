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

public class woodpicController {

    @FXML
    private VBox contentBox;

    @FXML
    private Button addWoodPicBtn;

    private HBox currentRow;
    private int imageCount = 0;

    private final String SAVE_FILE = "wood_image_paths.txt"; // File to save wood image paths
    private final int IMAGES_PER_ROW = 3; // Number of images per row
    private final double IMAGE_SPACING = 28.0; // Spacing between images (1cm in pixels)

    @FXML
    public void initialize() {
        currentRow = new HBox(IMAGE_SPACING); // Initialize the first row
        contentBox.getChildren().add(currentRow);
        loadImages(); // Load images from file at startup
    }

    @FXML
    public void addWoodPicAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = (Stage) addWoodPicBtn.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            addImage(selectedFile.toURI().toString(), selectedFile.getName(), true);
        }
    }

    private void addImage(String imagePath, String imageName, boolean save) {
        Image image = new Image(imagePath, 300, 300, true, true);
        ImageView imageView = new ImageView(image);

        TextField imageNameField = new TextField(imageName);
        imageNameField.setStyle("-fx-alignment: center;");
        imageNameField.setOnAction(e -> updateImageName(imagePath, imageNameField.getText()));

        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> {
            VBox imageContainer = (VBox) deleteBtn.getParent();
            HBox parentRow = (HBox) imageContainer.getParent();

            parentRow.getChildren().remove(imageContainer);

            if (parentRow.getChildren().isEmpty()) {
                contentBox.getChildren().remove(parentRow);
            }

            imageCount--;
            removeImagePath(imagePath);
        });

        VBox imageContainer = new VBox(5);
        imageContainer.getChildren().addAll(imageNameField, imageView, deleteBtn);
        imageContainer.setStyle("-fx-alignment: center;");

        currentRow.getChildren().add(imageContainer);
        imageCount++;

        if (imageCount == IMAGES_PER_ROW) {
            currentRow = new HBox(IMAGE_SPACING);
            contentBox.getChildren().add(currentRow);
            imageCount = 0;
        }

        if (save) {
            saveImagePath(imagePath);
        }
    }

    private void updateImageName(String imagePath, String newName) {
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
                    addImage(line, imageFile.getName(), false);
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
                lines.remove(imagePath);
                Files.write(file.toPath(), lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
