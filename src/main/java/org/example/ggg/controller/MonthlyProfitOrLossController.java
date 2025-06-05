package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.ggg.dto.MonthlyProfitOrLossDto;
import org.example.ggg.model.MonthlyProfitOrLossModel;

import java.sql.SQLException;
import java.util.Optional;

public class MonthlyProfitOrLossController {

    @FXML
    private TextField txtID, txtOrderTotal, txtPaymentTotal, txtLossOrProfitAmount, txtFinal;

    @FXML
    private TableView<MonthlyProfitOrLossDto> tblMonthlyProfitOrLoss;

    @FXML
    private TableColumn<MonthlyProfitOrLossDto, String> colID, colOrderTotal, colPaymentTotal, colLossOrProfitAmount, colFinal;

    private ObservableList<MonthlyProfitOrLossDto> monthlyProfitOrLossList;

    @FXML
    public void initialize() {
        colID.setCellValueFactory(data -> data.getValue().idProperty());
        colOrderTotal.setCellValueFactory(data -> data.getValue().orderTotalProperty());
        colPaymentTotal.setCellValueFactory(data -> data.getValue().paymentTotalProperty());
        colLossOrProfitAmount.setCellValueFactory(data -> data.getValue().lossOrProfitAmountProperty());
        colFinal.setCellValueFactory(data -> data.getValue().finalStatusProperty());

        monthlyProfitOrLossList = FXCollections.observableArrayList();
        tblMonthlyProfitOrLoss.setItems(monthlyProfitOrLossList);

        loadMonthlyProfitOrLossData();
    }

    private void loadMonthlyProfitOrLossData() {
        try {
            monthlyProfitOrLossList.clear();
            monthlyProfitOrLossList.addAll(MonthlyProfitOrLossModel.getAllRecords());
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load data from the database.");
        }
    }

    @FXML
    void addRecord(ActionEvent event) {
        String id = txtID.getText();
        String orderTotal = txtOrderTotal.getText();
        String paymentTotal = txtPaymentTotal.getText();
        String lossOrProfitAmount = txtLossOrProfitAmount.getText();
        String finalStatus = txtFinal.getText();

        if (id.isEmpty() || orderTotal.isEmpty() || paymentTotal.isEmpty() || lossOrProfitAmount.isEmpty() || finalStatus.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required!");
            return;
        }

        MonthlyProfitOrLossDto record = new MonthlyProfitOrLossDto(id, orderTotal, paymentTotal, lossOrProfitAmount, finalStatus);
        try {
            String result = MonthlyProfitOrLossModel.saveRecord(record);
            showAlert(Alert.AlertType.INFORMATION, "Success", result);
            resetFields();
            loadMonthlyProfitOrLossData();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save record.");
        }
    }

    @FXML
    void updateRecord(ActionEvent event) {
        // Similar to addRecord but calling update method
    }

    @FXML
    void deleteRecord(ActionEvent event) {
        // Similar to addRecord but calling delete method
    }

    @FXML
    void resetFields() {
        txtID.clear();
        txtOrderTotal.clear();
        txtPaymentTotal.clear();
        txtLossOrProfitAmount.clear();
        txtFinal.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
