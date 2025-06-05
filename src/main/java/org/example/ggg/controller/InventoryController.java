package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.dto.InventoryDto;
import org.example.ggg.model.InventoryModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryController {

    public Button btnSearch;
    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtItemID;

    @FXML
    private TextField txtSupplierOrderID;

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

        loadInventoryData();

        tblInventory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtItemID.setText(newValue.getItemId());
                txtSupplierOrderID.setText(newValue.getSupplierOrderId());
                txtQuantityAvailable.setText(newValue.getQuantityAvailable());
                btnAdd.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }
        });

        resetPage();
    }

    @FXML
    public void AddToInventory(ActionEvent actionEvent) {
        if (validateInputs()) {
            InventoryDto newItem = new InventoryDto(
                    txtItemID.getText(),
                    txtSupplierOrderID.getText(),
                    txtQuantityAvailable.getText()
            );
            try {
                boolean isAdded = InventoryModel.addInventory(newItem);
                if (isAdded) {
                    showAlert("Success", "Item successfully added to inventory!");
                    loadInventoryData();
                } else {
                    showAlert("Error", "Failed to add item!");
                }
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Database Error", e.getMessage());
            }
            resetPage();
        }
    }

    @FXML
    public void UpdateToInventory(ActionEvent actionEvent) {
        InventoryDto selectedItem = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedItem != null && validateInputs()) {
            InventoryDto updatedItem = new InventoryDto(
                    txtItemID.getText(),
                    txtSupplierOrderID.getText(),
                    txtQuantityAvailable.getText()
            );
            try {
                boolean isUpdated = InventoryModel.updateInventory(updatedItem);
                if (isUpdated) {
                    showAlert("Success", "Item successfully updated!");
                    loadInventoryData();
                } else {
                    showAlert("Error", "Failed to update item!");
                }
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Database Error", e.getMessage());
            }
            resetPage();
        }
    }

    @FXML
    public void DeleteToInventory(ActionEvent actionEvent) {
        InventoryDto selectedItem = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                boolean isDeleted = InventoryModel.deleteInventory(selectedItem.getItemId());
                if (isDeleted) {
                    showAlert("Success", "Item successfully deleted!");
                    loadInventoryData();
                } else {
                    showAlert("Error", "Failed to delete item!");
                }
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Database Error", e.getMessage());
            }
            resetPage();
        }
    }

    @FXML
    public void ResetToInventory(ActionEvent actionEvent) {
        resetPage();
    }

    private void resetPage() {
        txtItemID.clear();
        txtSupplierOrderID.clear();
        txtQuantityAvailable.clear();
        txtSearch.clear();
        tblInventory.getSelectionModel().clearSelection();
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private boolean validateInputs() {
        if (txtItemID.getText().isEmpty() || txtSupplierOrderID.getText().isEmpty() || txtQuantityAvailable.getText().isEmpty()) {
            showAlert("Validation Error", "All fields are required!");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadInventoryData() {
        inventoryList.clear();
        try {
            ArrayList<InventoryDto> allInventory = InventoryModel.getAllInventory();
            inventoryList.addAll(allInventory);
            tblInventory.setItems(inventoryList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Database Error", e.getMessage());
        }
    }

    @FXML
    public void SearchInventory(ActionEvent actionEvent) {
        String itemId = txtSearch.getText().trim();

        if (itemId.isEmpty()) {
            showAlert("Validation Error", "Please enter an Item ID to search!");
            return;
        }

        try {
            ArrayList<InventoryDto> searchResults = InventoryModel.searchInventory(itemId);

            inventoryList.clear();
            if (searchResults.isEmpty()) {
                showAlert("No Results", "No inventory data found for the given Item ID.");
            } else {
                inventoryList.addAll(searchResults);
                tblInventory.setItems(inventoryList);
            }
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Database Error", e.getMessage());
        }
    }
}
