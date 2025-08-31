package org.example.ggg.bo.impl;

import org.example.ggg.bo.CustomerBO;
import org.example.ggg.dao.CustomerDAO;
import org.example.ggg.dao.DAOFactory;
import org.example.ggg.model.CustomersDto;
import java.sql.SQLException;
import java.util.ArrayList;

public class customerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.addCustomer(dto);
    }

    @Override
    public boolean updateCustomer(CustomersDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.updateCustomer(dto);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.deleteCustomer(id);
    }

    @Override
    public ArrayList<CustomersDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllCustomers();
    }

    @Override
    public ArrayList<CustomersDto> searchCustomers(String keyword) throws SQLException, ClassNotFoundException {
        return customerDAO.searchCustomers(keyword);
    }
}