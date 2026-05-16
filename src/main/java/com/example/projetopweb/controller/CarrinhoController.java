package com.example.projetopweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.projetopweb.model.entity.Pessoa;
import com.example.projetopweb.model.entity.Produto;
import com.example.projetopweb.model.entity.Venda;
import com.example.projetopweb.model.repository.ClienteRepository;
import com.example.projetopweb.model.repository.PessoaRepository;
import com.example.projetopweb.model.repository.ProdutoRepository;
import com.example.projetopweb.model.repository.VendaRepository;
import com.example.projetopweb.service.CarrinhoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("carrinho")
public class CarrinhoController {

    @Autowired
    CarrinhoService carrinhoService;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @GetMapping
    public String visualizar(Model model) {
        model.addAttribute("itens", carrinhoService.obterItens());
        model.addAttribute("total", carrinhoService.calcularTotal());
        model.addAttribute("clientes", pessoaRepository.buscarTodas());
        return "carrinho/view";
    }

    @PostMapping("/adicionar/{produtoId}")
    public String adicionarProduto(@PathVariable Long produtoId) {
        Optional<Produto> produtoOpt = produtoRepository.buscarPorId(produtoId);
        
        if (produtoOpt.isPresent()) {
            carrinhoService.adicionarProduto(produtoOpt.get()); // Só adiciona se o produto existir
        }

        return "redirect:/carrinho";
    }

    @PostMapping("/remover/{produtoId}")
    public String removerProduto(@PathVariable Long produtoId) {
        carrinhoService.removerProduto(produtoId);
        return "redirect:/carrinho";
    }

    @PostMapping("/atualizar-quantidade")
    public String atualizarQuantidade(
            @RequestParam Long produtoId,
            @RequestParam Double quantidade) {
        carrinhoService.atualizarQuantidade(produtoId, quantidade);
        return "redirect:/carrinho";
    }

    @PostMapping("/finalizar")
    public String finalizarCompra(
            @RequestParam Long clienteId) {
        
        // Verifica se o carrinho está vazio antes de finalizar a compra
        if (carrinhoService.estaVazio()) {
            return "redirect:/carrinho";
        }

        // Verifica se o cliente existe antes de finalizar a compra
        Optional<Pessoa> clienteOpt = pessoaRepository.buscarPorId(clienteId);
        if (clienteOpt.isEmpty()) {
            return "redirect:/carrinho";
        }

        // Cria a venda e associa os itens do carrinho
        Venda venda = new Venda();
        venda.setData(LocalDateTime.now());
        venda.setCliente(clienteOpt.get());
        venda.setItens(carrinhoService.obterItens());

        // Garante que cada item da venda tenha a referência correta para a venda
        for (var item : venda.getItens()) {
            item.setVenda(venda);
        }

        // Salva a venda no banco de dados
        vendaRepository.salvar(venda);

        // Limpa o carrinho após finalizar a compra
        carrinhoService.limparCarrinho();

        return "redirect:/vendas/" + venda.getId();
    }
    
    @PostMapping("/limpar")
    public String limparCarrinho() {
        carrinhoService.limparCarrinho();
        return "redirect:/produtos/loja";
    }
}
