package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.model.InventoryDto;
import org.example.ggg.dao.impl.InventoryModel;

import java.sql.SQLException;

public class InventoryController {

    @FXML
    private ComboBox<String> cmbItemID;

    @FXML
    private ComboBox<String> cmbSupplierOrderID;

    @FXML
    private TextField txtQuantityAvailable;

    @FXML
    private TableView<InventoryDto> tblInventory;

    @FXML
    private TableColumn<InventoryDto, String> colItemID;

    @FXML
    private TableColumn<InventoryDto, String> colSupplierOrderID;

    @FXML
    private TableColumn<InventoryDto, String> colQuantityAvailable;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    private final ObservableList<InventoryDto> inventoryList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colSupplierOrderID.setCellValueFactory(new PropertyValueFactory<>("supplierOrderId"));
        colQuantityAvailable.setCellValueFactory(new PropertyValueFactory<>("quantityAvailable"));

        loadComboBoxData();
        loadInventoryData();

        tblInventory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cmbItemID.setValue(newValue.getItemId());
                cmbSupplierOrderID.setValue(newValue.getSupplierOrderId());
                txtQuantityAvailable.setText(newValue.getQuantityAvailable());
                btnAdd.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }
        });

        resetPage();
    }

    @FXML
    private void AddToInventory(ActionEvent event) {
        if (validateInputs()) {
            InventoryDto newItem = new InventoryDto(
                    cmbItemID.getValue(),
                    cmbSupplierOrderID.getValue(),
                    txtQuantityAvailable.getText()
            );
            try {
                boolean isAdded = InventoryModel.addInventory(newItem);
                if (isAdded) {
                    showAlert("Success", "Item successfully added to inventory!", Alert.AlertType.INFORMATION);
                    loadInventoryData();
                    resetPage();
                } else {
                    showAlert("Error", "Failed to add item!", Alert.AlertType.ERROR);
                }
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Database Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void UpdateToInventory(ActionEvent event) {
        InventoryDto selectedItem = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedItem != null && validateInputs()) {
            InventoryDto updatedItem = new InventoryDto(
                    cmbItemID.getValue(),
                    cmbSupplierOrderID.getValue(),
                    txtQuantityAvailable.getText()
            );
            try {
                boolean isUpdated = InventoryModel.updateInventory(updatedItem);
                if (isUpdated) {
                    showAlert("Success", "Item successfully updated!", Alert.AlertType.INFORMATION);
                    loadInventoryData();
                    resetPage();
                } else {
                    showAlert("Error", "Failed to update item!", Alert.AlertType.ERROR);
                }
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Database Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void DeleteToInventory(ActionEvent event) {
        InventoryDto selectedItem = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                boolean isDeleted = InventoryModel.deleteInventory(selectedItem.getItemId());
                if (isDeleted) {
                    showAlert("Success", "Item successfully deleted!", Alert.AlertType.INFORMATION);
                    loadInventoryData();
                    resetPage();
                } else {
                    showAlert("Error", "Failed to delete item!", Alert.AlertType.ERROR);
                }
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Database Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void ResetToInventory(ActionEvent event) {
        resetPage();
    }

    private void loadComboBoxData() {
        try {
            cmbItemID.setItems(FXCollections.observableArrayList(InventoryModel.getAllItemIds()));
            cmbSupplierOrderID.setItems(FXCollections.observableArrayList(InventoryModel.getAllSupplierOrderIDs()));
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Database Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadInventoryData() {
        inventoryList.clear();
        try {
            inventoryList.addAll(InventoryModel.getAllInventory());
            tblInventory.setItems(inventoryList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Database Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validateInputs() {
        if (cmbItemID.getValue() == null || cmbSupplierOrderID.getValue() == null || txtQuantityAvailable.getText().isEmpty()) {
            showAlert("Validation Error", "All fields are required!", Alert.AlertType.WARNING);
            return false;
        }
        if (!txtQuantityAvailable.getText().matches("\\d+")) {
            showAlert("Validation Error", "Quantity must be numeric!", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void resetPage() {
        cmbItemID.getSelectionModel().clearSelection();
        cmbSupplierOrderID.getSelectionModel().clearSelection();
        txtQuantityAvailable.clear();
        tblInventory.getSelectionModel().clearSelection();
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }
}
