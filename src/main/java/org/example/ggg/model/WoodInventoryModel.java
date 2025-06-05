package org.example.ggg.model;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.WoodInventoryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WoodInventoryModel {

    public static boolean addInventory(WoodInventoryDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO wood_inventory (Supplier_order_id, wood_id, wood_length) VALUES (?, ?, ?)";
        return CrudUtil.execute(sql, dto.getSupplierOrderId(), dto.getWoodId(), dto.getWoodLength());
    }

    public static boolean updateInventory(WoodInventoryDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE wood_inventory SET Supplier_order_id = ?, wood_length = ? WHERE wood_id = ?";
        return CrudUtil.execute(sql, dto.getSupplierOrderId(), dto.getWoodLength(), dto.getWoodId());
    }

    public static String deleteInventory(String woodId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM wood_inventory WHERE wood_id = ?";
        boolean isDeleted = CrudUtil.execute(sql, woodId);
        return isDeleted ? "Wood deletion fail." : "Wood deleted successfully.";
    }

    public static ArrayList<WoodInventoryDto> getAllInventory() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM wood_inventory";
        try (ResultSet resultSet = CrudUtil.executeQuery(sql)) {
            ArrayList<WoodInventoryDto> inventoryList = new ArrayList<>();
            while (resultSet.next()) {
                inventoryList.add(new WoodInventoryDto(
                        resultSet.getString("Supplier_order_id"),
                        resultSet.getString("wood_id"),
                        resultSet.getDouble("wood_length")
                ));
            }
            return inventoryList;
        }
    }

    public static WoodInventoryDto getInventoryByID(String woodId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM wood_inventory WHERE wood_id = ?";
        try (ResultSet resultSet = CrudUtil.executeQuery(sql, woodId)) {
            if (resultSet.next()) {
                return new WoodInventoryDto(
                        resultSet.getString("Supplier_order_id"),
                        resultSet.getString("wood_id"),
                        resultSet.getDouble("wood_length")
                );
            }
            return null;
        }
    }
}
