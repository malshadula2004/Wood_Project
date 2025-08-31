package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.bo.SupplierOrderBO;
import org.example.ggg.bo.SupplierOrderBOImpl;
import org.example.ggg.dao.impl.SuppliersModel;
import org.example.ggg.model.SupplierOrderDto;

import java.sql.SQLException;
import java.time.LocalDate;

public class SupplierOrderController {

    @FXML
    private TextField txtOrderId, txtSearch;

    @FXML
    private DatePicker datePickerOrderDate;

    @FXML
    private ComboBox<String> comboBoxSupplierId;

    @FXML
    private TableView<SupplierOrderDto> tblSupplierOrder;

    @FXML
    private TableColumn<SupplierOrderDto, String> colOrderId, colOrderDate, colSupplierId;

    @FXML
    private Button btnAdd, btnUpdate, btnDelete, btnReset, btnSearch;

    private final ObservableList<SupplierOrderDto> supplierOrderList = FXCollections.observableArrayList();
    private final SupplierOrderBO supplierOrderBO = new SupplierOrderBOImpl();

    @FXML
    public void initialize() {
        // Table Columns Mapping
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("supplierOrderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        // Load Data
        loadSupplierOrders();
        loadSupplierIds();

        // Disable Buttons Initially
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        // Make Order ID field ReadOnly (Primary Key)
        txtOrderId.setEditable(false);

        // Table Row Selection Listener
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
            SupplierOrderDto supplierOrder = new SupplierOrderDto(null, datePickerOrderDate.getValue().toString(), comboBoxSupplierId.getValue());
            try {
                String result = supplierOrderBO.saveSupplierOrder(supplierOrder);
                showAlert(Alert.AlertType.INFORMATION, "Success", result);
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
                String result = supplierOrderBO.updateSupplierOrder(selectedOrder);
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
                String result = supplierOrderBO.deleteSupplierOrder(selectedOrder.getSupplierOrderId());
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
        try {
            ObservableList<SupplierOrderDto> filteredList = FXCollections.observableArrayList(supplierOrderBO.getSupplierOrdersBySupplierId(supplierId));
            if (filteredList.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Results", "No orders found for Supplier ID: " + supplierId);
            } else {
                tblSupplierOrder.setItems(filteredList);
            }
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to search: " + e.getMessage());
        }
    }

    // Populate Fields from Table Row
    private void populateFields(SupplierOrderDto order) {
        txtOrderId.setText(order.getSupplierOrderId());
        datePickerOrderDate.setValue(LocalDate.parse(order.getDate()));
        comboBoxSupplierId.setValue(order.getSupplierId());
    }

    // Reset Form Fields
    private void resetFields() {
        txtOrderId.clear();
        datePickerOrderDate.setValue(null);
        comboBoxSupplierId.setValue(null);
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    // Input Validation
    private boolean validateInputs() {
        if (datePickerOrderDate.getValue() == null || comboBoxSupplierId.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return false;
        }
        return true;
    }

    // Show Alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Load Supplier Orders to Table
    private void loadSupplierOrders() {
        supplierOrderList.clear();
        try {
            supplierOrderList.addAll(supplierOrderBO.getAllSupplierOrders());
            tblSupplierOrder.setItems(supplierOrderList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load supplier orders: " + e.getMessage());
        }
    }

    // Load Supplier IDs to ComboBox
    private void loadSupplierIds() {
        try {
            ObservableList<String> supplierIds = FXCollections.observableArrayList(SuppliersModel.getAllSupplierIds());
            comboBoxSupplierId.setItems(supplierIds);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load supplier IDs: " + e.getMessage());
        }
    }
}
