package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.ItemsDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemsModel {

    // Save Item (Add)
    public static String addItem(ItemsDto item) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Items (Name, Description, Category, UnitPrice) VALUES (?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,
                item.getName(),
                item.getDescription(),
                item.getCategory(),
                item.getUnitPrice()
        );
        return isAdded ? "Item added successfully." : "Failed to add item.";
    }

    // Update Item
    public static String updateItem(ItemsDto item) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Items SET Name = ?, Description = ?, Category = ?, UnitPrice = ? WHERE ItemID = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                item.getName(),
                item.getDescription(),
                item.getCategory(),
                item.getUnitPrice(),
                item.getItemId()
        );
        return isUpdated ? "Item updated successfully." : "Failed to update item.";
    }

    // Delete Item
    public static String deleteItem(String itemId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Items WHERE ItemID = ?";
        boolean isDeleted = CrudUtil.execute(sql, itemId);
        return isDeleted ? "Item deleted successfully." : "Failed to delete item.";
    }

    // Load all items
    public static ArrayList<ItemsDto> loadItems() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Items";
        ResultSet rs = CrudUtil.executeQuery(sql);
        ArrayList<ItemsDto> itemsList = new ArrayList<>();

        while (rs.next()) {
            itemsList.add(new ItemsDto(
                    rs.getString("ItemID"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("Category"),
                    rs.getString("UnitPrice")
            ));
        }
        return itemsList;
    }

    // Search items by ID or Name

}

