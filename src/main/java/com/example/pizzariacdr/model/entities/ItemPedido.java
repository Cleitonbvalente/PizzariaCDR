package com.example.pizzariacdr.model.entities;

public class ItemPedido {
    private int idItem;
    private int quantidade;
    private Pedido pedido;  // Adicione esta linha
    private Pizza pizza;
    private Outros outros;

    // Getters e Setters
    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Pedido getPedido() {  // Novo getter
        return pedido;
    }

    public void setPedido(Pedido pedido) {  // Novo setter
        this.pedido = pedido;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Outros getOutros() {
        return outros;
    }

    public void setOutros(Outros outros) {
        this.outros = outros;
    }
}
