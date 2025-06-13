package com.example.pizzariacdr.model.dao;

import com.example.pizzariacdr.model.entities.Pedido;
import java.util.List;

public interface PedidoDAO {
    void inserir(Pedido pedido);
    void atualizar(Pedido pedido);
    void deletar(int idPedido);
    Pedido buscarPorId(int idPedido);
    List<Pedido> buscarTodos();
}
