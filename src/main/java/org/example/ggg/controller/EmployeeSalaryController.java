package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.dto.EmployeeSalaryDto;
import org.example.ggg.model.EmployeeSalaryModel;

import java.sql.SQLException;

public class EmployeeSalaryController {

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtMonthlySalary;

    @FXML
    private TableView<EmployeeSalaryDto> tblEmployeeSalary;

    @FXML
    private TableColumn<EmployeeSalaryDto, String> colEmployeeId;

    @FXML
    private TableColumn<EmployeeSalaryDto, String> colEmployeeName;

    @FXML
    private TableColumn<EmployeeSalaryDto, String> colMonthlySalary;

    private final ObservableList<EmployeeSalaryDto> employeeSalaryList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize Table Columns
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colMonthlySalary.setCellValueFactory(new PropertyValueFactory<>("monthlySalary"));

        // Load Data
        loadEmployeeSalaries();
    }

    private void loadEmployeeSalaries() {
        try {
            employeeSalaryList.setAll(EmployeeSalaryModel.getAllEmployeeSalaries());
            tblEmployeeSalary.setItems(employeeSalaryList);
        } catch (SQLException | ClassNotFoundException e) {
            showError("Error", "Failed to load employee salary data.");
        }
    }

    @FXML
    private void addEmployeeSalary(ActionEvent event) {
        try {
            EmployeeSalaryDto dto = new EmployeeSalaryDto(
                    txtEmployeeId.getText(),
                    txtEmployeeName.getText(),
                    txtMonthlySalary.getText()
            );

            String message = EmployeeSalaryModel.saveEmployeeSalary(dto);
            showInfo("Success", message);
            clearFields();
            loadEmployeeSalaries();
        } catch (SQLException | ClassNotFoundException e) {
            showError("Error", "Failed to add employee salary.");
        }
    }

    @FXML
    private void updateEmployeeSalary(ActionEvent event) {
        try {
            EmployeeSalaryDto dto = new EmployeeSalaryDto(
                    txtEmployeeId.getText(),
                    txtEmployeeName.getText(),
                    txtMonthlySalary.getText()
            );

            String message = EmployeeSalaryModel.updateEmployeeSalary(dto);
            showInfo("Success", message);
            clearFields();
            loadEmployeeSalaries();
        } catch (SQLException | ClassNotFoundException e) {
            showError("Error", "Failed to update employee salary.");
        }
    }

    @FXML
    private void deleteEmployeeSalary(ActionEvent event) {
        try {
            String employeeId = txtEmployeeId.getText();
            String message = EmployeeSalaryModel.deleteEmployeeSalary(employeeId);
            showInfo("Success", message);
            clearFields();
            loadEmployeeSalaries();
        } catch (SQLException | ClassNotFoundException e) {
            showError("Error", "Failed to delete employee salary.");
        }
    }

    @FXML
    private void resetEmployeeSalary(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void generateReport(ActionEvent event) {
        showInfo("Info", "Report generation is not implemented yet.");
    }

    private void clearFields() {
        txtEmployeeId.clear();
        txtEmployeeName.clear();
        txtMonthlySalary.clear();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
