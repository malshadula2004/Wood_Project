package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.dto.UserDto;
import org.example.ggg.model.UserViweModel;

import java.sql.SQLException;

public class userViweController {

    @FXML
    private TableView<UserDto> userTable;

    @FXML
    private TableColumn<UserDto, String> colUserName;

    @FXML
    private TableColumn<UserDto, String> colPassword;

    @FXML
    private TableColumn<UserDto, String> colJobRole;

    private ObservableList<UserDto> userData;

    @FXML
    public void initialize() {
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));

        loadUserData();
    }

    private void loadUserData() {
        try {
            userData = FXCollections.observableArrayList(UserViweModel.getAllUsers());
            userTable.setItems(userData);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load user data: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteSelected() {
        UserDto selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert("No Selection", "Please select a user to delete.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete user " + selectedUser.getUserName() + "?");

        if (confirmAlert.showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {
            try {
                if (UserViweModel.deleteUser(selectedUser.getUserName())) {
                    userData.remove(selectedUser);
                    showAlert("Success", "User deleted successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete user.", Alert.AlertType.ERROR);
                }
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Database error: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
