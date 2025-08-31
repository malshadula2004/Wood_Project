package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.SupplierOrderDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderModel {

    /**
     * Save a new supplier order in the database.
     *
     * @param supplierOrderDto The SupplierOrderDto containing order details.
     * @return Success or failure message.
     * @throws SQLException           If a database error occurs.
     * @throws ClassNotFoundException If the database driver is not found.
     */
    public static String saveSupplierOrder(SupplierOrderDto supplierOrderDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Supplier_Order (Date, SupplierID) VALUES (?, ?)";
        boolean isAdded = CrudUtil.execute(sql, supplierOrderDto.getDate(), supplierOrderDto.getSupplierId());
        return isAdded ? "Successfully added supplier order!" : "Failed to add supplier order!";
    }

    /**
     * Update an existing supplier order in the database.
     *
     * @param supplierOrderDto The SupplierOrderDto containing updated details.
     * @return Success or failure message.
     * @throws SQLException           If a database error occurs.
     * @throws ClassNotFoundException If the database driver is not found.
     */
    public static String updateSupplierOrder(SupplierOrderDto supplierOrderDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Supplier_Order SET Date = ?, SupplierID = ? WHERE Supplier_order_id = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                supplierOrderDto.getDate(),
                supplierOrderDto.getSupplierId(),
                supplierOrderDto.getSupplierOrderId());
        return isUpdated ? "Successfully updated supplier order!" : "Failed to update supplier order!";
    }

    /**
     * Delete a supplier order from the database.
     *
     * @param supplierOrderId The ID of the supplier order to delete.
     * @return Success or failure message.
     * @throws SQLException           If a database error occurs.
     * @throws ClassNotFoundException If the database driver is not found.
     */
    public static String deleteSupplierOrder(String supplierOrderId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Supplier_Order WHERE Supplier_order_id = ?";
        boolean isDeleted = CrudUtil.execute(sql, supplierOrderId);
        return isDeleted ? "Successfully deleted supplier order!" : "Failed to delete supplier order!";
    }

    /**
     * Retrieve all supplier orders from the database.
     *
     * @return A list of SupplierOrderDto objects.
     * @throws SQLException           If a database error occurs.
     * @throws ClassNotFoundException If the database driver is not found.
     */
    public static ArrayList<SupplierOrderDto> getAllSupplierOrders() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Supplier_Order";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<SupplierOrderDto> supplierOrderList = new ArrayList<>();
        while (resultSet.next()) {
            supplierOrderList.add(new SupplierOrderDto(
                    resultSet.getString("Supplier_order_id"),
                    resultSet.getString("Date"),
                    resultSet.getString("SupplierID")
            ));
        }
        return supplierOrderList;
    }

    /**
     * Retrieve supplier orders by Supplier ID.
     *
     * @param supplierId The Supplier ID to search by.
     * @return A list of SupplierOrderDto objects matching the Supplier ID.
     * @throws SQLException           If a database error occurs.
     * @throws ClassNotFoundException If the database driver is not found.
     */
    public static ArrayList<SupplierOrderDto> getSupplierOrdersBySupplierId(String supplierId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Supplier_Order WHERE SupplierID = ?";
        ResultSet resultSet = CrudUtil.executeQuery(sql, supplierId);
        ArrayList<SupplierOrderDto> supplierOrderList = new ArrayList<>();
        while (resultSet.next()) {
            supplierOrderList.add(new SupplierOrderDto(
                    resultSet.getString("Supplier_order_id"),
                    resultSet.getString("Date"),
                    resultSet.getString("SupplierID")
            ));
        }
        return supplierOrderList;
    }
}
