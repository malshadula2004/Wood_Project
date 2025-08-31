package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.MonthlyProfitLossDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MonthlyProfitLossModel {

    // Save Record
    public static String saveRecord(MonthlyProfitLossDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO MonthlyProfitLoss (id, month, order_amount, payment, profit_loss, final_amount) VALUES (?, ?, ?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql, dto.getId(), dto.getMonth(), dto.getOrderAmount(), dto.getPayment(), dto.getProfitLoss(), dto.getFinalAmount());
        return isAdded ? "Record successfully added!" : "Failed to add record!";
    }

    // Update Record
    public static String updateRecord(MonthlyProfitLossDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE MonthlyProfitLoss SET month = ?, order_amount = ?, payment = ?, profit_loss = ?, final_amount = ? WHERE id = ?";
        boolean isUpdated = CrudUtil.execute(sql, dto.getMonth(), dto.getOrderAmount(), dto.getPayment(), dto.getProfitLoss(), dto.getFinalAmount(), dto.getId());
        return isUpdated ? "Record successfully updated!" : "Failed to update record!";
    }

    // Delete Record
    public static String deleteRecord(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM MonthlyProfitLoss WHERE id = ?";
        boolean isDeleted = CrudUtil.execute(sql, id);
        return isDeleted ? "Record successfully deleted!" : "Failed to delete record!";
    }

    // Get All Records
    public static ArrayList<MonthlyProfitLossDto> getAllRecords() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM MonthlyProfitLoss";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<MonthlyProfitLossDto> recordList = new ArrayList<>();

        while (resultSet.next()) {
            recordList.add(new MonthlyProfitLossDto(
                    resultSet.getString("id"),
                    resultSet.getString("month"),
                    resultSet.getInt("order_amount"),
                    resultSet.getDouble("payment"),
                    resultSet.getDouble("profit_loss"),
                    resultSet.getDouble("final_amount")
            ));
        }
        return recordList;
    }
}
