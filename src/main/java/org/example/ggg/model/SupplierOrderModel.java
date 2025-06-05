package org.example.ggg.model;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.SupplierOrderDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderModel {

    public static String saveSupplierOrder(SupplierOrderDto supplierOrderDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Supplier_Order (Date, SupplierID) VALUES (?, ?)";
        boolean isAdded = CrudUtil.execute(sql, supplierOrderDto.getDate(), supplierOrderDto.getSupplierId());
        return isAdded ? "Successfully added supplier order!" : "Failed to add supplier order!";
    }

    public static String updateSupplierOrder(SupplierOrderDto supplierOrderDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Supplier_Order SET Date = ?, SupplierID = ? WHERE Supplier_order_id = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                supplierOrderDto.getDate(),
                supplierOrderDto.getSupplierId(),
                supplierOrderDto.getSupplierOrderId());
        return isUpdated ? "Successfully updated supplier order!" : "Failed to update supplier order!";
    }

    public static String deleteSupplierOrder(String supplierOrderId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Supplier_Order WHERE Supplier_order_id = ?";
        boolean isDeleted = CrudUtil.execute(sql, supplierOrderId);
        return isDeleted ? "Successfully deleted supplier order!" : "Failed to delete supplier order!";
    }

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
