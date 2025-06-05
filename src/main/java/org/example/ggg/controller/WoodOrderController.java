package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.TM.woodTM;
import org.example.ggg.dbconnection.DBConnection;
import org.example.ggg.model.CustomersModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WoodOrderController {

    @FXML
    private Button btnAddToCart, btnPlaceOrder, btnReset;

    @FXML
    private ComboBox<String> cmbCustomerId, cmbwoodId;

    @FXML
    private Label lblCustomerName, lblwoodName, lblItemQty, lblItemPrice, lblOrderId, orderDate;

    @FXML
    private TableView<woodTM> tblCart;

    @FXML
    private TableColumn<woodTM, String> colItemId, colName, colQuantity, colPrice, colTotal;
    @FXML
    private TableColumn<woodTM, Button> colAction;

    @FXML
    private TextField txtAddToCartQty;

    private final CustomersModel customerModel = new CustomersModel();
    private ObservableList<woodTM> cartList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            loadCustomerDetails();
            loadWoodDetails();
            setCellValueFactory();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            lblCustomerName.setText("Failed to load data!");
        }
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("woodId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeButton"));
    }

    private void loadCustomerDetails() throws SQLException, ClassNotFoundException {
        ObservableList<String> customerList = FXCollections.observableArrayList();
        String query = "SELECT CustomerID, Name FROM Customers";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int customerId = resultSet.getInt("CustomerID");
                String customerName = resultSet.getString("Name");
                customerList.add(customerId + " - " + customerName);
            }
        }
        cmbCustomerId.setItems(customerList);
    }

    private void loadWoodDetails() throws SQLException, ClassNotFoundException {
        ObservableList<String> woodList = FXCollections.observableArrayList();
        String query = "SELECT wood_id, wood_name, wood_length, price FROM wood_inventory";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int woodId = resultSet.getInt("wood_id");
                String woodName = resultSet.getString("wood_name");
                double woodLength = resultSet.getDouble("wood_length");
                double price = resultSet.getDouble("price");
                woodList.add(String.format("%d - %s (%.2fm, $%.2f)", woodId, woodName, woodLength, price));
            }
        }
        cmbwoodId.setItems(woodList);
    }

    @FXML
    private void btnAddToCartOnAction(ActionEvent event) {
        String woodSelection = cmbwoodId.getSelectionModel().getSelectedItem();
        if (woodSelection == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a wood item!").show();
            return;
        }

        String[] parts = woodSelection.split(" - ");
        String woodId = parts[0];
        String woodName = lblwoodName.getText();
        String unitPrice = lblItemPrice.getText();
        Double requestedQty;

        try {
            requestedQty = Double.parseDouble(txtAddToCartQty.getText());
            double availableQty = Double.parseDouble(lblItemQty.getText());
            if (requestedQty > availableQty) {
                new Alert(Alert.AlertType.WARNING, "Insufficient stock!").show();
                return;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity!").show();
            return;
        }

        double total = requestedQty * Double.parseDouble(unitPrice);
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            cartList.removeIf(item -> item.getWoodId().equals(woodId));
            tblCart.refresh();
        });

        woodTM cartItem = new woodTM(woodId, woodName, String.valueOf(requestedQty), unitPrice, String.format("%.2f", total), removeButton);
        cartList.add(cartItem);
        tblCart.setItems(cartList);

        lblItemQty.setText(String.valueOf(Double.parseDouble(lblItemQty.getText()) - requestedQty));
    }

    @FXML
    private void btnPlaceOrderOnAction(ActionEvent event) {
        if (cartList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Cart is empty!").show();
            return;
        }
        new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
        resetForm();
    }

    @FXML
    private void btnResetOnAction(ActionEvent event) {
        resetForm();
    }

    private void resetForm() {
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbwoodId.getSelectionModel().clearSelection();
        cartList.clear();
        tblCart.setItems(cartList);
        lblCustomerName.setText("");
        lblwoodName.setText("");
        lblItemQty.setText("");
        lblItemPrice.setText("");
        txtAddToCartQty.clear();
    }

    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        if (selectedCustomerId != null) {
            try (Connection connection = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = connection.prepareStatement("SELECT Name FROM Customers WHERE CustomerID = ?")) {
                ps.setString(1, selectedCustomerId.split(" - ")[0]);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    lblCustomerName.setText(rs.getString("Name"));
                } else {
                    lblCustomerName.setText("Not Found");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                lblCustomerName.setText("Error");
            }
        } else {
            lblCustomerName.setText("");
        }
    }

    public void cmbItemOnAction(ActionEvent actionEvent) {
        String selectedWood = cmbwoodId.getSelectionModel().getSelectedItem();
        if (selectedWood != null) {
            String woodId = selectedWood.split(" - ")[0];
            try (Connection connection = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = connection.prepareStatement("SELECT wood_name, wood_length, price FROM wood_inventory WHERE wood_id = ?")) {
                ps.setString(1, woodId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    lblwoodName.setText(rs.getString("wood_name"));
                    lblItemQty.setText(String.valueOf(rs.getDouble("wood_length")));
                    lblItemPrice.setText(String.format("%.2f", rs.getDouble("price")));
                } else {
                    lblwoodName.setText("Not Found");
                    lblItemQty.setText("0");
                    lblItemPrice.setText("0.00");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                lblwoodName.setText("Error");
                lblItemQty.setText("0");
                lblItemPrice.setText("0.00");
            }
        } else {
            lblwoodName.setText("");
            lblItemQty.setText("");
            lblItemPrice.setText("");
        }
    }
}