package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.model.VehicleDetailsDto;
import org.example.ggg.dao.impl.VehicleDetailsModel;

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

        tblVehical.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnAdd.setDisable(true);

                txtId.setText(newValue.getVehicleId());
                txtVehicalNumber.setText(newValue.getVehicleNumber());
                txtType.setText(newValue.getType());
                txtCapacity.setText(newValue.getCapacity());
                txtStatus.setText(newValue.getStatus());
                txtUserId.setText(newValue.getUserId());
            } else {
                clearFields();
            }
        });
    }

    private void resetPage() {
        loadVehicleTable();
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearFields();
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
                    null,
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
        resetPage();
    }

    public void SearchVehical(ActionEvent actionEvent) {
        try {
            String vehicleNumber = txtVehicalNumber.getText(); // Get the entered vehicle number
            if (vehicleNumber.isEmpty()) {
                showAlert("Search Error", "Please enter a Vehicle Number to search!");
                return;
            }

            VehicleDetailsDto vehicle = VehicleDetailsModel.getVehicleByNumber(vehicleNumber);

            if (vehicle != null) {
                // Populate the fields with the vehicle's details
                txtId.setText(vehicle.getVehicleId());
                txtType.setText(vehicle.getType());
                txtCapacity.setText(vehicle.getCapacity());
                txtStatus.setText(vehicle.getStatus());
                txtUserId.setText(vehicle.getUserId());

                // Optionally, highlight the vehicle in the TableView
                tblVehical.getItems().stream()
                        .filter(item -> item.getVehicleNumber().equals(vehicleNumber))
                        .findFirst()
                        .ifPresent(item -> tblVehical.getSelectionModel().select(item));

                showAlert("Search Result", "Vehicle found!");
            } else {
                showAlert("Search Result", "No vehicle found with the provided number!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to search vehicle: " + e.getMessage());
        }
    }

}
