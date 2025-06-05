package org.example.ggg.model;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.MonthlyProfitOrLossDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MonthlyProfitOrLossModel {

    // Save Record
    public static String saveRecord(MonthlyProfitOrLossDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Mounthly_ProfitOrLoss (ID, OrderTotal, PaymentTotal, LossOrProfitAmount, final) VALUES (?, ?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql, dto.getId(), dto.getOrderTotal(), dto.getPaymentTotal(), dto.getLossOrProfitAmount(), dto.getFinalStatus());
        return isAdded ? "Record successfully added!" : "Failed to add record!";
    }

    // Update Record
    public static String updateRecord(MonthlyProfitOrLossDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Mounthly_ProfitOrLoss SET OrderTotal = ?, PaymentTotal = ?, LossOrProfitAmount = ?, final = ? WHERE ID = ?";
        boolean isUpdated = CrudUtil.execute(sql, dto.getOrderTotal(), dto.getPaymentTotal(), dto.getLossOrProfitAmount(), dto.getFinalStatus(), dto.getId());
        return isUpdated ? "Record successfully updated!" : "Failed to update record!";
    }

    // Delete Record
    public static String deleteRecord(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Mounthly_ProfitOrLoss WHERE ID = ?";
        boolean isDeleted = CrudUtil.execute(sql, id);
        return isDeleted ? "Record successfully deleted!" : "Failed to delete record!";
    }

    // Get All Records
    public static ArrayList<MonthlyProfitOrLossDto> getAllRecords() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Mounthly_ProfitOrLoss";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<MonthlyProfitOrLossDto> recordList = new ArrayList<>();

        while (resultSet.next()) {
            recordList.add(new MonthlyProfitOrLossDto(
                    resultSet.getString("ID"),
                    resultSet.getString("OrderTotal"),
                    resultSet.getString("PaymentTotal"),
                    resultSet.getString("LossOrProfitAmount"),
                    resultSet.getString("final")
            ));
        }
        return recordList;
    }
}
