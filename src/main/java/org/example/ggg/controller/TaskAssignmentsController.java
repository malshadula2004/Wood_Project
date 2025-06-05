package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.dto.TaskAssignmentsDto;
import org.example.ggg.model.TaskAssignmentsModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class TaskAssignmentsController {
    public AnchorPane TaskAsignmentPage;
    public TextField txtId;
    public TextField txtEmployeeId;
    public TextField txtTaskDiscription;
    public TextField txtAssignedDate;
    public TextField txtDueDate;
    public TextField txtStatus;
    public Button btnAdd;
    public Button btnDelete;
    public Button btnUpdate;
    public TableView<TaskAssignmentsDto> tblTA;
    public TableColumn<TaskAssignmentsDto, String> coltaskId;
    public TableColumn<TaskAssignmentsDto, String> colEmployeeid;
    public TableColumn<TaskAssignmentsDto, String> colTaskDescription;
    public TableColumn<TaskAssignmentsDto, String> colAssignedDate;
    public TableColumn<TaskAssignmentsDto, String> colDueDate;
    public TableColumn<TaskAssignmentsDto, String> colStatus;
    public Button btnReset;

    public void initialize() {
        // Initialize TableView columns
        coltaskId.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        colEmployeeid.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colTaskDescription.setCellValueFactory(new PropertyValueFactory<>("taskDescription"));
        colAssignedDate.setCellValueFactory(new PropertyValueFactory<>("assignedDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            resetPage();
        } catch (Exception e) {
            showAlert("Error", "Initialization failed: " + e.getMessage());
        }

        // Add listener for row selection
        tblTA.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Populate fields with selected row data
                txtId.setText(newValue.getTaskId());
                txtEmployeeId.setText(newValue.getEmployeeId());
                txtTaskDiscription.setText(newValue.getTaskDescription());
                txtAssignedDate.setText(newValue.getAssignedDate());
                txtDueDate.setText(newValue.getDueDate());
                txtStatus.setText(newValue.getStatus());

                // Enable Update and Delete buttons, disable Add button
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnAdd.setDisable(true);
            } else {
                // Clear fields and reset buttons
                clearFields();
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                btnAdd.setDisable(false);
            }
        });
    }

    private void resetPage() {
        try {
            loadTaskAssignmentsTable();

            // Reset buttons
            btnAdd.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            // Clear fields
            clearFields();
        } catch (Exception e) {
            showAlert("Error", "Failed to reset page: " + e.getMessage());
        }
    }

    private void loadTaskAssignmentsTable() {
        try {
            ArrayList<TaskAssignmentsDto> allTaskAssignments = TaskAssignmentsModel.getAllTaskAssignments();
            ObservableList<TaskAssignmentsDto> taskAssignmentList = FXCollections.observableArrayList(allTaskAssignments);
            tblTA.setItems(taskAssignmentList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load task assignments: " + e.getMessage());
        }
    }

    public void AddToTA(ActionEvent actionEvent) {
        TaskAssignmentsDto taskAssignmentDto = new TaskAssignmentsDto(
                txtId.getText(),
                txtEmployeeId.getText(),
                txtTaskDiscription.getText(),
                txtAssignedDate.getText(),
                txtDueDate.getText(),
                txtStatus.getText()
        );

        try {
            String result = TaskAssignmentsModel.saveTaskAssignment(taskAssignmentDto);
            showAlert("Add Task Assignment", result);
            resetPage();
        } catch (Exception e) {
            showAlert("Error", "Failed to add task assignment: " + e.getMessage());
        }
    }

    public void DeleteToTA(ActionEvent actionEvent) {
        String taskId = txtId.getText();

        try {
            String result = TaskAssignmentsModel.deleteTaskAssignment(taskId);
            showAlert("Delete Task Assignment", result);
            resetPage();
        } catch (Exception e) {
            showAlert("Error", "Failed to delete task assignment: " + e.getMessage());
        }
    }

    public void UpdateToTA(ActionEvent actionEvent) {
        TaskAssignmentsDto taskAssignmentDto = new TaskAssignmentsDto(
                txtId.getText(),
                txtEmployeeId.getText(),
                txtTaskDiscription.getText(),
                txtAssignedDate.getText(),
                txtDueDate.getText(),
                txtStatus.getText()
        );

        try {
            String result = TaskAssignmentsModel.updateTaskAssignment(taskAssignmentDto);
            showAlert("Update Task Assignment", result);
            resetPage();
        } catch (Exception e) {
            showAlert("Error", "Failed to update task assignment: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtId.clear();
        txtEmployeeId.clear();
        txtTaskDiscription.clear();
        txtAssignedDate.clear();
        txtDueDate.clear();
        txtStatus.clear();
    }

    public void ResetToCoustomer(ActionEvent actionEvent) {
        resetPage();
    }
}
