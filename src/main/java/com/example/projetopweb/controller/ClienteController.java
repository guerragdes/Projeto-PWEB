package com.example.projetopweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projetopweb.model.entity.Usuario;
import com.example.projetopweb.model.repository.UsuarioRepository;

import java.util.List;

@Controller
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String nome,
            Model model) {

        List<Usuario> listaClientes;

        if (nome != null && !nome.isEmpty()) {
            listaClientes = usuarioRepository.buscarClientesPorNome(nome);
            model.addAttribute("nome", nome);
        } else {
            listaClientes = usuarioRepository.buscarTodosClientes();
        }

        model.addAttribute("listaClientes", listaClientes);
        return "clientes/list";
    }
}
