package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.PaymentsDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentsModel {

    public static String savePayment(PaymentsDto payment) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Payments (Purchase_Orders_id ,CustomerID, PaymentDate, Amount, PaymentMethod) VALUES (?, ?, ?, ? ,?)";
        boolean isAdded = CrudUtil.execute(sql, payment.getPurchaseOrdersId(),payment.getCustomerId(), payment.getPaymentDate(), payment.getAmount(), payment.getPaymentMethod());
        return isAdded ? "Payment added successfully" : "Failed to add payment";
    }

    public static String updatePayment(PaymentsDto payment) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Payments SET Purchase_Orders_id = ?, CustomerID = ?, PaymentDate = ?, Amount = ?, PaymentMethod = ? WHERE PaymentID = ?";
        boolean isUpdated = CrudUtil.execute(sql, payment.getPurchaseOrdersId(), payment.getCustomerId(), payment.getPaymentDate(), payment.getAmount(), payment.getPaymentMethod(), payment.getPaymentId());
        return isUpdated ? "Payment updated successfully" : "Failed to update payment";
    }

    public static String deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Payments WHERE PaymentID = ?";
        boolean isDeleted = CrudUtil.execute(sql, paymentId);
        return isDeleted ? "Payment deleted successfully" : "Failed to delete payment";
    }

    public static ArrayList<PaymentsDto> getAllPayments() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Payments";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<PaymentsDto> paymentsList = new ArrayList<>();

        while (resultSet.next()) {
            paymentsList.add(new PaymentsDto(
                    resultSet.getString("PaymentID"),
                    resultSet.getString("Purchase_Orders_id"),
                    resultSet.getString("CustomerID"),
                    resultSet.getString("PaymentDate"),
                    resultSet.getString("Amount"),
                    resultSet.getString("PaymentMethod")
            ));
        }
        return paymentsList;
    }

    public static ArrayList<PaymentsDto> searchPaymentsByEmployee(String customerId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Payments WHERE CustomerID = ?";
        ResultSet resultSet = CrudUtil.executeQuery(sql, customerId);
        ArrayList<PaymentsDto> paymentsList = new ArrayList<>();

        while (resultSet.next()) {
            paymentsList.add(new PaymentsDto(
                    resultSet.getString("PaymentID"),
                    resultSet.getString("Purchase_Orders_id"),
                    resultSet.getString("CustomerID"),
                    resultSet.getString("PaymentDate"),
                    resultSet.getString("Amount"),
                    resultSet.getString("PaymentMethod")
            ));
        }
        return paymentsList;
    }

    public static ArrayList<String> getEmployeeIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT CustomerID FROM Customers";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<String> customerIds = new ArrayList<>();
        while (resultSet.next()) {
            customerIds.add(resultSet.getString("CustomerID"));
        }
        return customerIds;
    }
}
