package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.dto.PaymentsDto;
import org.example.ggg.model.PaymentsModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentsController {

    public ComboBox<String> cmbCustomerId;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtPurchaseOrderId, txtCustomerId, txtPaymentDate, txtAmount, txtPaymentMethod, txtPaymentId;

    @FXML
    private TableView<PaymentsDto> tblPayments;

    @FXML
    private TableColumn<PaymentsDto, String> colPaymentId, colPurchaseOrdersId, colCustomerId, colPaymentDate, colAmount, colPaymentMethod;

    @FXML
    public void initialize() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPurchaseOrdersId.setCellValueFactory(new PropertyValueFactory<>("purchaseOrdersId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        loadPaymentsTable();
        loadCustomerIds(); // Load customer IDs into ComboBox
    }

    private void loadPaymentsTable() {
        try {
            ArrayList<PaymentsDto> allPayments = PaymentsModel.getAllPayments();
            ObservableList<PaymentsDto> observableList = FXCollections.observableArrayList(allPayments);
            tblPayments.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            showError("Error loading payments", e.getMessage());
        }
    }

    private void loadCustomerIds() {
        try {
            ArrayList<String> customerIds = PaymentsModel.getCustomerIds();
            cmbCustomerId.setItems(FXCollections.observableArrayList(customerIds));
        } catch (SQLException | ClassNotFoundException e) {
            showError("Error loading customer IDs", e.getMessage());
        }
    }

    @FXML
    private void addToPayment() {
        try {
            PaymentsDto payment = new PaymentsDto(
                    null,
                    txtPurchaseOrderId.getText(),
                    txtCustomerId.getText(),
                    txtPaymentMethod.getText(),
                    txtAmount.getText(),
                    txtPaymentDate.getText()
            );

            String result = PaymentsModel.savePayment(payment);
            showInfo(result);
            loadPaymentsTable();
        } catch (Exception e) {
            showError("Error adding payment", e.getMessage());
        }
    }

    @FXML
    private void updatePayment() {
        try {
            PaymentsDto payment = new PaymentsDto(
                    txtPaymentId.getText(),
                    txtPurchaseOrderId.getText(),
                    txtCustomerId.getText(),
                    txtPaymentMethod.getText(),
                    txtAmount.getText(),
                    txtPaymentDate.getText()
            );

            String result = PaymentsModel.updatePayment(payment);
            showInfo(result);
            loadPaymentsTable();
        } catch (Exception e) {
            showError("Error updating payment", e.getMessage());
        }
    }

    @FXML
    private void deletePayment() {
        try {
            String result = PaymentsModel.deletePayment(txtPaymentId.getText());
            showInfo(result);
            loadPaymentsTable();
        } catch (Exception e) {
            showError("Error deleting payment", e.getMessage());
        }
    }

    @FXML
    private void resetFields() {
        txtPaymentId.clear();
        txtPurchaseOrderId.clear();
        txtCustomerId.clear();
        txtPaymentDate.clear();
        txtAmount.clear();
        txtPaymentMethod.clear();
    }

    @FXML
    public void searchPaymentsByCustomer(ActionEvent actionEvent) {
        try {
            String customerId = txtSearch.getText();
            ArrayList<PaymentsDto> paymentsByCustomer = PaymentsModel.searchPaymentsByCustomer(customerId);
            tblPayments.setItems(FXCollections.observableArrayList(paymentsByCustomer));
        } catch (SQLException | ClassNotFoundException e) {
            showError("Error searching payments", e.getMessage());
        }
    }

    private void showInfo(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
