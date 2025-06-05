package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.dto.WoodInventoryDto;
import org.example.ggg.model.WoodInventoryModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class WoodInventoryController {

    // UI Components
    public TextField txtSupplierOrderID;
    public TextField txtWoodID;
    public TextField txtWoodLength;
    public TableView<WoodInventoryDto> tblInventory;
    public TableColumn<WoodInventoryDto, String> colSupplierOrderID;
    public TableColumn<WoodInventoryDto, String> colWoodID;
    public TableColumn<WoodInventoryDto, Double> colWoodLength;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public AnchorPane InventoryPage;
    public Button btnSearch;
    public TextField txtSearch;

    public void initialize() {
        colSupplierOrderID.setCellValueFactory(new PropertyValueFactory<>("supplierOrderId"));
        colWoodID.setCellValueFactory(new PropertyValueFactory<>("woodId"));
        colWoodLength.setCellValueFactory(new PropertyValueFactory<>("woodLength"));

        resetPage();

        tblInventory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnAdd.setDisable(true);

                txtSupplierOrderID.setText(newValue.getSupplierOrderId());
                txtWoodID.setText(newValue.getWoodId());
                txtWoodLength.setText(String.valueOf(newValue.getWoodLength()));
            } else {
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                btnAdd.setDisable(false);

                clearFields();
            }
        });
    }

    private void resetPage() {
        try {
            loadInventoryTable();
            clearFields();
            btnAdd.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to reset page: " + e.getMessage());
        }
    }

    public void loadInventoryTable() throws SQLException, ClassNotFoundException {
        ArrayList<WoodInventoryDto> inventoryList = WoodInventoryModel.getAllInventory();
        ObservableList<WoodInventoryDto> observableList = FXCollections.observableArrayList(inventoryList);
        tblInventory.setItems(observableList);
    }

    public void AddToInventory(ActionEvent actionEvent) {
        try {
            if (!validateInputs()) return;

            WoodInventoryDto dto = new WoodInventoryDto(
                    txtSupplierOrderID.getText(),
                    txtWoodID.getText(),
                    Double.parseDouble(txtWoodLength.getText())
            );

            boolean isAdded = WoodInventoryModel.addInventory(dto);
            showAlert("Add Inventory", isAdded ? "Successfully added!" : "Failed to add.");
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
        }
    }

    public void UpdateToInventory(ActionEvent actionEvent) {
        try {
            if (!validateInputs()) return;

            WoodInventoryDto dto = new WoodInventoryDto(
                    txtSupplierOrderID.getText(),
                    txtWoodID.getText(),
                    Double.parseDouble(txtWoodLength.getText())
            );

            boolean isUpdated = WoodInventoryModel.updateInventory(dto);
            showAlert("Update Inventory", isUpdated ? "Successfully updated!" : "Failed to update.");
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
        }
    }

    public void DeleteToInventory(ActionEvent actionEvent) {
        try {
            String woodId = txtWoodID.getText().trim();
            if (woodId.isEmpty()) {
                showAlert("Validation Error", "Please enter a Wood ID to delete.");
                return;
            }

            boolean isDeleted = Boolean.parseBoolean(WoodInventoryModel.deleteInventory(woodId));
            showAlert("Delete Inventory", isDeleted ? "Failed to delete. Dependent data may exist" : "Successfully deleted!.");
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
        }
    }


    public void ResetToInventory(ActionEvent actionEvent) {
        resetPage();
    }

    private boolean validateInputs() {
        try {
            Double.parseDouble(txtWoodLength.getText());
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter a valid number for Wood Length.");
            return false;
        }

        if (txtSupplierOrderID.getText().isEmpty() || txtWoodID.getText().isEmpty()) {
            showAlert("Validation Error", "All fields must be filled.");
            return false;
        }

        return true;
    }

    private void clearFields() {
        txtSupplierOrderID.clear();
        txtWoodID.clear();
        txtWoodLength.clear();
        tblInventory.getSelectionModel().clearSelection();
    }

    private void handleException(Exception e) {
        showAlert("Error", "An error occurred: " + e.getMessage());
        e.printStackTrace();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void SearchWood(ActionEvent actionEvent) {
        try {
            String woodId = txtSearch.getText().trim();
            if (woodId.isEmpty()) {
                showAlert("Validation Error", "Please enter a Wood ID to search.");
                return;
            }

            WoodInventoryDto dto = WoodInventoryModel.getInventoryByID(woodId);

            if (dto != null) {
                ObservableList<WoodInventoryDto> observableList = FXCollections.observableArrayList(dto);
                tblInventory.setItems(observableList);
            } else {
                showAlert("Search Result", "No data found for Wood ID: " + woodId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
        }
    }

}