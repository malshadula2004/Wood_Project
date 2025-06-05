package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.dto.ReportsDto;
import org.example.ggg.model.ReportsModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReportsController {

    // UI Components
    public AnchorPane ReportPage;
    public TextField txtId;
    public TextField txtTitle;
    public TextField txtContent;
    public TextField txtGeneratedDate;
    public TextField txtGeneratedBy;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public TableView<ReportsDto> tblReport;
    public TableColumn<ReportsDto, String> colReportId;
    public TableColumn<ReportsDto, String> colTitle;
    public TableColumn<ReportsDto, String> colGeneratedBy;
    public TableColumn<ReportsDto, String> colContent;
    public TableColumn<ReportsDto, String> colGeneratedDate;
    public Button btnSearch;
    public TextField txtSearch;

    public void initialize() {
        // Initialize TableView columns
        colReportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colGeneratedBy.setCellValueFactory(new PropertyValueFactory<>("generatedBy"));
        colContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        colGeneratedDate.setCellValueFactory(new PropertyValueFactory<>("generatedDate"));

        // Initialize TableView with data
        resetPage();

        // Add listener for TableView selection
        tblReport.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnAdd.setDisable(true);

                txtId.setText(newValue.getReportId());
                txtTitle.setText(newValue.getTitle());
                txtGeneratedBy.setText(newValue.getGeneratedBy());
                txtContent.setText(newValue.getContent());
                txtGeneratedDate.setText(newValue.getGeneratedDate());
            } else {
                resetButtons();
                clearFields();
            }
        });
    }

    public void loadReportTable() {
        try {
            ArrayList<ReportsDto> allReports = ReportsModel.getAllReports();
            ObservableList<ReportsDto> reportList = FXCollections.observableArrayList(allReports);
            tblReport.setItems(reportList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load reports: " + e.getMessage());
        }
    }

    public void AddToReport(ActionEvent actionEvent) {
        try {
            ReportsDto report = new ReportsDto(
                    txtId.getText(),
                    txtTitle.getText(),
                    txtGeneratedBy.getText(),
                    txtContent.getText(),
                    txtGeneratedDate.getText()
            );
            String result = ReportsModel.saveReport(report);
            showAlert("Add Report", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to add report: " + e.getMessage());
        }
    }

    public void UpdateToReport(ActionEvent actionEvent) {
        try {
            ReportsDto report = new ReportsDto(
                    txtId.getText(),
                    txtTitle.getText(),
                    txtGeneratedBy.getText(),
                    txtContent.getText(),
                    txtGeneratedDate.getText()
            );
            String result = ReportsModel.updateReport(report);
            showAlert("Update Report", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to update report: " + e.getMessage());
        }
    }

    public void DeleteToReport(ActionEvent actionEvent) {
        try {
            String reportId = txtId.getText();
            String result = ReportsModel.deleteReport(reportId);
            showAlert("Delete Report", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to delete report: " + e.getMessage());
        }
    }

    public void ResetToReport(ActionEvent actionEvent) {
        resetPage();
    }

    private void resetPage() {
        clearFields();
        resetButtons();
        loadReportTable();
    }

    private void resetButtons() {
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
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
        txtTitle.clear();
        txtContent.clear();
        txtGeneratedDate.clear();
        txtGeneratedBy.clear();
    }

    public void SearchReports(ActionEvent actionEvent) {
        try {
            String keyword = txtSearch.getText().trim();
            ArrayList<ReportsDto> filteredReports = ReportsModel.searchReports(keyword);
            ObservableList<ReportsDto> reportList = FXCollections.observableArrayList(filteredReports);
            tblReport.setItems(reportList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to search reports: " + e.getMessage());
        }
    }
}




