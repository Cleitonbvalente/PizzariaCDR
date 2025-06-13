package com.example.pizzariacdr.model.dao.impl;

import com.example.pizzariacdr.db.DB;
import com.example.pizzariacdr.model.dao.FuncionarioDAO;
import com.example.pizzariacdr.model.entities.Funcionario;
import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOJDBC implements FuncionarioDAO {
    private Connection conn;

    public FuncionarioDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Funcionario funcionario) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO funcionario (nome_func, email, telefone, cargo) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, funcionario.getNome());
            st.setString(2, funcionario.getEmail());
            st.setString(3, funcionario.getTelefone());
            st.setString(4, funcionario.getCargo());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    funcionario.setIdFuncionario(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir funcionário: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void atualizar(Funcionario funcionario) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE funcionario SET nome_func = ?, email = ?, telefone = ?, cargo = ? WHERE id_funcionario = ?"
            );
            st.setString(1, funcionario.getNome());
            st.setString(2, funcionario.getEmail());
            st.setString(3, funcionario.getTelefone());
            st.setString(4, funcionario.getCargo());
            st.setInt(5, funcionario.getIdFuncionario());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletar(int idFuncionario) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM funcionario WHERE id_funcionario = ?");
            st.setInt(1, idFuncionario);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar funcionário: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Funcionario buscarPorId(int idFuncionario) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM funcionario WHERE id_funcionario = ?");
            st.setInt(1, idFuncionario);
            rs = st.executeQuery();

            if (rs.next()) {
                Funcionario func = new Funcionario();
                func.setIdFuncionario(rs.getInt("id_funcionario"));
                func.setNome(rs.getString("nome_func"));
                func.setEmail(rs.getString("email"));
                func.setTelefone(rs.getString("telefone"));
                func.setCargo(rs.getString("cargo"));
                return func;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Funcionario> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM funcionario ORDER BY nome_func");
            rs = st.executeQuery();

            List<Funcionario> funcionarios = new ArrayList<>();
            while (rs.next()) {
                Funcionario func = new Funcionario();
                func.setIdFuncionario(rs.getInt("id_funcionario"));
                func.setNome(rs.getString("nome_func"));
                func.setEmail(rs.getString("email"));
                func.setTelefone(rs.getString("telefone"));
                func.setCargo(rs.getString("cargo"));
                funcionarios.add(func);
            }
            return funcionarios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionários: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
