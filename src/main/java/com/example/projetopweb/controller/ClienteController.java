package com.example.projetopweb.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projetopweb.model.entity.PessoaFisica;
import com.example.projetopweb.model.repository.ClienteRepository;

import java.util.List;

@Controller
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String nome,
            Model model) {

        List<PessoaFisica> listaClientes;

        if (nome != null && !nome.isEmpty()) {
            listaClientes = clienteRepository.buscarPorNome(nome);
            model.addAttribute("nome", nome);
        } else {
            listaClientes = clienteRepository.buscar();
        }

        model.addAttribute("listaClientes", listaClientes);
        return "clientes/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new PessoaFisica());
        return "clientes/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute("cliente") @Valid PessoaFisica cliente, BindingResult result) {
        if (result.hasErrors()) {
            return "clientes/form";
        }

        clienteRepository.salvar(cliente);
        return "redirect:/clientes";
    }
}
