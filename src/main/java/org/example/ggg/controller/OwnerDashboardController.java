package org.example.ggg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;


public class OwnerDashboardController {

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
    public Button btnOrderSearch;
    public Button btnPaymentt;
    public Button btnSubOrder;
    @FXML
    private BarChart<String, Number> itemBC;
    public BarChart <String, Number>woodBC;
    @FXML
    private BarChart<String, Number> barChart1;

    @FXML
    private BarChart<String, Number> barChart2;


    @FXML
    private PieChart taskProgressPieChart;





    // Initialization method called after FXML loads


    // Initialization method called after FXML loads
    @FXML
    public void initialize() {
        loadItemSalesDataFromDB();
        loadWoodOrderDataToAreaChart();
        loadTaskProgressPieChart(); // <- CORRECT METHOD CALL HERE
        loadInventoryItemsToBarChart(); // Load inventory items into bar chart
        loadWoodInventoryToBarChart(); // Load wood inventory into bar chart
    }

    private void loadWoodInventoryToBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Wood Length (meters)");

        String url = "jdbc:mysql://localhost:3306/wood";
        String user = "root";
        String password = "1234";

        String query = "SELECT wood_name, wood_length FROM wood_inventory";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String woodName = rs.getString("wood_name");
                double woodLength = rs.getDouble("wood_length");
                series.getData().add(new XYChart.Data<>(woodName, woodLength));
            }

            woodBC.getData().clear();
            woodBC.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load wood inventory into bar chart.").show();
        }
    }


    private void loadInventoryItemsToBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Available Quantity");

        String url = "jdbc:mysql://localhost:3306/wood";
        String user = "root";
        String password = "1234";

        String query = "SELECT i.Name, inv.QuantityAvailable " +
                "FROM inventory inv " +
                "JOIN items i ON inv.ItemID = i.ItemID";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String itemName = rs.getString("Name"); // <- Correct column name here
                int quantityAvailable = rs.getInt("QuantityAvailable");
                series.getData().add(new XYChart.Data<>(itemName, quantityAvailable));
            }

            itemBC.getData().clear();
            itemBC.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load inventory items into bar chart.").show();
        }
    }


    // Load Item Sales Data to BarChart (barChart1)
    private void loadItemSalesDataFromDB() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Item Sales Quantity");

        String url = "jdbc:mysql://localhost:3306/wood";
        String user = "root";
        String password = "1234";

        String query = "SELECT ItemName, SUM(Quantity) AS TotalQuantity FROM purchase_orders GROUP BY ItemName";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String itemName = rs.getString("ItemName");
                int totalQuantity = rs.getInt("TotalQuantity");
                series.getData().add(new XYChart.Data<>(itemName, totalQuantity));
            }

            barChart1.getData().clear();
            barChart1.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load sales data from database.").show();
        }
    }

    // Load Wood Order Data to BarChart (barChart2)
    private void loadWoodOrderDataToAreaChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Wood Order Quantity");

        String url = "jdbc:mysql://localhost:3306/wood";
        String user = "root";
        String password = "1234";

        String query = "SELECT name, SUM(quantity) AS TotalQuantity FROM wood_order GROUP BY name";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String woodName = rs.getString("name");
                double totalQuantity = rs.getDouble("TotalQuantity");
                series.getData().add(new XYChart.Data<>(woodName, totalQuantity));
            }

            barChart2.getData().clear();
            barChart2.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load wood order data.").show();
        }
    }

    // Load Task Progress Data to PieChart
    private void loadTaskProgressPieChart() {
        String url = "jdbc:mysql://localhost:3306/wood";
        String user = "root";
        String password = "1234";

        String completedQuery = "SELECT COUNT(*) AS Completed FROM taskassignments WHERE Status = 'Completed'";
        String pendingQuery = "SELECT COUNT(*) AS Pending FROM taskassignments WHERE Status = 'Pending'";
        String inProgressQuery = "SELECT COUNT(*) AS InProgress FROM taskassignments WHERE Status = 'In Progress'";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement completedStmt = connection.prepareStatement(completedQuery);
             PreparedStatement pendingStmt = connection.prepareStatement(pendingQuery);
             PreparedStatement inProgressStmt = connection.prepareStatement(inProgressQuery)) {

            ResultSet rsCompleted = completedStmt.executeQuery();
            ResultSet rsPending = pendingStmt.executeQuery();
            ResultSet rsInProgress = inProgressStmt.executeQuery();

            int completedTasks = 0, pendingTasks = 0, inProgressTasks = 0;

            if (rsCompleted.next()) completedTasks = rsCompleted.getInt("Completed");
            if (rsPending.next()) pendingTasks = rsPending.getInt("Pending");
            if (rsInProgress.next()) inProgressTasks = rsInProgress.getInt("InProgress");

            // PieChart Data Load
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Completed", completedTasks),
                    new PieChart.Data("Pending", pendingTasks),
                    new PieChart.Data("In Progress", inProgressTasks)
            );

            taskProgressPieChart.setData(pieChartData);
            taskProgressPieChart.setTitle("Task Progress");

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load task progress pie chart.").show();
        }

    }

    private void reloadDashboardCharts() {
        loadItemSalesDataFromDB();
        loadWoodOrderDataToAreaChart();
        loadTaskProgressPieChart();
        loadInventoryItemsToBarChart();
        loadWoodInventoryToBarChart();
    }


    @FXML
    private void handleMenuItemAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader;

            if (event.getSource() == itemChair) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ggg/view/Chair.fxml"));
            } else if (event.getSource() == itemTable) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ggg/view/table.fxml"));
            } else if (event.getSource() == itemSofa) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ggg/view/sofa.fxml"));
            } else {
                throw new IllegalArgumentException("Unhandled menu item action.");
            }

            Parent pane = fxmlLoader.load();
            ownerAncerpain.getChildren().retainAll(AllItem);
            ownerAncerpain.getChildren().add(pane);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the view.").show();
        }
    }

    private void navigateTo(String path) {
        try {
            ownerAncerpain.getChildren().clear();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            AnchorPane pane = fxmlLoader.load();

            pane.prefWidthProperty().bind(ownerAncerpain.widthProperty());
            pane.prefHeightProperty().bind(ownerAncerpain.heightProperty());

            pane.setStyle("-fx-background-color: #fff3e6;");

            ownerAncerpain.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page not found: " + path).show();
        }
    }

    // Remaining methods (HomeAction, EmpolyeeAction, OrderAction, etc.) keep unchanged as you wrote
    // ... (copy your existing methods here) ...



// Home Action
    public void HomeAction(ActionEvent actionEvent) {
        // Clear all existing children
        ownerAncerpain.getChildren().clear();

        // Add unique components (ensure all three are defined in your controller)
        ownerAncerpain.getChildren().addAll();
        ownerAncerpain.getChildren().addAll(barChart1, barChart2, taskProgressPieChart, itemBC, woodBC);
        reloadDashboardCharts();
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

    public void orderSearchAcction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/OrderSearch.fxml");
    }

    public void PaymentAcction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/PaymentA.fxml");
    }

    public void subOrderAcction(ActionEvent actionEvent) {
        navigateTo("/org/example/ggg/view/subOrder.fxml");

    }
}

