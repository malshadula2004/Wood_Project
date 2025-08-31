package org.example.ggg.dao.impl;

import org.example.ggg.TM.CartTM;
import org.example.ggg.dbconnection.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class PurchaseOrdersModel {


    public static boolean saveOrder(String customerId, String customerName, ArrayList<CartTM> cartItems) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        boolean isSaved = false;

        try {
            // Start transaction
            connection.setAutoCommit(false);

            // Insert each item into the Purchase_Orders table
            String insertQuery = "INSERT INTO Purchase_Orders (CustomerID, CustomerName, ItemID, ItemName, Quantity, UnitPrice, Total) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            for (CartTM cartItem : cartItems) {
                preparedStatement.setString(1, customerId);
                preparedStatement.setString(2, customerName);
                preparedStatement.setString(3, cartItem.getItemId());
                preparedStatement.setString(4, cartItem.getItemName());
                preparedStatement.setInt(5, Integer.parseInt(cartItem.getQty()));
                preparedStatement.setBigDecimal(6, new java.math.BigDecimal(cartItem.getUnitPrice()));
                preparedStatement.setBigDecimal(7, new java.math.BigDecimal(cartItem.getTotal()));

                preparedStatement.addBatch();
            }

            int[] results = preparedStatement.executeBatch();

            // Check if all rows were inserted successfully
            if (results.length == cartItems.size()) {
                // Update inventory quantities
                String updateInventoryQuery = "UPDATE Inventory SET QuantityAvailable = QuantityAvailable - ? WHERE ItemID = ?";
                PreparedStatement inventoryStmt = connection.prepareStatement(updateInventoryQuery);

                for (CartTM cartItem : cartItems) {
                    inventoryStmt.setInt(1, Integer.parseInt(cartItem.getQty()));
                    inventoryStmt.setString(2, cartItem.getItemId());
                    inventoryStmt.addBatch();
                }

                int[] inventoryResults = inventoryStmt.executeBatch();

                // Check if inventory updates were successful
                isSaved = inventoryResults.length == cartItems.size();
            }

            if (isSaved) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }

        return isSaved;
    }
}