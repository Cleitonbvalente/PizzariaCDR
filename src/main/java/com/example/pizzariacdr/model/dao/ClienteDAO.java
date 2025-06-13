package com.example.pizzariacdr.model.dao;

import com.example.pizzariacdr.model.entities.Cliente;
import java.util.List;

public interface ClienteDAO {
    void inserir(Cliente cliente);
    void atualizar(Cliente cliente);
    void deletar(int idCliente);
    Cliente buscarPorId(int idCliente);
    List<Cliente> buscarTodos();
}
