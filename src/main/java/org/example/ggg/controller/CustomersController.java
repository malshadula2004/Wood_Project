package org.example.ggg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ggg.bo.BOFactory;
import org.example.ggg.bo.CustomerBO;
import org.example.ggg.model.CustomersDto;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.example.ggg.bo.BOFactory.getInstance;

public class CustomersController {

    @FXML
    private TextField txtName, txtContactInfo, txtAddress, txtEmail, txtPayment, txtSearch;

    @FXML
    private TableView<CustomersDto> tblCustomer;

    @FXML
    private TableColumn<CustomersDto, String> colCustomerId, colName, colContactInfo, colAddress, colEmail, colPaymentMethod;

    private ObservableList<CustomersDto> customerList = FXCollections.observableArrayList();

    CustomerBO customerBO = (CustomerBO) getInstance(BOFactory.BOTypes.CUSTOMER);

    @FXML
    public void initialize() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactInfo.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        loadCustomerData();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
            }
        });
    }

    private void populateFields(CustomersDto customer) {
        txtName.setText(customer.getName());
        txtContactInfo.setText(customer.getContactInfo());
        txtAddress.setText(customer.getAddress());
        txtEmail.setText(customer.getEmail());
        txtPayment.setText(customer.getPaymentMethod());
    }

    private void loadCustomerData() {
        try {
            ArrayList<CustomersDto> customers = customerBO.getAllCustomers();
            customerList.clear();
            customerList.addAll(customers);
            tblCustomer.setItems(customerList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load customers: " + e.getMessage());
        }
    }

    @FXML
    private void AddToCustomer(ActionEvent event) {
        String id = generateCustomerId();
        String name = txtName.getText().trim();
        String contactInfo = txtContactInfo.getText().trim();
        String address = txtAddress.getText().trim();
        String email = txtEmail.getText().trim();
        String paymentMethod = txtPayment.getText().trim();

        if (!validateInputs(name, contactInfo, address, email, paymentMethod)) {
            return;
        }

        CustomersDto customer = new CustomersDto(id, name, contactInfo, address, email, paymentMethod);

        try {
            if (customerBO.addCustomer(customer)) {
                customerList.add(customer);
                tblCustomer.refresh();
                showAlert("Success", "Customer added successfully.");
                clearFields();
            } else {
                showAlert("Error", "Failed to add customer.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to add customer: " + e.getMessage());
        }
    }

    @FXML
    private void UpdateToCustomer(ActionEvent event) {
        CustomersDto selected = tblCustomer.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String name = txtName.getText().trim();
            String contactInfo = txtContactInfo.getText().trim();
            String address = txtAddress.getText().trim();
            String email = txtEmail.getText().trim();
            String paymentMethod = txtPayment.getText().trim();

            if (!validateInputs(name, contactInfo, address, email, paymentMethod)) {
                return;
            }

            selected.setName(name);
            selected.setContactInfo(contactInfo);
            selected.setAddress(address);
            selected.setEmail(email);
            selected.setPaymentMethod(paymentMethod);

            try {
                if (customerBO.updateCustomer(selected)) {
                    tblCustomer.refresh();
                    clearFields();
                } else {
                    showAlert("Error", "Failed to update customer.");
                }
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
                if (customerBO.deleteCustomer(selected.getCustomerId())) {
                    customerList.remove(selected);
                    clearFields();
                } else {
                    showAlert("Error", "Failed to delete customer.");
                }
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
            ArrayList<CustomersDto> searchResults = customerBO.searchCustomers(keyword);
            customerList.clear();
            customerList.addAll(searchResults);
            tblCustomer.setItems(customerList);
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Failed to search customers: " + e.getMessage());
        }
    }

    private boolean validateInputs(String name, String contactInfo, String address, String email, String paymentMethod) {
        if (name.isEmpty() || contactInfo.isEmpty() || address.isEmpty() || email.isEmpty() || paymentMethod.isEmpty()) {
            showAlert("Validation Error", "All fields are required.");
            return false;
        }
        if (!contactInfo.matches("\\d{10}")) {
            showAlert("Validation Error", "Contact Info must be a 10-digit number.");
            return false;
        }
        if (!email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            showAlert("Validation Error", "Invalid email format.");
            return false;
        }
        return true;
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
}
