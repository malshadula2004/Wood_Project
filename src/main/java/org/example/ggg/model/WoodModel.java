package org.example.ggg.model;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.WoodDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WoodModel {

    public static String saveWood(WoodDto woodDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Wood (Type, Grade, Dimensions, UnitPrice) VALUES (?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,
                woodDto.getType(),
                woodDto.getGrade(),
                woodDto.getDimensions(),
                woodDto.getUnitPrice()
        );
        return isAdded ? "Wood successfully added!" : "Failed to add wood!";
    }

    public static String updateWood(WoodDto woodDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Wood SET Type = ?, Grade = ?, Dimensions = ?, UnitPrice = ? WHERE WoodID = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                woodDto.getType(),
                woodDto.getGrade(),
                woodDto.getDimensions(),
                woodDto.getUnitPrice(),
                woodDto.getWoodId()
        );
        return isUpdated ? "Successfully updated wood!" : "Failed to update wood!";
    }

    public static String deleteWood(String woodId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Wood WHERE WoodID = ?";
        boolean isDeleted = CrudUtil.execute(sql, woodId);
        return isDeleted ? "Successfully deleted wood!" : "Failed to delete wood!";
    }

    public static ArrayList<WoodDto> getAllWood() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Wood";
        ResultSet resultSet = CrudUtil.executeQuery(sql);

        ArrayList<WoodDto> woodList = new ArrayList<>();
        while (resultSet.next()) {
            woodList.add(new WoodDto(
                    resultSet.getString("WoodID"),
                    resultSet.getString("Type"),
                    resultSet.getString("Grade"),
                    resultSet.getString("Dimensions"),
                    resultSet.getString("UnitPrice")
            ));
        }
        return woodList;
    }

    public static ArrayList<WoodDto> searchWood(String searchKey) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Wood WHERE WoodID LIKE ? OR Type LIKE ?";
        ResultSet resultSet = CrudUtil.executeQuery(sql, "%" + searchKey + "%", "%" + searchKey + "%");

        ArrayList<WoodDto> woodList = new ArrayList<>();
        while (resultSet.next()) {
            woodList.add(new WoodDto(
                    resultSet.getString("WoodID"),
                    resultSet.getString("Type"),
                    resultSet.getString("Grade"),
                    resultSet.getString("Dimensions"),
                    resultSet.getString("UnitPrice")
            ));
        }
        return woodList;
    }
}
