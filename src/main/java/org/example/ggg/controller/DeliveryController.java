package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.dto.DeliveryDto;
import org.example.ggg.model.DeliveryModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryController {

    // UI Components
    public TextField txtId;
    public TextField txtPurchaseOrderId;
    public TextField txtDeliveryDate;
    public TextField txtDriverId; // Corrected to match the FXML and DTO field
    public TextField txtDeliveryStatus;
    public TableView<DeliveryDto> tblDelivery;
    public TableColumn<DeliveryDto, String> colDeliveryId;
    public TableColumn<DeliveryDto, String> colPurchaseOrderId;
    public TableColumn<DeliveryDto, String> colDeliveryDate;
    public TableColumn<DeliveryDto, String> colDriverId; // Corrected
    public TableColumn<DeliveryDto, String> colDeliveryStatus;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public AnchorPane DeliveryPage;
    public TextField txtSearch;
    public Button btnSearch;

    public void initialize() {
        // Bind columns to DeliveryDto fields
        colDeliveryId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colPurchaseOrderId.setCellValueFactory(new PropertyValueFactory<>("purchaseOrdersId"));
        colDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("employeeId")); // Correct binding
        colDeliveryStatus.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));

        try {
            resetPage();
        } catch (Exception e) {
            showAlert("Error", "Initialization failed: " + e.getMessage());
        }

        tblDelivery.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnAdd.setDisable(true);

                txtId.setText(newValue.getDeliveryId());
                txtPurchaseOrderId.setText(newValue.getPurchaseOrdersId());
                txtDeliveryDate.setText(newValue.getDeliveryDate());
                txtDriverId.setText(newValue.getEmployeeId());
                txtDeliveryStatus.setText(newValue.getDeliveryStatus());
            } else {
                resetButtonsAndFields();
            }
        });
    }

    private void resetPage() {
        loadDeliveryTable();
        resetButtonsAndFields();
    }

    private void loadDeliveryTable() {
        try {
            ArrayList<DeliveryDto> allDeliveries = DeliveryModel.getAllDeliveries();
            ObservableList<DeliveryDto> deliveryList = FXCollections.observableArrayList(allDeliveries);
            tblDelivery.setItems(deliveryList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load delivery data: " + e.getMessage());
        }
    }

    public void AddToDelivery(ActionEvent actionEvent) {
        performSaveOrUpdate("add");
    }

    public void UpdateToDelivery(ActionEvent actionEvent) {
        performSaveOrUpdate("update");
    }

    private void performSaveOrUpdate(String action) {
        try {
            DeliveryDto deliveryDto = new DeliveryDto(
                    txtId.getText(),
                    txtPurchaseOrderId.getText(),
                    txtDeliveryDate.getText(),
                    txtDriverId.getText(),
                    txtDeliveryStatus.getText()
            );

            String result;
            if (action.equals("add")) {
                result = DeliveryModel.saveDelivery(deliveryDto);
            } else {
                result = DeliveryModel.updateDelivery(deliveryDto);
            }

            showAlert(action.equals("add") ? "Add Delivery" : "Update Delivery", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to " + action + " delivery: " + e.getMessage());
        }
    }

    public void DeleteToDelivery(ActionEvent actionEvent) {
        try {
            String deliveryId = txtId.getText();
            String result = DeliveryModel.deleteDelivery(Integer.parseInt(deliveryId));
            showAlert("Delete Delivery", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to delete delivery: " + e.getMessage());
        }
    }

    public void ResetToDelivery(ActionEvent actionEvent) {
        resetPage();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetButtonsAndFields() {
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        txtId.clear();
        txtPurchaseOrderId.clear();
        txtDeliveryDate.clear();
        txtDriverId.clear();
        txtDeliveryStatus.clear();
    }

    public void SearchDelivery(ActionEvent actionEvent) {
        String searchText = txtSearch.getText().toLowerCase();

        try {
            ArrayList<DeliveryDto> filteredDeliveries = DeliveryModel.searchDeliveries(searchText);
            ObservableList<DeliveryDto> filteredList = FXCollections.observableArrayList(filteredDeliveries);
            tblDelivery.setItems(filteredList);

            if (filteredList.isEmpty()) {
                showAlert("Search Result", "No matching deliveries found.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Search failed: " + e.getMessage());
        }
    }
}
