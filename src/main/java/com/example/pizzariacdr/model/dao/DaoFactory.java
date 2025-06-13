package com.example.pizzariacdr.model.dao;

import com.example.pizzariacdr.db.DB;
import com.example.pizzariacdr.model.dao.impl.ClienteDAOJDBC;
import com.example.pizzariacdr.model.dao.impl.PizzaDAOJDBC;

import java.sql.Connection;

public class DaoFactory {
    public static ClienteDAO createClienteDAO() {
        return new ClienteDAOJDBC(DB.getConnection());
    }

    public static PizzaDAO createPizzaDAO() {
        return new PizzaDAOJDBC(DB.getConnection());
    }
}