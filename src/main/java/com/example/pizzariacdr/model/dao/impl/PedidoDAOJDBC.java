package com.example.pizzariacdr.model.dao.impl;

import com.example.pizzariacdr.db.DB;
import com.example.pizzariacdr.model.dao.PedidoDAO;
import com.example.pizzariacdr.model.entities.Cliente;
import com.example.pizzariacdr.model.entities.Funcionario;
import com.example.pizzariacdr.model.entities.Pedido;
import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class PedidoDAOJDBC implements PedidoDAO {
    private Connection conn;

    public PedidoDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Pedido pedido) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(
                    "INSERT INTO pedido (data_pedido, status_pedido, valor_total, id_cliente, id_funcionario) " +
                            "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            st.setDate(1, Date.valueOf(pedido.getDataPedido().toLocalDate()));
            st.setString(2, pedido.getStatus());
            st.setDouble(3, pedido.getValorTotal());
            st.setInt(4, pedido.getCliente().getIdCliente());
            st.setInt(5, pedido.getFuncionario().getIdFuncionario());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    pedido.setIdPedido(rs.getInt(1));
                }
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fazer rollback: " + ex.getMessage());
            }
            throw new RuntimeException("Erro ao inserir pedido: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void atualizar(Pedido pedido) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE pedido SET data_pedido = ?, status_pedido = ?, valor_total = ?, " +
                            "id_cliente = ?, id_funcionario = ? WHERE id_pedido = ?"
            );
            st.setDate(1, Date.valueOf(pedido.getDataPedido().toLocalDate()));
            st.setString(2, pedido.getStatus());
            st.setDouble(3, pedido.getValorTotal());
            st.setInt(4, pedido.getCliente().getIdCliente());
            st.setInt(5, pedido.getFuncionario().getIdFuncionario());
            st.setInt(6, pedido.getIdPedido());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pedido: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletar(int idPedido) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM pedido WHERE id_pedido = ?");
            st.setInt(1, idPedido);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pedido: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Pedido buscarPorId(int idPedido) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT p.*, c.nome as nome_cliente, f.nome_func as nome_funcionario " +
                            "FROM pedido p " +
                            "JOIN cliente c ON p.id_cliente = c.id_cliente " +
                            "JOIN funcionario f ON p.id_funcionario = f.id_funcionario " +
                            "WHERE p.id_pedido = ?"
            );
            st.setInt(1, idPedido);
            rs = st.executeQuery();

            if (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("id_pedido"));
                pedido.setDataPedido(rs.getDate("data_pedido"));
                pedido.setStatus(rs.getString("status_pedido"));
                pedido.setValorTotal(rs.getDouble("valor_total"));

                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome_cliente"));
                pedido.setCliente(cliente);

                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                funcionario.setNome(rs.getString("nome_funcionario"));
                pedido.setFuncionario(funcionario);

                return pedido;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Pedido> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT p.*, c.nome as nome_cliente, f.nome_func as nome_funcionario " +
                            "FROM pedido p " +
                            "JOIN cliente c ON p.id_cliente = c.id_cliente " +
                            "JOIN funcionario f ON p.id_funcionario = f.id_funcionario " +
                            "ORDER BY p.data_pedido DESC"
            );
            rs = st.executeQuery();

            List<Pedido> pedidos = new ArrayList<>();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("id_pedido"));
                pedido.setDataPedido(rs.getDate("data_pedido"));
                pedido.setStatus(rs.getString("status_pedido"));
                pedido.setValorTotal(rs.getDouble("valor_total"));

                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome_cliente"));
                pedido.setCliente(cliente);

                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                funcionario.setNome(rs.getString("nome_funcionario"));
                pedido.setFuncionario(funcionario);

                pedidos.add(pedido);
            }
            return pedidos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedidos: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
