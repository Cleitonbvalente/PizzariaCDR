package com.example.pizzariacdr.model.dao.impl;

import com.example.pizzariacdr.db.DB;
import com.example.pizzariacdr.model.dao.ClienteDAO;
import com.example.pizzariacdr.model.entities.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public class ClienteDAOJDBC implements ClienteDAO {
    private Connection conn;

    public ClienteDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Cliente cliente) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO cliente (id_cliente, nome, telefone, email, endereco) " +
                            "VALUES (?, ?, ?, ?, ?)"
            );
            st.setInt(1, cliente.getIdCliente());
            st.setString(2, cliente.getNome());
            st.setString(3, cliente.getTelefone());
            st.setString(4, cliente.getEmail());
            st.setString(5, cliente.getEndereco());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE cliente SET nome=?, telefone=?, email=?, endereco=? " +
                            "WHERE id_cliente=?"
            );
            st.setString(1, cliente.getNome());
            st.setString(2, cliente.getTelefone());
            st.setString(3, cliente.getEmail());
            st.setString(4, cliente.getEndereco());
            st.setInt(5, cliente.getIdCliente());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletar(int idCliente) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM cliente WHERE id_cliente=?");
            st.setInt(1, idCliente);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Cliente buscarPorId(int idCliente) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM cliente WHERE id_cliente=?");
            st.setInt(1, idCliente);
            rs = st.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEndereco(rs.getString("endereco"));
                return cliente;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Cliente> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM cliente");
            rs = st.executeQuery();
            List<Cliente> clientes = new ArrayList<>();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEndereco(rs.getString("endereco"));
                clientes.add(cliente);
            }
            return clientes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}