package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.model.DeliveryDto;
import org.example.ggg.dao.impl.DeliveryModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DeliveryController {

    public Button btnReset;
    @FXML
    private TextField txtId;

    @FXML
    private ComboBox<String> cmbPurchaseOrderId;

    @FXML
    private DatePicker dpDeliveryDate;

    @FXML
    private ComboBox<String> cmbDriverId;

    @FXML
    private TextField txtDeliveryStatus;

    @FXML
    private TableView<DeliveryDto> tblDelivery;

    @FXML
    private TableColumn<DeliveryDto, String> colDeliveryId;

    @FXML
    private TableColumn<DeliveryDto, String> colPurchaseOrderId;

    @FXML
    private TableColumn<DeliveryDto, String> colDeliveryDate;

    @FXML
    private TableColumn<DeliveryDto, String> colDriverId;

    @FXML
    private TableColumn<DeliveryDto, String> colDeliveryStatus;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtSearch;

    @FXML
    public void initialize() {
        // Setup table columns
        colDeliveryId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colPurchaseOrderId.setCellValueFactory(new PropertyValueFactory<>("purchaseOrdersId"));
        colDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDeliveryStatus.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));

        // Load ComboBoxes and Table
        try {
            loadComboBoxes();
            loadAllDeliveries();
            resetButtonsAndFields();
        } catch (Exception e) {
            showAlert("Error", "Initialization error: " + e.getMessage());
        }

        // Listen table selection to populate form for update/delete
        tblDelivery.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnAdd.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);

                txtId.setText(newSelection.getDeliveryId());
                cmbPurchaseOrderId.setValue(newSelection.getPurchaseOrdersId());
                dpDeliveryDate.setValue(LocalDate.parse(newSelection.getDeliveryDate()));
                cmbDriverId.setValue(newSelection.getEmployeeId());
                txtDeliveryStatus.setText(newSelection.getDeliveryStatus());
            } else {
                resetButtonsAndFields();
            }
        });
    }

    private void loadComboBoxes() throws SQLException, ClassNotFoundException {
        ArrayList<String> purchaseOrderIds = DeliveryModel.getAllPurchaseOrderIds();
        cmbPurchaseOrderId.setItems(FXCollections.observableArrayList(purchaseOrderIds));

        ArrayList<String> employeeIds = DeliveryModel.getAllEmployeeIds();
        cmbDriverId.setItems(FXCollections.observableArrayList(employeeIds));
    }

    private void loadAllDeliveries() throws SQLException, ClassNotFoundException {
        ArrayList<DeliveryDto> deliveries = DeliveryModel.getAllDeliveries();
        tblDelivery.setItems(FXCollections.observableArrayList(deliveries));
    }

    @FXML
    void onAddClicked() {
        try {
            DeliveryDto dto = new DeliveryDto(
                    txtId.getText(),
                    cmbPurchaseOrderId.getValue(),
                    dpDeliveryDate.getValue().toString(),
                    cmbDriverId.getValue(),
                    txtDeliveryStatus.getText()
            );
            String message = DeliveryModel.saveDelivery(dto);
            showAlert("Add Delivery", message);
            loadAllDeliveries();
            resetButtonsAndFields();
        } catch (Exception e) {
            showAlert("Error", "Add failed: " + e.getMessage());
        }
    }

    @FXML
    void onUpdateClicked() {
        try {
            DeliveryDto dto = new DeliveryDto(
                    txtId.getText(),
                    cmbPurchaseOrderId.getValue(),
                    dpDeliveryDate.getValue().toString(),
                    cmbDriverId.getValue(),
                    txtDeliveryStatus.getText()
            );
            String message = DeliveryModel.updateDelivery(dto);
            showAlert("Update Delivery", message);
            loadAllDeliveries();
            resetButtonsAndFields();
        } catch (Exception e) {
            showAlert("Error", "Update failed: " + e.getMessage());
        }
    }

    @FXML
    void onDeleteClicked() {
        try {
            String deliveryId = txtId.getText();
            String message = DeliveryModel.deleteDelivery(deliveryId);
            showAlert("Delete Delivery", message);
            loadAllDeliveries();
            resetButtonsAndFields();
        } catch (Exception e) {
            showAlert("Error", "Delete failed: " + e.getMessage());
        }
    }

    @FXML
    void onSearchChanged() {
        try {
            String searchText = txtSearch.getText().trim();
            if (searchText.isEmpty()) {
                loadAllDeliveries();
            } else {
                ArrayList<DeliveryDto> filtered = DeliveryModel.searchDeliveries(searchText);
                tblDelivery.setItems(FXCollections.observableArrayList(filtered));
            }
        } catch (Exception e) {
            showAlert("Error", "Search failed: " + e.getMessage());
        }
    }

    private void resetButtonsAndFields() {
        txtId.clear();
        cmbPurchaseOrderId.getSelectionModel().clearSelection();
        dpDeliveryDate.setValue(null);
        cmbDriverId.getSelectionModel().clearSelection();
        txtDeliveryStatus.clear();

        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void resetToDelivery(ActionEvent actionEvent) {
        resetButtonsAndFields();
    }

}
