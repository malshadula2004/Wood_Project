package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.model.SupplierOrderDto;
import org.example.ggg.dao.impl.SupplierOrderModel;
import org.example.ggg.dao.impl.SuppliersModel;

import java.sql.SQLException;
import java.time.LocalDate;

public class SupplierOrderController {

    @FXML
    private TextField txtOrderId, txtSearch;

    @FXML
    private DatePicker datePickerOrderDate;  // Only one DatePicker here

    @FXML
    private ComboBox<String> comboBoxSupplierId;

    @FXML
    private TableView<SupplierOrderDto> tblSupplierOrder;

    @FXML
    private TableColumn<SupplierOrderDto, String> colOrderId, colOrderDate, colSupplierId;

    @FXML
    private Button btnAdd, btnUpdate, btnDelete, btnReset, btnSearch;

    private ObservableList<SupplierOrderDto> supplierOrderList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup Table Columns
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("supplierOrderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        loadSupplierOrders();
        loadSupplierIds();

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        tblSupplierOrder.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnAdd.setDisable(true);
            } else {
                resetFields();
            }
        });
    }

    @FXML
    private void AddToSupplierOrders() {
        if (validateInputs()) {
            String date = datePickerOrderDate.getValue().toString();
            String supplierId = comboBoxSupplierId.getValue();

            SupplierOrderDto supplierOrder = new SupplierOrderDto(null, date, supplierId);

            try {
                String result = SupplierOrderModel.saveSupplierOrder(supplierOrder);
                showAlert(Alert.AlertType.INFORMATION, "Success", result);

                // Reload table data and reset fields - do NOT close window
                loadSupplierOrders();
                resetFields();

            } catch (SQLException | ClassNotFoundException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add supplier order: " + e.getMessage());
            }
        }
    }

    @FXML
    private void UpdateToSupplierOrders() {
        SupplierOrderDto selectedOrder = tblSupplierOrder.getSelectionModel().getSelectedItem();
        if (selectedOrder != null && validateInputs()) {
            selectedOrder.setDate(datePickerOrderDate.getValue().toString());
            selectedOrder.setSupplierId(comboBoxSupplierId.getValue());

            try {
                String result = SupplierOrderModel.updateSupplierOrder(selectedOrder);
                showAlert(Alert.AlertType.INFORMATION, "Success", result);
                loadSupplierOrders();
                resetFields();
            } catch (SQLException | ClassNotFoundException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update supplier order: " + e.getMessage());
            }
        }
    }

    @FXML
    private void DeleteToSupplierOrders() {
        SupplierOrderDto selectedOrder = tblSupplierOrder.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                String result = SupplierOrderModel.deleteSupplierOrder(selectedOrder.getSupplierOrderId());
                showAlert(Alert.AlertType.INFORMATION, "Success", result);
                loadSupplierOrders();
                resetFields();
            } catch (SQLException | ClassNotFoundException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete supplier order: " + e.getMessage());
            }
        }
    }

    @FXML
    private void ResetToSupplierOrders() {
        tblSupplierOrder.getSelectionModel().clearSelection();
        txtSearch.clear();
        tblSupplierOrder.setItems(supplierOrderList);
        resetFields();
    }

    @FXML
    private void SearchSupplierOrders() {
        String supplierId = txtSearch.getText().trim();

        if (supplierId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter Supplier ID to search.");
            return;
        }

        ObservableList<SupplierOrderDto> filteredList = FXCollections.observableArrayList();
        for (SupplierOrderDto order : supplierOrderList) {
            if (order.getSupplierId().equalsIgnoreCase(supplierId)) {
                filteredList.add(order);
            }
        }

        if (filteredList.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Results", "No orders found for Supplier ID: " + supplierId);
        } else {
            tblSupplierOrder.setItems(filteredList);
        }
    }

    private void populateFields(SupplierOrderDto order) {
        txtOrderId.setText(order.getSupplierOrderId());
        datePickerOrderDate.setValue(LocalDate.parse(order.getDate()));
        comboBoxSupplierId.setValue(order.getSupplierId());
    }

    private void resetFields() {
        txtOrderId.clear();
        datePickerOrderDate.setValue(null);
        comboBoxSupplierId.setValue(null);
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private boolean validateInputs() {
        if (datePickerOrderDate.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Order Date cannot be empty.");
            return false;
        }
        if (comboBoxSupplierId.getValue() == null || comboBoxSupplierId.getValue().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Supplier ID cannot be empty.");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadSupplierOrders() {
        supplierOrderList.clear();
        try {
            supplierOrderList.addAll(SupplierOrderModel.getAllSupplierOrders());
            tblSupplierOrder.setItems(supplierOrderList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load supplier orders: " + e.getMessage());
        }
    }

    private void loadSupplierIds() {
        try {
            ObservableList<String> supplierIds = FXCollections.observableArrayList(SuppliersModel.getAllSupplierIds());
            comboBoxSupplierId.setItems(supplierIds);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load supplier IDs: " + e.getMessage());
        }
    }
}
