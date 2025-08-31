package org.example.ggg.dao.impl;

import org.example.ggg.dao.CustomerDAO;

import org.example.ggg.dbconnection.DBConnection;
import org.example.ggg.model.CustomersDto;
import java.sql.*;
import java.util.ArrayList;

public class CustomersDAOImpl implements CustomerDAO {
    @Override
    public boolean addCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Customer VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getCustomerId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getContactInfo());
        pstm.setString(4, dto.getAddress());
        pstm.setString(5, dto.getEmail());
        pstm.setString(6, dto.getPaymentMethod());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean updateCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Customer SET name=?, contactInfo=?, address=?, email=?, paymentMethod=? WHERE customerId=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getContactInfo());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getEmail());
        pstm.setString(5, dto.getPaymentMethod());
        pstm.setString(6, dto.getCustomerId());
        pstm.executeUpdate();
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Customer WHERE customerId=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        pstm.executeUpdate();
        return false;
    }

    @Override
    public ArrayList<CustomersDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        ArrayList<CustomersDto> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new CustomersDto(
                    rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6)
            ));
        }
        return list;
    }

    @Override
    public ArrayList<CustomersDto> searchCustomers(String keyword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer WHERE name LIKE ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, "%" + keyword + "%");
        ResultSet rs = pstm.executeQuery();
        ArrayList<CustomersDto> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new CustomersDto(
                    rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6)
            ));
        }
        return list;
    }
}
