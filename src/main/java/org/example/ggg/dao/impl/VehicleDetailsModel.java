package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.VehicleDetailsDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDetailsModel {

    public static String saveVehicle(VehicleDetailsDto vehicleDetailsDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO VehicleDetails (VehicleNumber, Type, Capacity, Status, UserID) VALUES (?, ?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,
                vehicleDetailsDto.getVehicleNumber(),
                vehicleDetailsDto.getType(),
                vehicleDetailsDto.getCapacity(),
                vehicleDetailsDto.getStatus(),
                vehicleDetailsDto.getUserId()
        );
        return isAdded ? "Successfully added vehicle!" : "Failed to add vehicle!";
    }

    public static String updateVehicle(VehicleDetailsDto vehicleDetailsDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE VehicleDetails SET VehicleNumber = ?, Type = ?, Capacity = ?, Status = ?, UserID = ? WHERE VehicleID = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                vehicleDetailsDto.getVehicleNumber(),
                vehicleDetailsDto.getType(),
                vehicleDetailsDto.getCapacity(),
                vehicleDetailsDto.getStatus(),
                vehicleDetailsDto.getUserId(),
                vehicleDetailsDto.getVehicleId()
        );
        return isUpdated ? "Successfully updated vehicle!" : "Failed to update vehicle!";
    }

    public static String deleteVehicle(String vehicleId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM VehicleDetails WHERE VehicleID = ?";
        boolean isDeleted = CrudUtil.execute(sql, vehicleId);
        return isDeleted ? "Successfully deleted vehicle!" : "Failed to delete vehicle!";
    }

    public static VehicleDetailsDto getVehicleByNumber(String vehicleNumber) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM VehicleDetails WHERE VehicleNumber = ?";
        ResultSet resultSet = CrudUtil.executeQuery(sql, vehicleNumber);

        if (resultSet.next()) {
            return new VehicleDetailsDto(
                    resultSet.getString("VehicleID"),
                    resultSet.getString("VehicleNumber"),
                    resultSet.getString("Type"),
                    resultSet.getString("Capacity"),
                    resultSet.getString("Status"),
                    resultSet.getString("UserID")
            );
        }
        return null; // Return null if no match is found
    }

    public static ArrayList<VehicleDetailsDto> getAllVehicles() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM VehicleDetails";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<VehicleDetailsDto> vehicleList = new ArrayList<>();

        while (resultSet.next()) {
            vehicleList.add(new VehicleDetailsDto(
                    resultSet.getString("VehicleID"),
                    resultSet.getString("VehicleNumber"),
                    resultSet.getString("Type"),
                    resultSet.getString("Capacity"),
                    resultSet.getString("Status"),
                    resultSet.getString("UserID")
            ));
        }
        return vehicleList;
    }
}
