package org.example.ggg.model;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.WoodOrderDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WoodOrderModel {

    public static String addWoodOrder(WoodOrderDTO woodOrder) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO WoodOrders (WoodID, Length, Total, OrderDate) VALUES (?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,
                woodOrder.getWoodId(),
                woodOrder.getWoodLength(),
                woodOrder.getTotal(),
                woodOrder.getOrderDate()
        );
        return isAdded ? "Wood order added successfully." : "Failed to add wood order.";
    }

    public static String updateWoodOrder(WoodOrderDTO woodOrder) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE WoodOrders SET WoodID = ?, Length = ?, Total = ?, OrderDate = ? WHERE OrderID = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                woodOrder.getWoodId(),
                woodOrder.getWoodLength(),
                woodOrder.getTotal(),
                woodOrder.getOrderDate(),
                woodOrder.getId()
        );
        return isUpdated ? "Wood order updated successfully." : "Failed to update wood order.";
    }

    public static ArrayList<WoodOrderDTO> loadWoodOrders() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM WoodOrders";
        ResultSet rs = CrudUtil.executeQuery(sql);
        ArrayList<WoodOrderDTO> woodOrderList = new ArrayList<>();

        while (rs.next()) {
            woodOrderList.add(new WoodOrderDTO(
                    rs.getString("OrderID"),
                    rs.getString("WoodID"),
                    rs.getString("Length"),
                    rs.getString("Total"),
                    rs.getString("OrderDate")
            ));
        }
        return woodOrderList;
    }

    public static ArrayList<WoodOrderDTO> searchWoodOrder(String keyword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM WoodOrders WHERE OrderID LIKE ? OR WoodID LIKE ?";
        ResultSet rs = CrudUtil.executeQuery(sql, "%" + keyword + "%", "%" + keyword + "%");
        ArrayList<WoodOrderDTO> woodOrderList = new ArrayList<>();

        while (rs.next()) {
            woodOrderList.add(new WoodOrderDTO(
                    rs.getString("OrderID"),
                    rs.getString("WoodID"),
                    rs.getString("Length"),
                    rs.getString("Total"),
                    rs.getString("OrderDate")
            ));
        }
        return woodOrderList;
    }
}
