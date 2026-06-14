package com.example.projetopweb.controller;

import com.example.projetopweb.model.entity.Endereco;
import com.example.projetopweb.model.entity.Usuario;
import com.example.projetopweb.model.repository.EnderecoRepository;
import com.example.projetopweb.model.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String listar(@AuthenticationPrincipal Usuario usuario, Model model) {
        Optional<Usuario> clienteOpt = usuarioRepository.buscarPorId(usuario.getId());
        if (clienteOpt.isPresent()) {
            model.addAttribute("enderecos", clienteOpt.get().getEnderecos());
        }
        return "enderecos/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("endereco", new Endereco());
        return "enderecos/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute("endereco") @Valid Endereco endereco, BindingResult result, @AuthenticationPrincipal Usuario usuario) {
        if (result.hasErrors()) {
            return "enderecos/form";
        }
        
        Optional<Usuario> clienteOpt = usuarioRepository.buscarPorId(usuario.getId());
        if (clienteOpt.isPresent()) {
            endereco.setUsuario(clienteOpt.get());
            enderecoRepository.salvar(endereco);
        }
        
        return "redirect:/enderecos";
    }

    @PostMapping("/remover/{id}")
    public String remover(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        Optional<Endereco> enderecoOpt = enderecoRepository.buscarPorId(id);
        if (enderecoOpt.isPresent() && enderecoOpt.get().getUsuario().getId().equals(usuario.getId())) {
            enderecoRepository.remover(id);
        }
        return "redirect:/enderecos";
    }
}
