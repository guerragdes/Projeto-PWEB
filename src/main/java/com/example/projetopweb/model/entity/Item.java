package com.example.projetopweb.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Item implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Double quantidade;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Venda venda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public BigDecimal total() {
        if (produto == null || quantidade == null) {
            return BigDecimal.ZERO;
        }

        return produto.getValor().multiply(BigDecimal.valueOf(quantidade));
    }
}
