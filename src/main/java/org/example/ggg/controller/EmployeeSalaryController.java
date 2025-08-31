package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.model.EmployeeSalaryDto;
import org.example.ggg.dao.impl.EmployeeSalaryModel;

import java.sql.SQLException;

public class EmployeeSalaryController {

    public AnchorPane EmployeeSalaryPage;
    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtMonthlySalary;

    @FXML
    private TextField txtWD;

    @FXML
    private TextField txtTA;

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
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colMonthlySalary.setCellValueFactory(new PropertyValueFactory<>("monthlySalary"));

        loadEmployeeSalaries();

        txtEmployeeId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                loadEmployeeData(newValue);
            } else {
                clearFields();
            }
        });
    }

    private void loadEmployeeData(String employeeId) {
        try {
            String employeeName = EmployeeSalaryModel.getEmployeeName(employeeId);
            int workedDays = EmployeeSalaryModel.getDaysWorkedThisMonth(employeeId);
            int taskDays = EmployeeSalaryModel.getTaskCompletionDays(employeeId);

            int totalDays = workedDays + taskDays;
            int salary = totalDays * 1500;

            txtEmployeeName.setText(employeeName);
            txtWD.setText(String.valueOf(workedDays));
            txtTA.setText(String.valueOf(taskDays));
            txtMonthlySalary.setText(String.valueOf(salary));

        } catch (SQLException | ClassNotFoundException e) {
            showError("Error", "Failed to load employee data.");
        }
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
        txtWD.clear();
        txtTA.clear();
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
