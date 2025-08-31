package org.example.ggg.dao;

import org.example.ggg.model.CustomersDto;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends superDAO {
    boolean addCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    ArrayList<CustomersDto> getAllCustomers() throws SQLException, ClassNotFoundException;
    ArrayList<CustomersDto> searchCustomers(String keyword) throws SQLException, ClassNotFoundException;
}