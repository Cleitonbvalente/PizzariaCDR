package com.example.pizzariacdr.model.dao.impl;

import com.example.pizzariacdr.db.DB;
import com.example.pizzariacdr.model.dao.PizzaDAO;
import com.example.pizzariacdr.model.entities.Pizza;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaDAOJDBC implements PizzaDAO {
    private Connection conn;

    public PizzaDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Pizza pizza) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO pizza (tamanho, sabor, preco_base, descricao_pizza, ingredientes, nome_pizza) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, pizza.getTamanho());
            st.setString(2, pizza.getSabor());
            st.setDouble(3, pizza.getPrecoBase());
            st.setString(4, pizza.getDescricao());
            st.setString(5, pizza.getIngredientes());
            st.setString(6, pizza.getNome());

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    pizza.setIdPizza(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pizza: " + e.getMessage(), e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void atualizar(Pizza pizza) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE pizza SET tamanho = ?, sabor = ?, preco_base = ?, " +
                            "descricao_pizza = ?, ingredientes = ?, nome_pizza = ? " +
                            "WHERE id_pizza = ?"
            );
            st.setString(1, pizza.getTamanho());
            st.setString(2, pizza.getSabor());
            st.setDouble(3, pizza.getPrecoBase());
            st.setString(4, pizza.getDescricao());
            st.setString(5, pizza.getIngredientes());
            st.setString(6, pizza.getNome());
            st.setInt(7, pizza.getIdPizza());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pizza: " + e.getMessage(), e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletar(int idPizza) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM pizza WHERE id_pizza = ?");
            st.setInt(1, idPizza);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pizza: " + e.getMessage(), e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Pizza buscarPorId(int idPizza) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM pizza WHERE id_pizza = ?");
            st.setInt(1, idPizza);
            rs = st.executeQuery();

            if (rs.next()) {
                Pizza pizza = new Pizza();
                pizza.setIdPizza(rs.getInt("id_pizza"));
                pizza.setTamanho(rs.getString("tamanho"));
                pizza.setSabor(rs.getString("sabor"));
                pizza.setPrecoBase(rs.getDouble("preco_base"));
                pizza.setDescricao(rs.getString("descricao_pizza"));
                pizza.setIngredientes(rs.getString("ingredientes"));
                pizza.setNome(rs.getString("nome_pizza"));
                return pizza;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pizza por ID: " + e.getMessage(), e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Pizza> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM pizza ORDER BY nome_pizza");
            rs = st.executeQuery();

            List<Pizza> pizzas = new ArrayList<>();
            while (rs.next()) {
                Pizza pizza = new Pizza();
                pizza.setIdPizza(rs.getInt("id_pizza"));
                pizza.setTamanho(rs.getString("tamanho"));
                pizza.setSabor(rs.getString("sabor"));
                pizza.setPrecoBase(rs.getDouble("preco_base"));
                pizza.setDescricao(rs.getString("descricao_pizza"));
                pizza.setIngredientes(rs.getString("ingredientes"));
                pizza.setNome(rs.getString("nome_pizza"));
                pizzas.add(pizza);
            }
            return pizzas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as pizzas: " + e.getMessage(), e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
