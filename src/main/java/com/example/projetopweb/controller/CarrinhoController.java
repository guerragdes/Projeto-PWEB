package com.example.projetopweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projetopweb.model.entity.Produto;
import com.example.projetopweb.model.entity.Usuario;
import com.example.projetopweb.model.entity.Venda;
import com.example.projetopweb.model.repository.UsuarioRepository;
import com.example.projetopweb.model.repository.ProdutoRepository;
import com.example.projetopweb.model.repository.VendaRepository;
import com.example.projetopweb.service.CarrinhoService;

import java.time.LocalDateTime;
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
    UsuarioRepository usuarioRepository;

    @GetMapping
    public String visualizar(Model model, @AuthenticationPrincipal Usuario usuario) {
        model.addAttribute("itens", carrinhoService.obterItens());
        model.addAttribute("total", carrinhoService.calcularTotal());
        model.addAttribute("nomeUsuario", usuario.getDisplayName());
        return "carrinho/view";
    }

    @PostMapping("/adicionar/{produtoId}")
    public String adicionarProduto(@PathVariable Long produtoId, RedirectAttributes redirectAttributes) {
        Optional<Produto> produtoOpt = produtoRepository.buscarPorId(produtoId);
        
        if (produtoOpt.isPresent()) {
            carrinhoService.adicionarProduto(produtoOpt.get()); // Só adiciona se o produto existir
            redirectAttributes.addFlashAttribute("SucessoKey", "Produto adicionado ao carrinho com sucesso!");
        }

        return "redirect:/produtos/loja";
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
            @AuthenticationPrincipal Usuario usuario,
            RedirectAttributes redirectAttributes) {
        
        // Verifica se o carrinho está vazio antes de finalizar a compra
        if (carrinhoService.estaVazio()) {
            return "redirect:/carrinho";
        }

        // Busca a entidade Usuario gerenciada pelo JPA a partir do ID do usuário logado.
        // Isso é necessário porque o objeto "usuario" vindo do Spring Security
        // está desanexado da sessão JPA atual (foi carregado em outra transação,
        // no momento do login). Sem isso, o Hibernate lançaria um erro ao tentar
        // salvar a Venda com uma entidade não-gerenciada.
        Optional<Usuario> clienteOpt = usuarioRepository.buscarPorId(usuario.getId());

        if (clienteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("erroKey", "NotFound.venda.cliente");
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
