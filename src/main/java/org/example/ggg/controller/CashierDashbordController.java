package org.example.ggg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class CashierDashbordController {
    public MenuButton AllItem;
    public MenuItem itemChair;
    public MenuItem itemSofa;
    public MenuItem itemTable;
    public AnchorPane OwnerDashboard;
    public Button btnHomee;
    public Button btnHome;
    public Button btnCustomer;
    public Button btnEmpolyee;
    public Button btnInventory;
    public Button btnOrde;
    public Button btnSupliyer;
    public Button btnItem;
    public Button btnWood;
    public Button btnReport;
    public Button btnSupplierOrder;
    public Button btnTaskAssignment;
    public Button btnAttendance;
    public Button btnVehical;
    public Button btnDelivery;
    public Button btnPayment;
    public AnchorPane ownerAncerpain;
    public ImageView btnSofa;
    public ImageView AllWood;
    public Button btnWoods;
    public Button btnLoginOut;
    public Button btnSetting;
    public ImageView woodPalat1;
    public Button btnEmail;
    public MenuButton btnInventorymenu;
    public MenuItem menuInventory;
    public MenuItem menuWoodInventory;

    // MenuItem Action Handler


    @FXML
    private void handleMenuItemAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader;

            if (event.getSource() == itemChair) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ggg/view/Chair.fxml"));
            } else if (event.getSource() == itemTable) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ggg/view/table.fxml"));
            } else {
                throw new IllegalArgumentException("Unhandled menu item action.");
            }

            Parent pane = fxmlLoader.load();
            ownerAncerpain.getChildren().retainAll(AllItem);
            ownerAncerpain.getChildren().add(pane); // Add the new content

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the view.").show();
        }
    }


    // Navigation Method
    private void navigateTo(String path) {
        try {
            ownerAncerpain.getChildren().clear();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            AnchorPane pane = fxmlLoader.load();

            // Bind properties for responsive layout
            pane.prefWidthProperty().bind(ownerAncerpain.widthProperty());
            pane.prefHeightProperty().bind(ownerAncerpain.heightProperty());

            // Set background color
            pane.setStyle("-fx-background-color: #F5E1C8;");

            ownerAncerpain.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page not found: " + path).show();
        }
    }

    // Home Action
    public void HomeAction(ActionEvent actionEvent) {

        ownerAncerpain.getChildren().retainAll(btnWoods);
    }


    // Other Navigation Actions
    public void EmpolyeeAction(ActionEvent actionEvent) throws IOException {
        navigateTo("/org/example/ggg/view/Employees.fxml");


    }



    public void OrderAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/PurchaseOrders.fxml");
    }

    public void SuplierAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/Suppliers.fxml");
    }

    public void ItemAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/Item.fxml");
    }

    public void WoodAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/Wood.fxml");
    }

    public void ReportAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/Reports.fxml");
    }

    public void SuplierOrderAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/SupplierOrder.fxml");
    }

    public void AttendenceAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/Attendance.fxml");
    }

    public void TaskAssignmentAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/TaskAssignments.fxml");
    }

    public void vehicalAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/VehicleDetails.fxml");
    }

    public void DeliveryAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/Delivery.fxml");
    }

    public void PaymentAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/Payments.fxml");
    }

    public void customerAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/Customer.fxml");
    }

    public void woodsActtion(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ggg/view/WoodPic.fxml"));
            Parent root = fxmlLoader.load();

            // Keep the buttons so they are not cleared
            ownerAncerpain.getChildren().retainAll(btnWoods, AllItem);
            ownerAncerpain.getChildren().add(root); // Add the new content
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load WoodPic view.").show();
        }
    }

    // Apply Shadow Effect for btnSofa
    @FXML
    public void applyShadowEffect(MouseEvent event) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setRadius(20);
        shadow.setSpread(0.5);

        DropShadow blueShadow = new DropShadow();
        blueShadow.setColor(Color.BLUE);
        blueShadow.setRadius(15);
        blueShadow.setSpread(0.3);

        shadow.setInput(blueShadow);
        btnSofa.setEffect(shadow);
    }

    // Remove Shadow Effect for btnSofa
    @FXML
    public void removeShadowEffect(MouseEvent event) {
        btnSofa.setEffect(null);
    }

    // Apply Shadow Effect for AllWood
    @FXML
    public void applyShadowEffecttt(MouseEvent mouseEvent) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setRadius(20);
        shadow.setSpread(0.5);

        DropShadow blueShadow = new DropShadow();
        blueShadow.setColor(Color.BLUE);
        blueShadow.setRadius(15);
        blueShadow.setSpread(0.3);

        shadow.setInput(blueShadow);
        AllWood.setEffect(shadow);

    }

    // Remove Shadow Effect for AllWood
    @FXML
    public void removeShadowEffecttt(MouseEvent mouseEvent) {
        AllWood.setEffect(null);
    }

    // Logout Action
    public void LoginOutActttion(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ggg/view/Page1.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to log out.").show();
        }
    }

    // Setting Action
    public void SettingACttion(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/Settings.fxml");
    }

    public void emailActtion(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ggg/view/Email.fxml"));
            Parent root = loader.load();

            Stage emailStage = new Stage();
            emailStage.setTitle("Send Email");
            emailStage.setScene(new Scene(root));
            emailStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load email view.").show();
        }
    }


    public void handleMenuItemActionc(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ggg/view/userViwe.fxml"));
            Parent pane = fxmlLoader.load();
            ownerAncerpain.getChildren().retainAll(AllItem);
            ownerAncerpain.getChildren().add(pane); // Add the new content
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the view.").show();
        }
    }

    public void handleMenuItemActiona(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ggg/view/table.fxml"));
            Parent pane = fxmlLoader.load();
            ownerAncerpain.getChildren().retainAll(AllItem);
            ownerAncerpain.getChildren().add(pane); // Add the new content
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the view.").show();
        }
    }

    public void navigateToInventory(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/Inventory.fxml");
    }

    public void navigateToWoodInventory(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/woodInventroy.fxml");
    }

    public void viewOrdersAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/PurchaseOrders.fxml");
    }

    public void createOrderAction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/woodOrder.fxml");
    }
}



