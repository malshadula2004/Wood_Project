package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.dto.EmployeesDto;
import org.example.ggg.model.EmployeesModel;

import java.sql.SQLException;

public class EmployeesController {

    @FXML
    private TextField txtID, txtName, txtRole, txtContactInfo, txtSearch;

    @FXML
    private TableView<EmployeesDto> tblEmployees;

    @FXML
    private TableColumn<EmployeesDto, String> colEmployeeId, colName, colRole, colContactInfo;

    @FXML
    private Button btnAdd, btnUpdate, btnDelete, btnReset, btnSearch;

    private ObservableList<EmployeesDto> employeeList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeesId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colContactInfo.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));

        // Load data
        loadEmployees();

        // Disable update and delete buttons initially
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        // Set listener for table row selection
        tblEmployees.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
    private void AddToEmployees() {
        if (validateInputs()) {
            EmployeesDto employee = new EmployeesDto(null, txtName.getText(), txtRole.getText(), txtContactInfo.getText());
            try {
                String result = EmployeesModel.saveEmployee(employee);
                loadEmployees();
                resetFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", result);
            } catch (SQLException | ClassNotFoundException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add employee: " + e.getMessage());
            }
        }
    }

    @FXML
    private void UpdateToEmployees() {
        EmployeesDto selectedEmployee = tblEmployees.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null && validateInputs()) {
            selectedEmployee.setName(txtName.getText());
            selectedEmployee.setRole(txtRole.getText());
            selectedEmployee.setContactInfo(txtContactInfo.getText());
            try {
                String result = EmployeesModel.updateEmployee(selectedEmployee);
                loadEmployees();
                resetFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", result);
            } catch (SQLException | ClassNotFoundException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update employee: " + e.getMessage());
            }
        }
    }

    @FXML
    private void DeleteToEmployees() {
        EmployeesDto selectedEmployee = tblEmployees.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                String result = EmployeesModel.deleteEmployee(selectedEmployee.getEmployeesId());
                loadEmployees();
                resetFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", result);
            } catch (SQLException | ClassNotFoundException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete employee: " + e.getMessage());
            }
        }
    }

    @FXML
    private void ResetToEmployees() {
        tblEmployees.getSelectionModel().clearSelection();
        txtSearch.clear();
        tblEmployees.setItems(employeeList);
        resetFields();
    }

    @FXML
    private void SearchEmployee() {
        String searchText = txtSearch.getText().trim(); // TextField එකේ පූර්ණ කර ඇති value එක ගන්න
        if (searchText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter an ID or Name to search."); // හිස් වුවහොත් warning alert එකක්
            return;
        }

        // Filter employees based on searchText
        ObservableList<EmployeesDto> filteredList = FXCollections.observableArrayList();
        for (EmployeesDto employee : employeeList) {
            if (employee.getEmployeesId().equalsIgnoreCase(searchText) ||
                    employee.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(employee);
            }
        }

        // Check if results are empty
        if (filteredList.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Results", "No employee found for the given ID or Name."); // ගැලපෙන විශේෂයක් නැති නම්
        } else {
            tblEmployees.setItems(filteredList);
        }
    }
    private void populateFields(EmployeesDto employee) {
        txtID.setText(employee.getEmployeesId());
        txtName.setText(employee.getName());
        txtRole.setText(employee.getRole());
        txtContactInfo.setText(employee.getContactInfo());
    }

    private void resetFields() {
        txtID.clear();
        txtName.clear();
        txtRole.clear();
        txtContactInfo.clear();
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private boolean validateInputs() {
        if (txtName.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Name cannot be empty.");
            return false;
        }
        if (txtRole.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Role cannot be empty.");
            return false;
        }
        if (txtContactInfo.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Contact Info cannot be empty.");
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

    private void loadEmployees() {
        employeeList.clear();
        try {
            employeeList.addAll(EmployeesModel.getAllEmployees());
            tblEmployees.setItems(employeeList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load employees: " + e.getMessage());
        }
    }
}
