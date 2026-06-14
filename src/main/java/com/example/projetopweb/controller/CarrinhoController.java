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
        
        Optional<Usuario> clienteOpt = usuarioRepository.buscarPorId(usuario.getId());
        if (clienteOpt.isPresent()) {
            model.addAttribute("nomeUsuario", clienteOpt.get().getDisplayName());
            model.addAttribute("enderecos", clienteOpt.get().getEnderecos());
        }
        
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
            @RequestParam(required = false) String formaPagamento,
            @RequestParam(required = false) Long enderecoId,
            @AuthenticationPrincipal Usuario usuario,
            RedirectAttributes redirectAttributes) {
        
        // Verifica se o carrinho está vazio antes de finalizar a compra
        if (carrinhoService.estaVazio()) {
            return "redirect:/carrinho";
        }

        // Verifica se a forma de pagamento foi selecionada
        if (formaPagamento == null || formaPagamento.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("erroKey", "Selecione uma forma de pagamento.");
            return "redirect:/carrinho";
        }

        // Verifica se o endereço foi selecionado
        if (enderecoId == null) {
            redirectAttributes.addFlashAttribute("erroKey", "Selecione um endereço de entrega.");
            return "redirect:/carrinho";
        }

        // Busca a entidade Usuario gerenciada pelo JPA a partir do ID do usuário logado.
        Optional<Usuario> clienteOpt = usuarioRepository.buscarPorId(usuario.getId());

        if (clienteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("erroKey", "NotFound.venda.cliente");
            return "redirect:/carrinho";
        }

        Usuario cliente = clienteOpt.get();
        
        // Encontra o endereço na lista do cliente
        com.example.projetopweb.model.entity.Endereco enderecoSelecionado = cliente.getEnderecos().stream()
                .filter(e -> e.getId().equals(enderecoId))
                .findFirst()
                .orElse(null);

        if (enderecoSelecionado == null) {
            redirectAttributes.addFlashAttribute("erroKey", "Endereço inválido.");
            return "redirect:/carrinho";
        }

        // Cria a venda e associa os itens do carrinho
        Venda venda = new Venda();
        venda.setData(LocalDateTime.now());
        venda.setCliente(cliente);
        venda.setFormaPagamento(formaPagamento);
        venda.setEndereco(enderecoSelecionado);
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
