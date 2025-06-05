package org.example.ggg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;

public class tableController {

    @FXML
    private Button addtableBtn; // Button from FXML

    @FXML
    private ScrollPane scrollPane; // ScrollPane from FXML

    @FXML
    private VBox contentBox; // VBox from FXML

    private int tableCount = 0; // Total tables added
    private final int TABLES_PER_ROW = 3; // Max tables per row
    private final int IMAGE_SIZE = 370; // Fixed size for the table image

    @FXML
    public void initialize() {
        scrollPane.setFitToWidth(true); // Auto-fit width
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Always show vertical scrollbar
    }

    @FXML
    private void addtableAcction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            TextInputDialog nameDialog = new TextInputDialog("Table Name");
            nameDialog.setTitle("Enter Table Name");
            nameDialog.setHeaderText(null);
            nameDialog.setContentText("Please enter a name for the table:");

            Optional<String> nameResult = nameDialog.showAndWait();
            String tableName = nameResult.orElse("Unnamed Table");

            ImageView imageView = new ImageView();
            imageView.setFitHeight(IMAGE_SIZE);
            imageView.setFitWidth(IMAGE_SIZE);
            imageView.setPreserveRatio(true);
            imageView.setImage(new Image(selectedFile.toURI().toString()));

            VBox imageContainer = new VBox(5);
            imageContainer.setStyle("-fx-alignment: center; -fx-padding: 10;");
            Button tableNameButton = new Button(tableName);

            imageView.setOnMouseClicked(e -> confirmAndDeleteImage(imageContainer));

            imageContainer.getChildren().addAll(imageView, tableNameButton);

            if (tableCount % TABLES_PER_ROW == 0) {
                HBox newRow = new HBox(20);
                newRow.setStyle("-fx-alignment: center; -fx-padding: 10;");
                contentBox.getChildren().add(newRow);
            }

            HBox currentRow = (HBox) contentBox.getChildren().get(contentBox.getChildren().size() - 1);
            currentRow.getChildren().add(imageContainer);

            tableCount++;
        }
    }

    private void confirmAndDeleteImage(VBox imageContainer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Table Image");
        alert.setHeaderText("Are you sure you want to delete this table image?");
        alert.setContentText("Click OK to delete, or Cancel to keep the image.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            HBox parentRow = (HBox) imageContainer.getParent();
            parentRow.getChildren().remove(imageContainer);

            if (parentRow.getChildren().isEmpty()) {
                contentBox.getChildren().remove(parentRow);
            }

            tableCount--;
        }
    }
}
