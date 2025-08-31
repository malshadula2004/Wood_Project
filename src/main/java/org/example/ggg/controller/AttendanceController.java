package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;
import org.example.ggg.dao.impl.AttendanceModel;
import org.example.ggg.model.AttendanceDto;

import java.time.LocalDate;
import java.util.List;

public class AttendanceController {

    @FXML
    private TextField txtAttendanceId;

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private DatePicker datePickerDate; // Replace TextField with DatePicker

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtRemarks;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<AttendanceDto> tblAttendance;

    @FXML
    private TableColumn<AttendanceDto, String> colAttendanceId;

    @FXML
    private TableColumn<AttendanceDto, String> colEmployeeId;

    @FXML
    private TableColumn<AttendanceDto, String> colDate;

    @FXML
    private TableColumn<AttendanceDto, String> colStatus;

    @FXML
    private TableColumn<AttendanceDto, String> colRemarks;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    public void initialize() {
        colAttendanceId.setCellValueFactory(cellData -> cellData.getValue().attendanceIdProperty());
        colEmployeeId.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty());
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        colRemarks.setCellValueFactory(cellData -> cellData.getValue().remarksProperty());

        loadAttendanceTable();
        loadEmployeeIds();
        toggleButtons(true);

        tblAttendance.setOnMouseClicked(this::handleTableClick);

        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> searchAttendance(newVal));
    }

    private void loadAttendanceTable() {
        List<AttendanceDto> attendanceList = AttendanceModel.getAllAttendance();
        ObservableList<AttendanceDto> observableList = FXCollections.observableArrayList(attendanceList);
        tblAttendance.setItems(observableList);
    }

    private void loadEmployeeIds() {
        List<String> employeeIds = AttendanceModel.getAllEmployeeIds();
        ObservableList<String> ids = FXCollections.observableArrayList(employeeIds);
        cmbEmployeeId.setItems(ids);
    }

    @FXML
    public void AddToAttendance(ActionEvent actionEvent) {
        if (isInputValid()) {
            AttendanceDto attendance = new AttendanceDto(
                    null,
                    cmbEmployeeId.getValue(),
                    datePickerDate.getValue().toString(), // Convert LocalDate to String
                    txtStatus.getText().trim(),
                    txtRemarks.getText().trim()
            );
            String resultMessage = AttendanceModel.addAttendance(attendance);
            showAlert("Info", resultMessage);
            loadAttendanceTable();
            clearFields();
        }
    }

    @FXML
    public void UpdateToAttendance(ActionEvent actionEvent) {
        if (isInputValid() && !txtAttendanceId.getText().trim().isEmpty()) {
            AttendanceDto attendance = new AttendanceDto(
                    txtAttendanceId.getText().trim(),
                    cmbEmployeeId.getValue(),
                    datePickerDate.getValue().toString(), // Convert LocalDate to String
                    txtStatus.getText().trim(),
                    txtRemarks.getText().trim()
            );
            String resultMessage = AttendanceModel.updateAttendance(attendance);
            showAlert("Info", resultMessage);
            loadAttendanceTable();
            clearFields();
            toggleButtons(true);
        }
    }

    @FXML
    public void DeleteToAttendance(ActionEvent actionEvent) {
        String attendanceId = txtAttendanceId.getText().trim();
        if (!attendanceId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete Attendance ID: " + attendanceId + "?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                String resultMessage = AttendanceModel.deleteAttendance(attendanceId);
                showAlert("Info", resultMessage);
                loadAttendanceTable();
                clearFields();
                toggleButtons(true);
            }
        } else {
            showAlert("Error", "Please select a record to delete.");
        }
    }

    private void handleTableClick(MouseEvent event) {
        AttendanceDto selected = tblAttendance.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtAttendanceId.setText(selected.getAttendanceId());
            cmbEmployeeId.setValue(selected.getEmployeeId());
            datePickerDate.setValue(LocalDate.parse(selected.getDate())); // Convert String to LocalDate
            txtStatus.setText(selected.getStatus());
            txtRemarks.setText(selected.getRemarks());
            toggleButtons(false);
        }
    }

    private void toggleButtons(boolean isAddMode) {
        btnAdd.setDisable(!isAddMode);
        btnUpdate.setDisable(isAddMode);
        btnDelete.setDisable(isAddMode);
    }

    private void clearFields() {
        txtAttendanceId.clear();
        cmbEmployeeId.setValue(null);
        datePickerDate.setValue(null); // Clear DatePicker
        txtStatus.clear();
        txtRemarks.clear();
    }

    private boolean isInputValid() {
        if (cmbEmployeeId.getValue() == null) {
            showAlert("Validation Error", "Employee ID is required.");
            return false;
        }
        if (datePickerDate.getValue() == null) {
            showAlert("Validation Error", "Date is required.");
            return false;
        }
        if (txtStatus.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Status is required.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void resetAttendance(ActionEvent actionEvent) {
        clearFields();
        toggleButtons(true);
    }

    @FXML
    public void SearchAttendance(ActionEvent actionEvent) {
        searchAttendance(txtSearch.getText().trim());
    }

    private void searchAttendance(String keyword) {
        List<AttendanceDto> filteredList = AttendanceModel.searchAttendance(keyword);
        ObservableList<AttendanceDto> observableList = FXCollections.observableArrayList(filteredList);
        tblAttendance.setItems(observableList);
    }
}
