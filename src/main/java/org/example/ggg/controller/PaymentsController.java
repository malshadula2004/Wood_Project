package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.model.PaymentsDto;
import org.example.ggg.dao.impl.PaymentsModel;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PaymentsController {

    @FXML
    private DatePicker dpPaymentDate;

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField txtSearch, txtPurchaseOrderId, txtAmount, txtPaymentMethod, txtId;

    @FXML
    private TableView<PaymentsDto> tblPayments;

    @FXML
    private TableColumn<PaymentsDto, String> colPaymentId, colPurchaseOrdersId, colCustomerId, colPaymentDate, colAmount, colPaymentMethod;

    @FXML
    public void initialize() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPurchaseOrdersId.setCellValueFactory(new PropertyValueFactory<>("purchaseOrdersId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        loadPaymentsTable();
        loadEmployeeIds();
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

    private void loadEmployeeIds() {
        try {
            ArrayList<String> employeeIds = PaymentsModel.getEmployeeIds();
            cmbEmployeeId.setItems(FXCollections.observableArrayList(employeeIds));
        } catch (SQLException | ClassNotFoundException e) {
            showError("Error loading employee IDs", e.getMessage());
        }
    }

    @FXML
    private void addToPayment() {
        try {
            if (dpPaymentDate.getValue() == null) {
                showError("Invalid Input", "Please select a valid payment date.");
                return;
            }

            String formattedDate = dpPaymentDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            PaymentsDto payment = new PaymentsDto(
                    null,
                    txtPurchaseOrderId.getText(),
                    cmbEmployeeId.getValue(),
                    formattedDate,
                    txtAmount.getText(),
                    txtPaymentMethod.getText()
            );

            String result = PaymentsModel.savePayment(payment);
            showInfo(result);
            loadPaymentsTable();
        } catch (Exception e) {
            showError("Error Adding Payment", e.getMessage());
        }
    }

    @FXML
    private void updatePayment() {
        try {
            if (dpPaymentDate.getValue() == null) {
                showError("Invalid Input", "Please select a valid payment date.");
                return;
            }

            String formattedDate = dpPaymentDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            PaymentsDto payment = new PaymentsDto(
                    txtId.getText(),
                    txtPurchaseOrderId.getText(),
                    cmbEmployeeId.getValue(),
                    formattedDate,
                    txtAmount.getText(),
                    txtPaymentMethod.getText()
            );

            String result = PaymentsModel.updatePayment(payment);
            showInfo(result);
            loadPaymentsTable();
        } catch (Exception e) {
            showError("Error Updating Payment", e.getMessage());
        }
    }

    @FXML
    private void deletePayment() {
        try {
            String result = PaymentsModel.deletePayment(txtId.getText());
            showInfo(result);
            loadPaymentsTable();
        } catch (Exception e) {
            showError("Error deleting payment", e.getMessage());
        }
    }

    @FXML
    private void resetFields() {
        txtId.clear();
        txtPurchaseOrderId.clear();
        cmbEmployeeId.setValue(null);
        dpPaymentDate.setValue(null);
        txtAmount.clear();
        txtPaymentMethod.clear();
    }

    @FXML
    private void searchPaymentsByEmployee(ActionEvent actionEvent) {
        try {
            String employeeId = txtSearch.getText();
            ArrayList<PaymentsDto> paymentsByEmployee = PaymentsModel.searchPaymentsByEmployee(employeeId);
            tblPayments.setItems(FXCollections.observableArrayList(paymentsByEmployee));
        } catch (SQLException | ClassNotFoundException e) {
            showError("Error searching payments", e.getMessage());
        }
    }

    private void showInfo(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
