package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.CustomersDto;
import org.example.ggg.model.CustomersModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomersController {

    @FXML
    private TextField txtName, txtContactInfo, txtAddress, txtEmail, txtPayment, txtSearch;

    @FXML
    private TableView<CustomersDto> tblCustomer;

    @FXML
    private TableColumn<CustomersDto, String> colCustomerId, colName, colContactInfo, colAddress, colEmail, colPaymentMethod;

    private ObservableList<CustomersDto> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactInfo.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        loadCustomerData();
    }

    private void loadCustomerData() {
        try {
            ArrayList<CustomersDto> customers = CustomersModel.loadCustomers();
            customerList.clear();
            customerList.addAll(customers);
            tblCustomer.setItems(customerList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load customers: " + e.getMessage());
        }
    }




    @FXML
    private void UpdateToCustomer(ActionEvent event) {
        CustomersDto selected = tblCustomer.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setName(txtName.getText());
            selected.setContactInfo(txtContactInfo.getText());
            selected.setAddress(txtAddress.getText());
            selected.setEmail(txtEmail.getText());
            selected.setPaymentMethod(txtPayment.getText());
            try {
                CustomersModel.updateCustomer(selected);
                tblCustomer.refresh();
                clearFields();
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to update customer: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select a customer to update.");
        }
    }

    @FXML
    private void DeleteToCustomer(ActionEvent event) {
        CustomersDto selected = tblCustomer.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                CustomersModel.deleteCustomer(selected.getCustomerId());
                customerList.remove(selected);
                clearFields();
            } catch (SQLException | ClassNotFoundException e) {
                showAlert("Error", "Failed to delete customer: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select a customer to delete.");
        }
    }

    @FXML
    private void SearchCustomer(ActionEvent event) {
        String keyword = txtSearch.getText().trim();
        try {
            ArrayList<CustomersDto> searchResults = CustomersModel.searchCustomer(keyword);
            customerList.clear();
            customerList.addAll(searchResults);
            tblCustomer.setItems(customerList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to search customers: " + e.getMessage());
        }
    }

    private String generateCustomerId() {
        return "C" + (customerList.size() + 1);
    }

    private void clearFields() {
        txtName.clear();
        txtContactInfo.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtPayment.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void ResetToCustomer(ActionEvent actionEvent) {
        tblCustomer.getSelectionModel().clearSelection();
        txtSearch.clear();
        tblCustomer.setItems(customerList);
        clearFields();
    }

    @FXML
    private void AddToCustomer(ActionEvent event) {
        // Generate a new Customer ID
        String id = generateCustomerId();
        String name = txtName.getText().trim();
        String contactInfo = txtContactInfo.getText().trim();
        String address = txtAddress.getText().trim();
        String email = txtEmail.getText().trim();
        String paymentMethod = txtPayment.getText().trim();

        // Validate input
        if (name.isEmpty() || contactInfo.isEmpty() || address.isEmpty() || email.isEmpty() || paymentMethod.isEmpty()) {
            showAlert("Validation Error", "All fields are required.");
            return;
        }

        // Create a new CustomersDto object
        CustomersDto customer = new CustomersDto(id, name, contactInfo, address, email, paymentMethod);

        try {
            // Call the model to add the customer
            String result = CustomersModel.addCustomer(customer);
            if (result.equals("Customer added successfully.")) {
                customerList.add(customer); // Add to observable list
                tblCustomer.refresh(); // Refresh table view
                showAlert("Success", result);
                clearFields(); // Clear form fields
            } else {
                showAlert("Error", result);
            }
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to add customer: " + e.getMessage());
        }
    }
}
