package org.example.ggg.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.example.ggg.model.ReportsDto;
import org.example.ggg.dao.impl.ReportsModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportsController {

    public AnchorPane ReportPage;
    public TextField txtId, txtTitle, txtContent, txtGeneratedDate, txtGeneratedBy, txtSearch;
    public Button btnAdd, btnUpdate, btnDelete, btnReset,  btnGenerateReport;
    public TableView<ReportsDto> tblReport;
    public TableColumn<ReportsDto, String> colReportId, colTitle, colGeneratedBy, colContent, colGeneratedDate;
    public Button btnSearchl;

    public void initialize() {
        colReportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colGeneratedBy.setCellValueFactory(new PropertyValueFactory<>("generatedBy"));
        colContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        colGeneratedDate.setCellValueFactory(new PropertyValueFactory<>("generatedDate"));

        resetPage();
        tblReport.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateFields(newValue));
    }

    private void updateFields(ReportsDto selectedReport) {
        if (selectedReport != null) {
            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);

            txtId.setText(selectedReport.getReportId());
            txtTitle.setText(selectedReport.getTitle());
            txtGeneratedBy.setText(selectedReport.getGeneratedBy());
            txtContent.setText(selectedReport.getContent());
            txtGeneratedDate.setText(selectedReport.getGeneratedDate());
        } else {
            resetButtons();
            clearFields();
        }
    }

    public void AddToReport(ActionEvent actionEvent) {
        try {
            ReportsDto report = new ReportsDto(txtId.getText(), txtTitle.getText(), txtGeneratedBy.getText(), txtContent.getText(), txtGeneratedDate.getText());
            showAlert("Add Report", ReportsModel.saveReport(report));
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", e.getMessage());
        }
    }

    public void UpdateToReport(ActionEvent actionEvent) {
        try {
            ReportsDto report = new ReportsDto(txtId.getText(), txtTitle.getText(), txtGeneratedBy.getText(), txtContent.getText(), txtGeneratedDate.getText());
            showAlert("Update Report", ReportsModel.updateReport(report));
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", e.getMessage());
        }
    }

    public void DeleteToReport(ActionEvent actionEvent) {
        try {
            String reportId = txtId.getText();
            showAlert("Delete Report", ReportsModel.deleteReport(reportId));
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", e.getMessage());
        }
    }

    public void ResetToReport(ActionEvent actionEvent) {
        resetPage();
    }



    public void generateReport(ActionEvent actionEvent) {
        ObservableList<ReportsDto> selectedReports = tblReport.getSelectionModel().getSelectedItems();

        if (selectedReports.isEmpty()) {
            selectedReports = tblReport.getItems(); // Select all rows if no row is selected
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                document.add(new Paragraph("All Report Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));

                for (ReportsDto report : selectedReports) {
                    document.add(new Paragraph("-------------------------------------------------"));
                    document.add(new Paragraph("Report ID: " + report.getReportId()));
                    document.add(new Paragraph("Title: " + report.getTitle()));
                    document.add(new Paragraph("Content: " + report.getContent()));
                    document.add(new Paragraph("Generated By: " + report.getGeneratedBy()));
                    document.add(new Paragraph("Generated Date: " + report.getGeneratedDate()));
                }

                document.add(new Paragraph("-------------------------------------------------"));
                document.add(new Paragraph("\n--- End of Reports ---"));

                document.close();
                showAlert("Success", "Reports generated and saved successfully!");

            } catch (DocumentException | IOException e) {
                showAlert("Error", "Failed to generate PDF: " + e.getMessage());
            }
        }
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

    private void clearFields() {
        txtId.clear();
        txtTitle.clear();
        txtContent.clear();
        txtGeneratedBy.clear();
        txtGeneratedDate.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public void loadReportTable() {
        try {
            ArrayList<ReportsDto> allReports = ReportsModel.getAllReports();
            ObservableList<ReportsDto> reportList = FXCollections.observableArrayList(allReports);
            tblReport.setItems(reportList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", e.getMessage());
        }
    }

    public void SearchReports(ActionEvent actionEvent) {
        // Search operation logic
        String keyword = txtSearch.getText();
        try {
            ArrayList<ReportsDto> searchResults = ReportsModel.searchReports(keyword);
            ObservableList<ReportsDto> reportList = FXCollections.observableArrayList(searchResults);
            tblReport.setItems(reportList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", e.getMessage());
        }
    }

}
