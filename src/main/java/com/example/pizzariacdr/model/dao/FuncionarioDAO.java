package com.example.pizzariacdr.model.dao;

import com.example.pizzariacdr.model.entities.Funcionario;
import java.util.List;

public interface FuncionarioDAO {
    void inserir(Funcionario funcionario);
    void atualizar(Funcionario funcionario);
    void deletar(int idFuncionario);
    Funcionario buscarPorId(int idFuncionario);
    List<Funcionario> buscarTodos();
}