package org.example.ggg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.ggg.dao.impl.UserModel;
import org.example.ggg.model.UserDto;

import java.io.IOException;

public class UserController {

    public AnchorPane SignPage;
    public TextField CreateUserName;
    public TextField createPassword;
    public CheckBox CBOwner;
    public CheckBox CBCashier;
    public CheckBox CBAccontant;

    public void GoToDashboardActtion(ActionEvent actionEvent) throws IOException {
        try {
            String username = CreateUserName.getText().trim();
            String password = createPassword.getText().trim();
            String role = getSelectedRole();

            if (username.isEmpty() || password.isEmpty() || role == null) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required!");
                return;
            }

            UserDto userDto = new UserDto(username, password, role);

            String response = UserModel.Sign(userDto);

            if (response.equals("User successfully registered!")) {
                SignPage.getChildren().clear();
                String dashboardPath = getDashboardPath(role);
                AnchorPane dashboard = FXMLLoader.load(getClass().getResource(dashboardPath));
                SignPage.getChildren().setAll(dashboard);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Registration Status", response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to register user. Please try again.");
        }
    }

    private String getSelectedRole() {
        if (CBOwner.isSelected()) return "Owner";
        if (CBCashier.isSelected()) return "Cashier";
        if (CBAccontant.isSelected()) return "Accountant";
        return null;
    }

    private String getDashboardPath(String role) {
        switch (role) {
            case "Owner":
                return "/org/example/ggg/view/OwnerDashboard.fxml";
            case "Cashier":
                return "/org/example/ggg/view/CashierDashboard.fxml";
            case "Accountant":
                return "/org/example/ggg/view/AccountantDashboard.fxml";
            default:
                return "/org/example/ggg/view/page1.fxml";
        }
    }

    public void GoToHomePageAcction(ActionEvent actionEvent) throws IOException {

        AnchorPane page1 = FXMLLoader.load(getClass().getResource("/org/example/ggg/view/page3.fxml"));
        SignPage.getChildren().setAll(page1);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
