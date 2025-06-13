package com.example.pizzariacdr.model.dao;

import com.example.pizzariacdr.db.DB;
import com.example.pizzariacdr.model.dao.impl.*;
import java.sql.Connection;
import com.example.pizzariacdr.db.DB;

public class DaoFactory {
    public static ClienteDAO createClienteDAO() {
        return new ClienteDAOJDBC(DB.getConnection());
    }

    public static PizzaDAO createPizzaDAO() {
        return new PizzaDAOJDBC(DB.getConnection());
    }

    public static FuncionarioDAO createFuncionarioDAO() {
        return new FuncionarioDAOJDBC(DB.getConnection());
    }

    public static PedidoDAO createPedidoDAO() {
        return new PedidoDAOJDBC(DB.getConnection());
    }

    public static ItemPedidoDAO createItemPedidoDAO() {
        return new ItemPedidoDAOJDBC(DB.getConnection());
    }
}