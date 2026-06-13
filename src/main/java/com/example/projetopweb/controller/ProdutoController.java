package com.example.projetopweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.projetopweb.model.entity.Produto;
import com.example.projetopweb.model.repository.ProdutoRepository;
import com.example.projetopweb.service.CarrinhoService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CarrinhoService carrinhoService;

    private static final String UPLOAD_DIR = "uploads/produtos/";

    @GetMapping
    public String listar(
            @RequestParam(required = false) String descricao,
            Model model) {

        List<Produto> listaProdutos;

        if (descricao != null && !descricao.isEmpty()) {
            listaProdutos = produtoRepository.buscarPorDescricao(descricao);
            model.addAttribute("descricao", descricao);
        } else {
            listaProdutos = produtoRepository.listar();
        }

        model.addAttribute("listaProdutos", listaProdutos);
        return "produto/list"; // renderiza view produto/list.html
    }

    @GetMapping("/loja")
    public String loja(
        @RequestParam(required = false) String descricao,
        Model model) {

        List<Produto> listaProdutos;
        
        if (descricao != null && !descricao.isEmpty()) {
            listaProdutos = produtoRepository.buscarPorDescricao(descricao);
            model.addAttribute("descricao", descricao);
        } else {
            listaProdutos = produtoRepository.listar();
        }

        model.addAttribute("listaProdutos", listaProdutos);
        model.addAttribute("quantidadeCarrinho", carrinhoService.obterQuantidadeTotalItens());
        return "produto/loja";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/form";
    }

    @PostMapping
    public String salvar(
            @ModelAttribute("produto") @Valid Produto produto,
            BindingResult result,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem) {

        if (result.hasErrors()) {
            return "produto/form";
        }

        // Processa upload de imagem, se fornecida
        if (imagem != null && !imagem.isEmpty()) {
            try {
                // Garante que o diretório de upload existe
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Gera nome único: timestamp + nome original
                String nomeOriginal = imagem.getOriginalFilename();
                String nomeArquivo = System.currentTimeMillis() + "_" + nomeOriginal;

                // Salva o arquivo
                Path destino = uploadPath.resolve(nomeArquivo);
                Files.copy(imagem.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

                // Grava o nome do arquivo na entidade
                produto.setImagemUrl(nomeArquivo);
            } catch (IOException e) {
                e.printStackTrace();
                // Em caso de erro no upload, salva o produto sem imagem
            }
        }

        produtoRepository.salvar(produto);
        return "redirect:/produtos"; // faz uma nova requisição http para /produtos, evitando reenvio de formulário
    }
}