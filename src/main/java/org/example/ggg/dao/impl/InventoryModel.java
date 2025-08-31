package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.InventoryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryModel {

    public static boolean addInventory(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Inventory (ItemID, Supplier_order_id, QuantityAvailable) VALUES (?, ?, ?)";
        return CrudUtil.execute(sql,
                inventoryDto.getItemId(),
                inventoryDto.getSupplierOrderId(),
                inventoryDto.getQuantityAvailable());
    }

    public static boolean updateInventory(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Inventory SET Supplier_order_id = ?, QuantityAvailable = ? WHERE ItemID = ?";
        return CrudUtil.execute(sql,
                inventoryDto.getSupplierOrderId(),
                inventoryDto.getQuantityAvailable(),
                inventoryDto.getItemId());
    }

    public static boolean deleteInventory(String itemId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Inventory WHERE ItemID = ?";
        return CrudUtil.execute(sql, itemId);
    }

    public static ArrayList<InventoryDto> getAllInventory() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryDto> list = new ArrayList<>();
        ResultSet rs = CrudUtil.executeQuery("SELECT ItemID, Supplier_order_id, QuantityAvailable FROM Inventory");
        while (rs.next()) {
            list.add(new InventoryDto(
                    rs.getString("ItemID"),
                    rs.getString("Supplier_order_id"),
                    rs.getString("QuantityAvailable")));
        }
        return list;
    }

    public static ArrayList<InventoryDto> searchInventory(String itemId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ItemID, Supplier_order_id, QuantityAvailable FROM Inventory WHERE ItemID LIKE ?";
        ResultSet rs = CrudUtil.executeQuery(sql, "%" + itemId + "%");

        ArrayList<InventoryDto> inventoryList = new ArrayList<>();
        while (rs.next()) {
            inventoryList.add(new InventoryDto(
                    rs.getString("ItemID"),
                    rs.getString("Supplier_order_id"),
                    rs.getString("QuantityAvailable")
            ));
        }
        return inventoryList;
    }

    public static ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT ItemID FROM Inventory";
        ResultSet rs = CrudUtil.executeQuery(sql);
        ArrayList<String> itemIds = new ArrayList<>();

        while (rs.next()) {
            itemIds.add(rs.getString("ItemID"));
        }

        return itemIds;
    }

    public static ArrayList<String> getAllSupplierOrderIDs() throws SQLException, ClassNotFoundException {
        String sql = "SELECT DISTINCT Supplier_order_id FROM Inventory";
        ResultSet rs = CrudUtil.executeQuery(sql);
        ArrayList<String> supplierOrderIds = new ArrayList<>();

        while (rs.next()) {
            supplierOrderIds.add(rs.getString("Supplier_order_id"));
        }

        return supplierOrderIds;
    }

    public static String[] getItemDetailsById(String itemId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT i.Name, inv.QuantityAvailable, i.UnitPrice FROM Inventory inv " +
                "JOIN Items i ON inv.ItemID = i.ItemID WHERE inv.ItemID = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, itemId);

        if (rs.next()) {
            return new String[]{
                    rs.getString("Name"),              // Item Name
                    rs.getString("QuantityAvailable"), // Quantity Available
                    rs.getString("UnitPrice")          // Unit Price
            };
        } else {
            return null;
        }
    }
}
