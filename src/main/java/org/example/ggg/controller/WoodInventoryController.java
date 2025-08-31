package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.model.WoodInventoryDto;
import org.example.ggg.dao.impl.WoodInventoryModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class WoodInventoryController {

    public Button btnSearch;
    public AnchorPane WoodInventoryPage;
    public TextField txtprice1;

    @FXML
    private TextField txtSearch;

    @FXML
    private ComboBox<String> cmbSupplierOrderID;

    @FXML
    private ComboBox<String> cmbWoodID;

    @FXML
    private TextField txtWoodName;

    @FXML
    private TextField txtWoodLength;

    @FXML
    private TextField txtID;

    @FXML
    private TableView<WoodInventoryDto> tblInventory;

    @FXML
    private TableColumn<WoodInventoryDto, String> colID;

    @FXML
    private TableColumn<WoodInventoryDto, String> colSupplierOrderID;

    @FXML
    private TableColumn<WoodInventoryDto, String> colWoodID;

    @FXML
    private TableColumn<WoodInventoryDto, String> colWoodName;

    @FXML
    private TableColumn<WoodInventoryDto, String> colWoodLength;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    public void initialize() {
        // Initialize Table Columns
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierOrderID.setCellValueFactory(new PropertyValueFactory<>("supplierOrderId"));
        colWoodID.setCellValueFactory(new PropertyValueFactory<>("woodId"));
        colWoodName.setCellValueFactory(new PropertyValueFactory<>("woodName"));
        colWoodLength.setCellValueFactory(new PropertyValueFactory<>("woodLength"));

        try {
            loadSupplierOrderIDs();
            loadWoodIDs();
            loadInventoryTable();

            cmbWoodID.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    populateWoodDetails(newValue);
                } else {
                    txtWoodName.clear();
                    txtprice1.clear();
                }
            });
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to initialize data!", Alert.AlertType.ERROR);
        }
    }

    private void loadSupplierOrderIDs() throws SQLException, ClassNotFoundException {
        ArrayList<String> supplierOrderIDs = WoodInventoryModel.getSupplierOrderIDs();
        cmbSupplierOrderID.setItems(FXCollections.observableArrayList(supplierOrderIDs));
    }

    private void loadWoodIDs() throws SQLException, ClassNotFoundException {
        ArrayList<String> woodIDs = WoodInventoryModel.getWoodIDs();
        cmbWoodID.setItems(FXCollections.observableArrayList(woodIDs));
    }

    private void loadInventoryTable() throws SQLException, ClassNotFoundException {
        ArrayList<WoodInventoryDto> inventoryList = WoodInventoryModel.getAllInventory();
        ObservableList<WoodInventoryDto> data = FXCollections.observableArrayList(inventoryList);
        tblInventory.setItems(data);
    }

    private void populateWoodDetails(String woodId) {
        try {
            String woodName = WoodInventoryModel.getWoodNameById(woodId);
            Double unitPrice = WoodInventoryModel.getWoodUnitPriceById(woodId);

            if (woodName != null) {
                txtWoodName.setText(woodName);
            } else {
                txtWoodName.clear();
                showAlert("Warning", "No wood name found for the selected ID!", Alert.AlertType.WARNING);
            }

            if (unitPrice != null) {
                txtprice1.setText(String.valueOf(unitPrice));
            } else {
                txtprice1.clear();
                showAlert("Warning", "No price found for the selected wood ID!", Alert.AlertType.WARNING);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to fetch wood details!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void AddToInventory(ActionEvent event) {
        try {
            String supplierOrderId = cmbSupplierOrderID.getValue();
            String woodId = cmbWoodID.getValue();
            String woodName = txtWoodName.getText();
            String woodLength = txtWoodLength.getText();
            String price = txtprice1.getText();

            if (supplierOrderId == null || woodId == null || woodName.isEmpty() || woodLength.isEmpty() || price.isEmpty()) {
                showAlert("Error", "All fields are required!", Alert.AlertType.WARNING);
                return;
            }

            boolean isAdded = WoodInventoryModel.addInventory(new WoodInventoryDto(null, supplierOrderId, woodId, woodName, woodLength, price));
            showAlert("Success", isAdded ? "Inventory added successfully!" : "Failed to add inventory!", isAdded ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            loadInventoryTable();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add inventory!", Alert.AlertType.ERROR);
        }
    }

    public void pdateToInventory(ActionEvent actionEvent) {
        try {
            String id = txtID.getText();
            String supplierOrderId = cmbSupplierOrderID.getValue();
            String woodId = cmbWoodID.getValue();
            String woodName = txtWoodName.getText();
            String woodLength = txtWoodLength.getText();
            String price = txtprice1.getText();

            if (id.isEmpty() || supplierOrderId == null || woodId == null || woodName.isEmpty() || woodLength.isEmpty() || price.isEmpty()) {
                showAlert("Error", "All fields are required!", Alert.AlertType.WARNING);
                return;
            }

            boolean isUpdated = WoodInventoryModel.updateInventory(new WoodInventoryDto(id, supplierOrderId, woodId, woodName, woodLength, price));
            showAlert("Success", isUpdated ? "Inventory updated successfully!" : "Failed to update inventory!", isUpdated ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            loadInventoryTable();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update inventory!", Alert.AlertType.ERROR);
        }
    }

    public void DeleteToInventory(ActionEvent actionEvent) {
        try {
            String id = txtID.getText();

            if (id.isEmpty()) {
                showAlert("Error", "Please enter the inventory ID!", Alert.AlertType.WARNING);
                return;
            }

            boolean isDeleted = WoodInventoryModel.deleteInventory(id);
            showAlert("Success", isDeleted ? "Inventory deleted successfully!" : "Failed to delete inventory!", isDeleted ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            loadInventoryTable();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete inventory!", Alert.AlertType.ERROR);
        }
    }

    public void ResetToInventory(ActionEvent actionEvent) {
        txtID.clear();
        cmbSupplierOrderID.getSelectionModel().clearSelection();
        cmbWoodID.getSelectionModel().clearSelection();
        txtWoodName.clear();
        txtWoodLength.clear();
        txtprice1.clear();
        txtSearch.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    public void SearchWood(ActionEvent actionEvent) {
        try {
            String searchText = txtSearch.getText().trim();

            if (searchText.isEmpty()) {
                // Reload all inventory if the search field is empty
                loadInventoryTable();
                return;
            }

            // Fetch matching inventory records based on WoodID or WoodName
            ArrayList<WoodInventoryDto> searchResults = WoodInventoryModel.searchInventoryByIdOrName(searchText);

            if (searchResults.isEmpty()) {
                showAlert("Info", "No records found for the search query!", Alert.AlertType.INFORMATION);
                tblInventory.getItems().clear(); // Clear the table if no records found
            } else {
                ObservableList<WoodInventoryDto> data = FXCollections.observableArrayList(searchResults);
                tblInventory.setItems(data); // Update the table with search results
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to search inventory!", Alert.AlertType.ERROR);
        }
    }

}
