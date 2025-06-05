package org.example.ggg.model;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.DeliveryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryModel {

    // Save Delivery
    public static String saveDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Delivery ( Purchase_Orders_id, DeliveryDate, EmployeeID, DeliveryStatus) VALUES ( ?, ?, ?, ?)";
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
    public static String deleteDelivery(int deliveryId) throws SQLException, ClassNotFoundException {
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
    public static ArrayList<DeliveryDto> searchDeliveries(String searchText) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Delivery WHERE LOWER(DeliveryID) LIKE ? OR LOWER(Purchase_Orders_id) LIKE ?";
        ResultSet resultSet = CrudUtil.executeQuery(sql, "%" + searchText + "%", "%" + searchText + "%");
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

}
