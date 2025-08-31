package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.model.MonthlyProfitLossDto;
import org.example.ggg.dao.impl.MonthlyProfitLossModel;

import java.sql.SQLException;

class MonthlyProfitOrLossController {

    public AnchorPane MonthlyProfitOrLossPage;
    @FXML
    private TextField txtID, txtMonth, txtOrderAmount, txtPayment, txtProfitLoss, txtFinalAmount;

    @FXML
    private TableView<MonthlyProfitLossDto> tblMonthlyProfitLoss;

    @FXML
    private TableColumn<MonthlyProfitLossDto, String> colID, colMonth;

    @FXML
    private TableColumn<MonthlyProfitLossDto, Number> colOrderAmount, colPayment, colProfitLoss, colFinalAmount;

    private ObservableList<MonthlyProfitLossDto> monthlyProfitLossList;

    @FXML
    public void initialize() {
        colID.setCellValueFactory(data -> data.getValue().idProperty());
        colMonth.setCellValueFactory(data -> data.getValue().monthProperty());
        colOrderAmount.setCellValueFactory(data -> data.getValue().orderAmountProperty());
        colPayment.setCellValueFactory(data -> data.getValue().paymentProperty());
        colProfitLoss.setCellValueFactory(data -> data.getValue().profitLossProperty());
        colFinalAmount.setCellValueFactory(data -> data.getValue().finalAmountProperty());

        monthlyProfitLossList = FXCollections.observableArrayList();
        tblMonthlyProfitLoss.setItems(monthlyProfitLossList);

        loadMonthlyProfitLossData();
    }

    private void loadMonthlyProfitLossData() {
        try {
            monthlyProfitLossList.clear();
            monthlyProfitLossList.addAll(MonthlyProfitLossModel.getAllRecords());
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load data from database.");
        }
    }

    @FXML
    void addRecord(ActionEvent event) {
        String id = txtID.getText();
        String month = txtMonth.getText();
        int orderAmount = Integer.parseInt(txtOrderAmount.getText());
        double payment = Double.parseDouble(txtPayment.getText());
        double profitLoss = Double.parseDouble(txtProfitLoss.getText());
        double finalAmount = Double.parseDouble(txtFinalAmount.getText());

        if (id.isEmpty() || month.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "ID and Month fields are required!");
            return;
        }

        MonthlyProfitLossDto record = new MonthlyProfitLossDto(id, month, orderAmount, payment, profitLoss, finalAmount);
        try {
            String result = MonthlyProfitLossModel.saveRecord(record);
            showAlert(Alert.AlertType.INFORMATION, "Success", result);
            resetFields();
            loadMonthlyProfitLossData();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add record.");
        }
    }

    @FXML
    void updateRecord(ActionEvent event) {
        String id = txtID.getText();
        String month = txtMonth.getText();
        int orderAmount = Integer.parseInt(txtOrderAmount.getText());
        double payment = Double.parseDouble(txtPayment.getText());
        double profitLoss = Double.parseDouble(txtProfitLoss.getText());
        double finalAmount = Double.parseDouble(txtFinalAmount.getText());

        if (id.isEmpty() || month.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "ID and Month fields are required!");
            return;
        }

        MonthlyProfitLossDto record = new MonthlyProfitLossDto(id, month, orderAmount, payment, profitLoss, finalAmount);
        try {
            String result = MonthlyProfitLossModel.updateRecord(record);
            showAlert(Alert.AlertType.INFORMATION, "Success", result);
            resetFields();
            loadMonthlyProfitLossData();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update record.");
        }
    }

    @FXML
    void deleteRecord(ActionEvent event) {
        String id = txtID.getText();
        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter ID to delete record.");
            return;
        }

        try {
            String result = MonthlyProfitLossModel.deleteRecord(id);
            showAlert(Alert.AlertType.INFORMATION, "Success", result);
            resetFields();
            loadMonthlyProfitLossData();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete record.");
        }
    }

    @FXML
    void resetFields() {
        txtID.clear();
        txtMonth.clear();
        txtOrderAmount.clear();
        txtPayment.clear();
        txtProfitLoss.clear();
        txtFinalAmount.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
