package org.example.ggg.dao;

import org.example.ggg.dao.impl.CustomersDAOImpl;

public class DAOFactory {

    public enum DAOTypes {
        CUSTOMER
    }

 public static superDAO getDAO(DAOTypes type) {
        switch (type) {
            case CUSTOMER:
                return new CustomersDAOImpl() {
                };
            default:
                return null;
        }
    }
}