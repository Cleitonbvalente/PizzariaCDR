package com.example.pizzariacdr.model.entities;

public class Outros {
    private int idOutros;
    private String nome;
    private String descricao;
    private double precoBase;

    // Getters e Setters
    public int getIdOutros() {
        return idOutros;
    }

    public void setIdOutros(int idOutros) {
        this.idOutros = idOutros;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }
}
