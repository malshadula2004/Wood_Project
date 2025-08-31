package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.DeliveryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryModel {

    // Save Delivery
    public static String saveDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Delivery (Purchase_Orders_id, DeliveryDate, EmployeeID, DeliveryStatus) VALUES (?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,
                deliveryDto.getPurchaseOrdersId(),
                deliveryDto.getDeliveryDate(),
                deliveryDto.getEmployeeId(),
                deliveryDto.getDeliveryStatus()
        );
        return isAdded ? "Successfully added delivery!" : "Failed to add delivery!";
    }

    // Update Delivery
    public static String updateDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Delivery SET Purchase_Orders_id = ?, DeliveryDate = ?, EmployeeID = ?, DeliveryStatus = ? WHERE DeliveryID = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                deliveryDto.getPurchaseOrdersId(),
                deliveryDto.getDeliveryDate(),
                deliveryDto.getEmployeeId(),
                deliveryDto.getDeliveryStatus(),
                deliveryDto.getDeliveryId()
        );
        return isUpdated ? "Successfully updated delivery!" : "Failed to update delivery!";
    }

    // Delete Delivery
    public static String deleteDelivery(String deliveryId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Delivery WHERE DeliveryID = ?";
        boolean isDeleted = CrudUtil.execute(sql, deliveryId);
        return isDeleted ? "Successfully deleted delivery!" : "Failed to delete delivery!";
    }

    // Get all deliveries
    public static ArrayList<DeliveryDto> getAllDeliveries() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Delivery";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<DeliveryDto> deliveries = new ArrayList<>();

        while (resultSet.next()) {
            deliveries.add(new DeliveryDto(
                    resultSet.getString("DeliveryID"),
                    resultSet.getString("Purchase_Orders_id"),
                    resultSet.getString("DeliveryDate"),
                    resultSet.getString("EmployeeID"),
                    resultSet.getString("DeliveryStatus")
            ));
        }
        return deliveries;
    }

    // Search Deliveries by DeliveryID or Purchase_Orders_id (case insensitive)
    public static ArrayList<DeliveryDto> searchDeliveries(String searchText) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Delivery WHERE LOWER(DeliveryID) LIKE ? OR LOWER(Purchase_Orders_id) LIKE ?";
        ResultSet resultSet = CrudUtil.executeQuery(sql, "%" + searchText.toLowerCase() + "%", "%" + searchText.toLowerCase() + "%");
        ArrayList<DeliveryDto> deliveries = new ArrayList<>();

        while (resultSet.next()) {
            deliveries.add(new DeliveryDto(
                    resultSet.getString("DeliveryID"),
                    resultSet.getString("Purchase_Orders_id"),
                    resultSet.getString("DeliveryDate"),
                    resultSet.getString("EmployeeID"),
                    resultSet.getString("DeliveryStatus")
            ));
        }
        return deliveries;
    }

    // Get all Purchase Order IDs for ComboBox
    public static ArrayList<String> getAllPurchaseOrderIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT purchaseOrderID FROM Purchase_Orders";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<String> purchaseOrderIds = new ArrayList<>();

        while (resultSet.next()) {
            purchaseOrderIds.add(resultSet.getString("purchaseOrderID"));
        }
        return purchaseOrderIds;
    }

    // Get all Employee IDs for ComboBox
    public static ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT EmployeeID FROM Employees";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<String> employeeIds = new ArrayList<>();

        while (resultSet.next()) {
            employeeIds.add(resultSet.getString("EmployeeID"));
        }
        return employeeIds;
    }
}
