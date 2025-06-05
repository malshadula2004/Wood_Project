package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.dto.VehicleDetailsDto;
import org.example.ggg.model.VehicleDetailsModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDetailsController {

    // UI Components
    public TextField txtId;
    public TextField txtVehicalNumber;
    public TextField txtType;
    public TextField txtCapacity;
    public TextField txtStatus;
    public TextField txtUserId;
    public TableView<VehicleDetailsDto> tblVehical;
    public TableColumn<VehicleDetailsDto, String> colVehicalId;
    public TableColumn<VehicleDetailsDto, String> colVehicalNumber;
    public TableColumn<VehicleDetailsDto, String> colType;
    public TableColumn<VehicleDetailsDto, String> colCapacity;
    public TableColumn<VehicleDetailsDto, String> colStatus;
    public TableColumn<VehicleDetailsDto, String> colUserId;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public AnchorPane VehicalPage;

    public void initialize() {
        // Initialize TableView columns
        colVehicalId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colVehicalNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        try {
            resetPage();
        } catch (Exception e) {
            showAlert("Error", "Initialization failed: " + e.getMessage());
        }

        // Add listener to TableView selection
        tblVehical.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Enable Update and Delete buttons
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnAdd.setDisable(true); // Disable Add button when a row is selected

                // Load selected row's data into the fields
                txtId.setText(newValue.getVehicleId());
                txtVehicalNumber.setText(newValue.getVehicleNumber());
                txtType.setText(newValue.getType());
                txtCapacity.setText(newValue.getCapacity());
                txtStatus.setText(newValue.getStatus());
                txtUserId.setText(newValue.getUserId());
            } else {
                // No row selected, enable Add button and disable others
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                btnAdd.setDisable(false); // Enable Add button when no row is selected

                // Clear the text fields
                clearFields();
            }
        });
    }

    private void resetPage() {
        try {
            loadVehicleTable();

            // Enable/disable buttons as needed
            btnAdd.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            clearFields();
        } catch (Exception e) {
            showAlert("Error", "Failed to reset page: " + e.getMessage());
        }
    }

    public void loadVehicleTable() {
        try {
            ArrayList<VehicleDetailsDto> allVehicles = VehicleDetailsModel.getAllVehicles();
            ObservableList<VehicleDetailsDto> vehicleList = FXCollections.observableArrayList(allVehicles);
            tblVehical.setItems(vehicleList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load vehicle data: " + e.getMessage());
        }
    }

    public void AddToVehical(ActionEvent actionEvent) {
        try {
            VehicleDetailsDto vehicleDetailsDto = new VehicleDetailsDto(
                    txtId.getText(),
                    txtVehicalNumber.getText(),
                    txtType.getText(),
                    txtCapacity.getText(),
                    txtStatus.getText(),
                    txtUserId.getText()
            );
            String result = VehicleDetailsModel.saveVehicle(vehicleDetailsDto);
            showAlert("Add Vehicle", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to add vehicle: " + e.getMessage());
        }
    }

    public void UpdateToVehical(ActionEvent actionEvent) {
        try {
            VehicleDetailsDto vehicleDetailsDto = new VehicleDetailsDto(
                    txtId.getText(),
                    txtVehicalNumber.getText(),
                    txtType.getText(),
                    txtCapacity.getText(),
                    txtStatus.getText(),
                    txtUserId.getText()
            );
            String result = VehicleDetailsModel.updateVehicle(vehicleDetailsDto);
            showAlert("Update Vehicle", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to update vehicle: " + e.getMessage());
        }
    }

    public void DeleteToVehical(ActionEvent actionEvent) {
        try {
            String vehicleId = txtId.getText();
            String result = VehicleDetailsModel.deleteVehicle(vehicleId);
            showAlert("Delete Vehicle", result);
            resetPage();
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to delete vehicle: " + e.getMessage());
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
        txtVehicalNumber.clear();
        txtType.clear();
        txtCapacity.clear();
        txtStatus.clear();
        txtUserId.clear();
    }

    public void ResetToVehical(ActionEvent actionEvent) {
        try {
            resetPage();
        } catch (Exception e) {
            showAlert("Error", "Failed to reset page: " + e.getMessage());
        }
    }
}
