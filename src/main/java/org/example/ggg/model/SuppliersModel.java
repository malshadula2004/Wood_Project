package org.example.ggg.model;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.SuppliersDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SuppliersModel {
    public static String saveSupplier(SuppliersDto suppliersDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Suppliers ( Name, ContactInfo, Address, SuppliedMaterials) VALUES ( ?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,

                suppliersDto.getName(),
                suppliersDto.getContactInfo(),
                suppliersDto.getAddress(),
                suppliersDto.getSuppliedMaterials()
        );
        return isAdded ? "Successfully added supplier!" : "Failed to add supplier!";
    }

    public static String updateSupplier(SuppliersDto suppliersDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Suppliers SET Name = ?, ContactInfo = ?, Address = ?, SuppliedMaterials = ? WHERE SupplierID = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                suppliersDto.getName(),
                suppliersDto.getContactInfo(),
                suppliersDto.getAddress(),
                suppliersDto.getSuppliedMaterials(),
                suppliersDto.getSupplierId()
        );
        return isUpdated ? "Successfully updated supplier!" : "Failed to update supplier!";
    }

    public static String deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Suppliers WHERE SupplierID = ?";
        boolean isDeleted = CrudUtil.execute(sql, supplierId);
        return isDeleted ? "Successfully deleted supplier!" : "Failed to delete supplier!";
    }

    public static ArrayList<SuppliersDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Suppliers";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<SuppliersDto> suppliersDto = new ArrayList<>();

        while (resultSet.next()) {
            suppliersDto.add(new SuppliersDto(
                    resultSet.getString("SupplierID"),
                    resultSet.getString("Name"),
                    resultSet.getString("ContactInfo"),
                    resultSet.getString("Address"),
                    resultSet.getString("SuppliedMaterials") // Fixed column name
            ));
        }
        return suppliersDto;
    }

    public static ArrayList<SuppliersDto> searchSuppliers(String keyword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Suppliers WHERE SupplierID LIKE ? OR Name LIKE ?";
        ResultSet resultSet = CrudUtil.executeQuery(sql, "%" + keyword + "%", "%" + keyword + "%");
        ArrayList<SuppliersDto> suppliers = new ArrayList<>();

        while (resultSet.next()) {
            suppliers.add(new SuppliersDto(
                    resultSet.getString("SupplierID"),
                    resultSet.getString("Name"),
                    resultSet.getString("ContactInfo"),
                    resultSet.getString("Address"),
                    resultSet.getString("SuppliedMaterials")
            ));
        }
        return suppliers;
    }
}
