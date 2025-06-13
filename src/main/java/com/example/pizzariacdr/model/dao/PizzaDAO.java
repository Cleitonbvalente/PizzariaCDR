package com.example.pizzariacdr.model.dao;

import com.example.pizzariacdr.model.entities.Pizza;
import java.util.List;

public interface PizzaDAO {
    void inserir(Pizza pizza);
    void atualizar(Pizza pizza);
    void deletar(int idPizza);
    Pizza buscarPorId(int idPizza);
    List<Pizza> buscarTodos();
}
