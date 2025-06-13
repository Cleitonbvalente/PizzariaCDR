package com.example.pizzariacdr.model.entities;

public class Pizza {
    private int idPizza;
    private String tamanho;
    private String sabor;
    private double precoBase;
    private String descricao;
    private String ingredientes;
    private String nome;

    // Getters e Setters
    public int getIdPizza() {
        return idPizza;
    }
    public void setIdPizza(int idPizza) {
        this.idPizza = idPizza;
    }
    public String getTamanho() {
        return tamanho;
    }
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
    public String getSabor() {
        return sabor;
    }
    public void setSabor(String sabor) {
        this.sabor = sabor;
    }
    public double getPrecoBase() {
        return precoBase;
    }
    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getIngredientes() {
        return ingredientes;
    }
    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}