package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.model.TaskAssignmentsDto;
import org.example.ggg.dao.impl.TaskAssignmentsModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskAssignmentsController {

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private DatePicker dateAssigned;

    @FXML
    private DatePicker dateDue;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtTaskDescription;

    @FXML
    private TextField txtStatus;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnReset;

    @FXML
    private TableView<TaskAssignmentsDto> tblTA;

    @FXML
    private TableColumn<TaskAssignmentsDto, String> coltaskId;

    @FXML
    private TableColumn<TaskAssignmentsDto, String> colEmployeeid;

    @FXML
    private TableColumn<TaskAssignmentsDto, String> colTaskDescription;

    @FXML
    private TableColumn<TaskAssignmentsDto, String> colAssignedDate;

    @FXML
    private TableColumn<TaskAssignmentsDto, String> colDueDate;

    @FXML
    private TableColumn<TaskAssignmentsDto, String> colStatus;

    @FXML
    public void initialize() {
        // Setup table columns
        coltaskId.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        colEmployeeid.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colTaskDescription.setCellValueFactory(new PropertyValueFactory<>("taskDescription"));
        colAssignedDate.setCellValueFactory(new PropertyValueFactory<>("assignedDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            // Load employee IDs dynamically from DB
            ArrayList<String> employeeIds = TaskAssignmentsModel.getAllEmployeeIds();
            cmbEmployeeId.setItems(FXCollections.observableArrayList(employeeIds));

            resetPage();
        } catch (Exception e) {
            showAlert("Error", "Initialization failed: " + e.getMessage());
        }

        // Table selection listener
        tblTA.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getTaskId());
                cmbEmployeeId.setValue(newSelection.getEmployeeId());
                txtTaskDescription.setText(newSelection.getTaskDescription());

                if (newSelection.getAssignedDate() != null && !newSelection.getAssignedDate().isEmpty()) {
                    dateAssigned.setValue(LocalDate.parse(newSelection.getAssignedDate()));
                } else {
                    dateAssigned.setValue(null);
                }

                if (newSelection.getDueDate() != null && !newSelection.getDueDate().isEmpty()) {
                    dateDue.setValue(LocalDate.parse(newSelection.getDueDate()));
                } else {
                    dateDue.setValue(null);
                }

                txtStatus.setText(newSelection.getStatus());

                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnAdd.setDisable(true);
            } else {
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
            clearFields();
            btnAdd.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } catch (Exception e) {
            showAlert("Error", "Failed to reset page: " + e.getMessage());
        }
    }

    private void loadTaskAssignmentsTable() throws SQLException, ClassNotFoundException {
        ArrayList<TaskAssignmentsDto> allTaskAssignments = TaskAssignmentsModel.getAllTaskAssignments();
        ObservableList<TaskAssignmentsDto> taskAssignmentList = FXCollections.observableArrayList(allTaskAssignments);
        tblTA.setItems(taskAssignmentList);
    }

    public void AddToTA(ActionEvent actionEvent) {
        String assignedDateStr = dateAssigned.getValue() != null ? dateAssigned.getValue().toString() : "";
        String dueDateStr = dateDue.getValue() != null ? dateDue.getValue().toString() : "";

        TaskAssignmentsDto taskAssignmentDto = new TaskAssignmentsDto(
                txtId.getText(),
                cmbEmployeeId.getValue(),
                txtTaskDescription.getText(),
                assignedDateStr,
                dueDateStr,
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
        String assignedDateStr = dateAssigned.getValue() != null ? dateAssigned.getValue().toString() : "";
        String dueDateStr = dateDue.getValue() != null ? dateDue.getValue().toString() : "";

        TaskAssignmentsDto taskAssignmentDto = new TaskAssignmentsDto(
                txtId.getText(),
                cmbEmployeeId.getValue(),
                txtTaskDescription.getText(),
                assignedDateStr,
                dueDateStr,
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
        cmbEmployeeId.setValue(null);
        txtTaskDescription.clear();
        dateAssigned.setValue(null);
        dateDue.setValue(null);
        txtStatus.clear();
    }

    public void resetToTA(ActionEvent actionEvent) {
        resetPage();
    }
}
