package com.example.pizzariacdr.model.dao;

import com.example.pizzariacdr.model.entities.ItemPedido;
import java.util.List;

public interface ItemPedidoDAO {
    void inserir(ItemPedido item);
    void atualizar(ItemPedido item);
    void deletar(int idItem);
    List<ItemPedido> buscarPorPedido(int idPedido);
}
