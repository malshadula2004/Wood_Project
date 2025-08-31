package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.SuppliersDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SuppliersModel {

    /**
     * Save a new supplier in the database.
     *
     * @param suppliersDto The SuppliersDto containing supplier details.
     * @return Success or failure message.
     * @throws SQLException           If a database error occurs.
     * @throws ClassNotFoundException If the database driver is not found.
     */
    public static String saveSupplier(SuppliersDto suppliersDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Suppliers (Name, ContactInfo, Address, SuppliedMaterials) VALUES (?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,
                suppliersDto.getName(),
                suppliersDto.getContactInfo(),
                suppliersDto.getAddress(),
                suppliersDto.getSuppliedMaterials()
        );
        return isAdded ? "Successfully added supplier!" : "Failed to add supplier!";
    }

    /**
     * Update an existing supplier in the database.
     *
     * @param suppliersDto The SuppliersDto containing updated supplier details.
     * @return Success or failure message.
     * @throws SQLException           If a database error occurs.
     * @throws ClassNotFoundException If the database driver is not found.
     */
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

    /**
     * Delete a supplier from the database.
     *
     * @param supplierId The ID of the supplier to delete.
     * @return Success or failure message.
     * @throws SQLException           If a database error occurs.
     * @throws ClassNotFoundException If the database driver is not found.
     */
    public static String deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Suppliers WHERE SupplierID = ?";
        boolean isDeleted = CrudUtil.execute(sql, supplierId);
        return isDeleted ? "Successfully deleted supplier!" : "Failed to delete supplier!";
    }

    /**
     * Retrieve all suppliers from the database.
     *
     * @return A list of SuppliersDto objects.
     * @throws SQLException           If a database error occurs.
     * @throws ClassNotFoundException If the database driver is not found.
     */
    public static ArrayList<SuppliersDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Suppliers";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<SuppliersDto> suppliersList = new ArrayList<>();

        while (resultSet.next()) {
            suppliersList.add(new SuppliersDto(
                    resultSet.getString("SupplierID"),
                    resultSet.getString("Name"),
                    resultSet.getString("ContactInfo"),
                    resultSet.getString("Address"),
                    resultSet.getString("SuppliedMaterials")
            ));
        }
        return suppliersList;
    }

    /**
     * Search suppliers by a keyword.
     *
     * @param keyword The search keyword.
     * @return A list of SuppliersDto objects matching the search criteria.
     * @throws SQLException           If a database error occurs.
     * @throws ClassNotFoundException If the database driver is not found.
     */
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

    /**
     * Retrieve all supplier IDs from the database.
     *
     * @return A list of supplier IDs as strings.
     * @throws SQLException           If a database error occurs.
     * @throws ClassNotFoundException If the database driver is not found.
     */
    public static ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SupplierID FROM Suppliers";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<String> supplierIds = new ArrayList<>();

        while (resultSet.next()) {
            supplierIds.add(resultSet.getString("SupplierID"));
        }
        return supplierIds;
    }
}
