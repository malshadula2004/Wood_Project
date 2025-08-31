package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.WoodInventoryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WoodInventoryModel {

    // Retrieve Supplier Order IDs for ComboBox
    public static ArrayList<String> getSupplierOrderIDs() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SupplierID FROM suppliers";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<String> supplierOrderIDs = new ArrayList<>();

        while (resultSet.next()) {
            supplierOrderIDs.add(resultSet.getString("SupplierID"));
        }
        return supplierOrderIDs;
    }

    // Retrieve Wood IDs for ComboBox
    public static ArrayList<String> getWoodIDs() throws SQLException, ClassNotFoundException {
        String sql = "SELECT WoodID FROM wood";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<String> woodIDs = new ArrayList<>();

        while (resultSet.next()) {
            woodIDs.add(resultSet.getString("WoodID"));
        }
        return woodIDs;
    }

    // Retrieve all inventory data for TableView
    public static ArrayList<WoodInventoryDto> getAllInventory() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM wood_inventory";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<WoodInventoryDto> inventoryList = new ArrayList<>();

        while (resultSet.next()) {
            inventoryList.add(new WoodInventoryDto(
                    resultSet.getString("id"),
                    resultSet.getString("Supplier_order_id"),
                    resultSet.getString("wood_id"),
                    resultSet.getString("wood_name"),
                    resultSet.getString("wood_length"),
                    resultSet.getString("price")
            ));
        }
        return inventoryList;
    }

    // Add new inventory record
    public static boolean addInventory(WoodInventoryDto inventory) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO wood_inventory (Supplier_order_id, wood_id, wood_name, wood_length, price) VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                inventory.getSupplierOrderId(),
                inventory.getWoodId(),
                inventory.getWoodName(),
                inventory.getWoodLength(),
                inventory.getPrice()
        );
    }

    // Update existing inventory record
    public static boolean updateInventory(WoodInventoryDto inventory) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE wood_inventory SET Supplier_order_id = ?, wood_id = ?, wood_name = ?, wood_length = ?, price = ? WHERE id = ?";
        return CrudUtil.execute(
                sql,
                inventory.getSupplierOrderId(),
                inventory.getWoodId(),
                inventory.getWoodName(),
                inventory.getWoodLength(),
                inventory.getPrice(),
                inventory.getId()
        );
    }

    // Delete inventory record by ID
    public static boolean deleteInventory(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM wood_inventory WHERE id = ?";
        return CrudUtil.execute(sql, id);
    }

    // Retrieve wood name by WoodID
    public static String getWoodNameById(String woodId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT Type FROM Wood WHERE WoodID = ?";
        ResultSet resultSet = CrudUtil.executeQuery(sql, woodId);

        if (resultSet.next()) {
            return resultSet.getString("Type");
        }
        return null;
    }

    // Retrieve wood unit price by WoodID
    public static Double getWoodUnitPriceById(String woodId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT UnitPrice FROM Wood WHERE WoodID = ?";
        ResultSet resultSet = CrudUtil.executeQuery(sql, woodId);

        if (resultSet.next()) {
            return resultSet.getDouble("UnitPrice");
        }
        return null;
    }

    // Search inventory by ID or Name
    public static ArrayList<WoodInventoryDto> searchInventoryByIdOrName(String searchText) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM wood_inventory WHERE wood_id LIKE ? OR wood_name LIKE ?";
        ResultSet resultSet = CrudUtil.executeQuery(sql, "%" + searchText + "%", "%" + searchText + "%");

        ArrayList<WoodInventoryDto> searchResults = new ArrayList<>();
        while (resultSet.next()) {
            searchResults.add(new WoodInventoryDto(
                    resultSet.getString("id"),
                    resultSet.getString("Supplier_order_id"),
                    resultSet.getString("wood_id"),
                    resultSet.getString("wood_name"),
                    resultSet.getString("wood_length"),
                    resultSet.getString("price")
            ));
        }
        return searchResults;
    }
}
