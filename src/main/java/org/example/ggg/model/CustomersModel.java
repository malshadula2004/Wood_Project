package org.example.ggg.model;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.CustomersDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomersModel {

    // Save Customer (Add)
    public static String addCustomer(CustomersDto customer) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Customers (Name, ContactInfo, Address, Email, PaymentMethod) VALUES (?, ?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,
                customer.getName(),
                customer.getContactInfo(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getPaymentMethod()
        );
        return isAdded ? "Customer added successfully." : "Failed to add customer.";
    }

    // Update Customer
    public static String updateCustomer(CustomersDto customer) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Customers SET Name = ?, ContactInfo = ?, Address = ?, Email = ?, PaymentMethod = ? WHERE CustomerID = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                customer.getName(),
                customer.getContactInfo(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getPaymentMethod(),
                customer.getCustomerId()
        );
        return isUpdated ? "Customer updated successfully." : "Failed to update customer.";
    }

    // Delete Customer
    public static String deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Customers WHERE CustomerID = ?";
        boolean isDeleted = CrudUtil.execute(sql, customerId);
        return isDeleted ? "Customer deleted successfully." : "Failed to delete customer.";
    }

    // Load all customers
    public static ArrayList<CustomersDto> loadCustomers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customers";
        ResultSet rs = CrudUtil.executeQuery(sql);
        ArrayList<CustomersDto> customersList = new ArrayList<>();

        while (rs.next()) {
            customersList.add(new CustomersDto(
                    rs.getString("CustomerID"),
                    rs.getString("Name"),
                    rs.getString("ContactInfo"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("PaymentMethod")
            ));
        }
        return customersList;
    }

    // Search customers by ID or Name
    public static ArrayList<CustomersDto> searchCustomer(String keyword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customers WHERE CustomerID LIKE ? OR Name LIKE ?";
        ResultSet rs = CrudUtil.executeQuery(sql, "%" + keyword + "%", "%" + keyword + "%");
        ArrayList<CustomersDto> customersList = new ArrayList<>();

        while (rs.next()) {
            customersList.add(new CustomersDto(
                    rs.getString("CustomerID"),
                    rs.getString("Name"),
                    rs.getString("ContactInfo"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("PaymentMethod")
            ));
        }
        return customersList;
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT CustomerID FROM Customers";
        ResultSet rs = CrudUtil.executeQuery(sql);
        ArrayList<String> customerIds = new ArrayList<>();

        while (rs.next()) {
            customerIds.add(rs.getString("CustomerID"));
        }
        return customerIds;
    }


    public String findNameById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT Name FROM Customers WHERE CustomerID = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, selectedCustomerId);

        if (rs.next()) {
            return rs.getString("Name");
        }
        return null; // Return null if no matching customer is fou
    }
}
