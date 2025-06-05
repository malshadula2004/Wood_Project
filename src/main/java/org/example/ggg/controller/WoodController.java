package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.dto.WoodDto;
import org.example.ggg.model.WoodModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class WoodController {

    @FXML
    private TextField txtSearch, txtId, txtType, txtGrade, txtDimensions, txtUnitPrice;

    @FXML
    private TableView<WoodDto> tblWood;

    @FXML
    private TableColumn<WoodDto, String> colWoodID, colWoodType, colWoodGrade, colWoodDimensions;

    @FXML
    private TableColumn<WoodDto, String> colWoodUnitPrice;

    @FXML
    private Button btnSearch, btnAdd, btnUpdate, btnDelete, btnReset;

    @FXML
    private AnchorPane woodPage;

    private ObservableList<WoodDto> woodList = FXCollections.observableArrayList();

    public void initialize() {
        colWoodID.setCellValueFactory(new PropertyValueFactory<>("woodId"));
        colWoodType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colWoodGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colWoodDimensions.setCellValueFactory(new PropertyValueFactory<>("dimensions"));
        colWoodUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        resetPage();

        tblWood.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
            }
        });
    }

    @FXML
    private void searchWood() {
        String searchKey = txtSearch.getText().trim();
        try {
            ArrayList<WoodDto> results = WoodModel.searchWood(searchKey);
            woodList.clear();
            woodList.addAll(results);
            tblWood.setItems(woodList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Search Error", "Failed to search wood: " + e.getMessage());
        }
    }


    @FXML
    private void addWood() {
        try {
            validateFields();

            WoodDto wood = new WoodDto(
                    null, // WoodID will be auto-generated
                    txtType.getText(),
                    txtGrade.getText(),
                    txtDimensions.getText(),
                    txtUnitPrice.getText()
            );
            String result = WoodModel.saveWood(wood);
            showAlert("Add Wood", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to add wood: " + e.getMessage());
        }
    }

    @FXML
    private void updateWood() {
        try {
            validateFields();

            WoodDto wood = new WoodDto(
                    txtId.getText(),
                    txtType.getText(),
                    txtGrade.getText(),
                    txtDimensions.getText(),
                    txtUnitPrice.getText()
            );
            String result = WoodModel.updateWood(wood);
            showAlert("Update Wood", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to update wood: " + e.getMessage());
        }
    }

    @FXML
    private void deleteWood() {
        String woodId = txtId.getText().trim();
        if (woodId.isEmpty()) {
            showAlert("Delete Error", "Please select a wood entry to delete.");
            return;
        }
        try {
            String result = WoodModel.deleteWood(woodId);
            showAlert("Delete Wood", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to delete wood: " + e.getMessage());
        }
    }

    @FXML
    private void resetToWood() {
        resetPage();
        showAlert("Reset", "Page reset successfully.");
    }

    private void populateFields(WoodDto wood) {
        txtId.setText(wood.getWoodId());
        txtType.setText(wood.getType());
        txtGrade.setText(wood.getGrade());
        txtDimensions.setText(wood.getDimensions());
        txtUnitPrice.setText(wood.getUnitPrice());
    }

    private void resetPage() {
        try {
            woodList.clear();
            woodList.addAll(WoodModel.getAllWood());
            tblWood.setItems(woodList);
            clearFields();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to reset page: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtId.clear();
        txtType.clear();
        txtGrade.clear();
        txtDimensions.clear();
        txtUnitPrice.clear();
        txtSearch.clear();
        tblWood.getSelectionModel().clearSelection();
    }

    private void validateFields() {
        if (txtType.getText().isEmpty() || txtGrade.getText().isEmpty() ||
                txtDimensions.getText().isEmpty() || txtUnitPrice.getText().isEmpty()) {
            throw new IllegalArgumentException("All fields except ID are required.");
        }
    }

    private String generateWoodId() {
        return "W" + (woodList.size() + 1);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
