package com.example.projetopweb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.example.projetopweb.model.entity.Item;
import com.example.projetopweb.model.entity.Produto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CarrinhoService implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Item> itens = new ArrayList<>();

    public void adicionarProduto(Produto produto) {
        for (Item item : itens) {
            if (item.getProduto().getId().equals(produto.getId())) {
                item.setQuantidade(item.getQuantidade() + 1);
                return;
            }
        }

        // Se o produto não estiver no carrinho, adiciona um novo item
        Item novoItem = new Item();
        novoItem.setProduto(produto);
        novoItem.setQuantidade(1.0);
        itens.add(novoItem);
    }

    public void removerProduto(Long produtoId) {
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getProduto().getId().equals(produtoId)) {
                itens.remove(i);
                i--; // Ajusta o índice após a remoção
                // sai do laço sozinho?
            }
        }
    }

    public void atualizarQuantidade(Long produtoId, Double quantidade) {
        // Se a quantidade for zero ou negativa, remove o produto do carrinho
        if (quantidade <= 0) {
            removerProduto(produtoId);
            return;
        }

        for (Item item : itens) {
            if (item.getProduto().getId().equals(produtoId)) {
                item.setQuantidade(quantidade);
                return;
            }
        }
    }

    public List<Item> obterItens() {
        return itens;
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (Item item : itens) {
            BigDecimal valorItem = item.getProduto().getValor().multiply(BigDecimal.valueOf(item.getQuantidade()));
            total = total.add(valorItem);
        }

        return total;
    }

    public void limparCarrinho() {
        itens.clear();
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }
}