package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.dto.SuppliersDto;
import org.example.ggg.model.SuppliersModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class SuppliersController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContactInfo;
    public TextField txtSuppliedMaterials;
    public TableView<SuppliersDto> tblSupplier;
    public TableColumn<SuppliersDto, String> colSupplierID;
    public TableColumn<SuppliersDto, String> colName;
    public TableColumn<SuppliersDto, String> colContactInfo;
    public TableColumn<SuppliersDto, String> colAddress;
    public TableColumn<SuppliersDto, String> colSM;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public AnchorPane SupplierPage;
    public TextField txtSearch;
    public Button btnSearch;

    public void initialize() {
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactInfo.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSM.setCellValueFactory(new PropertyValueFactory<>("suppliedMaterials"));

        resetPage();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnAdd.setDisable(true);

                txtId.setText(newValue.getSupplierId());
                txtName.setText(newValue.getName());
                txtContactInfo.setText(newValue.getContactInfo());
                txtAddress.setText(newValue.getAddress());
                txtSuppliedMaterials.setText(newValue.getSuppliedMaterials());
            } else {
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                btnAdd.setDisable(false);

                clearFields();
            }
        });
    }

    private void resetPage() {
        loadSupTable();
        clearFields();
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadSupTable() {
        try {
            ArrayList<SuppliersDto> allSuppliers = SuppliersModel.getAllSuppliers();
            ObservableList<SuppliersDto> supplierList = FXCollections.observableArrayList(allSuppliers);
            tblSupplier.setItems(supplierList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load supplier data: " + e.getMessage());
        }
    }

    public void AddToSupplier(ActionEvent actionEvent) {
        try {
            SuppliersDto suppliersDto = new SuppliersDto(
                    txtId.getText(),
                    txtName.getText(),
                    txtContactInfo.getText(),
                    txtAddress.getText(),
                    txtSuppliedMaterials.getText()
            );
            String result = SuppliersModel.saveSupplier(suppliersDto);
            showAlert("Add Supplier", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to add supplier: " + e.getMessage());
        }
    }

    public void UpdateToSuplier(ActionEvent actionEvent) {
        try {
            SuppliersDto suppliersDto = new SuppliersDto(
                    txtId.getText(),
                    txtName.getText(),
                    txtContactInfo.getText(),
                    txtAddress.getText(),
                    txtSuppliedMaterials.getText()
            );
            String result = SuppliersModel.updateSupplier(suppliersDto);
            showAlert("Update Supplier", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to update supplier: " + e.getMessage());
        }
    }

    public void DeleteToSupplier(ActionEvent actionEvent) {
        try {
            String supplierId = txtId.getText();
            String result = SuppliersModel.deleteSupplier(supplierId);
            showAlert("Delete Supplier", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to delete supplier: " + e.getMessage());
        }
    }

    public void ResetToSupplier(ActionEvent actionEvent) {
        resetPage();
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
        txtName.clear();
        txtContactInfo.clear();
        txtAddress.clear();
        txtSuppliedMaterials.clear();
        tblSupplier.getSelectionModel().clearSelection();
    }

    public void SearchSupplier(ActionEvent actionEvent) {
        String keyword = txtSearch.getText().trim();

        if (keyword.isEmpty()) {
            showAlert("Search Error", "Please enter a Supplier ID or Name to search.");
            return;
        }

        try {
            ArrayList<SuppliersDto> searchResults = SuppliersModel.searchSuppliers(keyword);
            if (searchResults.isEmpty()) {
                showAlert("No Results", "No suppliers found matching the search criteria.");
            } else {
                ObservableList<SuppliersDto> supplierList = FXCollections.observableArrayList(searchResults);
                tblSupplier.setItems(supplierList);
            }
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to search suppliers: " + e.getMessage());
        }
    }
}