package com.example.pizzariacdr.model.dao.impl;

import com.example.pizzariacdr.db.DB;
import com.example.pizzariacdr.model.dao.ItemPedidoDAO;
import com.example.pizzariacdr.model.entities.ItemPedido;
import com.example.pizzariacdr.model.entities.Outros;
import com.example.pizzariacdr.model.entities.Pizza;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAOJDBC implements ItemPedidoDAO {
    private Connection conn;

    public ItemPedidoDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(ItemPedido item) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO item_pedido (quantidade, id_pedido, id_pizza, id_outros) " +
                            "VALUES (?, ?, ?, ?)"
            );
            st.setInt(1, item.getQuantidade());
            st.setInt(2, item.getPedido().getIdPedido());

            if (item.getPizza() != null) {
                st.setInt(3, item.getPizza().getIdPizza());
            } else {
                st.setNull(3, Types.INTEGER);
            }

            if (item.getOutros() != null) {
                st.setInt(4, item.getOutros().getIdOutros());
            } else {
                st.setNull(4, Types.INTEGER);
            }

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir item do pedido: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void atualizar(ItemPedido item) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE item_pedido SET quantidade = ?, id_pizza = ?, id_outros = ? " +
                            "WHERE id_item = ?"
            );
            st.setInt(1, item.getQuantidade());

            if (item.getPizza() != null) {
                st.setInt(2, item.getPizza().getIdPizza());
            } else {
                st.setNull(2, Types.INTEGER);
            }

            if (item.getOutros() != null) {
                st.setInt(3, item.getOutros().getIdOutros());
            } else {
                st.setNull(3, Types.INTEGER);
            }

            st.setInt(4, item.getIdItem());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item do pedido: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletar(int idItem) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM item_pedido WHERE id_item = ?");
            st.setInt(1, idItem);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar item do pedido: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public List<ItemPedido> buscarPorPedido(int idPedido) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT ip.*, p.nome_pizza, o.nome_outros " +
                            "FROM item_pedido ip " +
                            "LEFT JOIN pizza p ON ip.id_pizza = p.id_pizza " +
                            "LEFT JOIN outros o ON ip.id_outros = o.id_outros " +
                            "WHERE ip.id_pedido = ?"
            );
            st.setInt(1, idPedido);
            rs = st.executeQuery();

            List<ItemPedido> itens = new ArrayList<>();
            while (rs.next()) {
                ItemPedido item = new ItemPedido();
                item.setIdItem(rs.getInt("id_item"));
                item.setQuantidade(rs.getInt("quantidade"));

                // Se tiver pizza associada
                if (rs.getObject("id_pizza") != null) {
                    Pizza pizza = new Pizza();
                    pizza.setIdPizza(rs.getInt("id_pizza"));
                    pizza.setNome(rs.getString("nome_pizza"));
                    item.setPizza(pizza);
                }

                // Se tiver item "outros" associado
                if (rs.getObject("id_outros") != null) {
                    Outros outros = new Outros();
                    outros.setIdOutros(rs.getInt("id_outros"));
                    outros.setNome(rs.getString("nome_outros"));
                    item.setOutros(outros);
                }

                itens.add(item);
            }
            return itens;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar itens do pedido: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
