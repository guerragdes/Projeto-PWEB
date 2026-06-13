package com.example.projetopweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projetopweb.model.entity.Role;
import com.example.projetopweb.model.entity.Usuario;
import com.example.projetopweb.model.repository.RoleRepository;
import com.example.projetopweb.model.repository.UsuarioRepository;

@Controller
@RequestMapping("/admin/cadastrar")
public class AdminCadastroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String exibirFormulario() {
        return "admin/form";
    }

    @PostMapping
    public String cadastrar(
            @RequestParam String login,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String confirmaSenha,
            Model model) {

        if (!senha.equals(confirmaSenha)) {
            model.addAttribute("erro", "As senhas não coincidem.");
            return "admin/form";
        }

        if (usuarioRepository.usuario(login) != null) {
            model.addAttribute("erro", "Este login já está em uso.");
            return "admin/form";
        }

        if (usuarioRepository.usuarioPorEmail(email) != null) {
            model.addAttribute("erro", "Este email já está cadastrado.");
            return "admin/form";
        }

        Usuario novoAdmin = new Usuario();
        novoAdmin.setLogin(login);
        novoAdmin.setNome(nome);
        novoAdmin.setEmail(email);
        novoAdmin.setSenha(passwordEncoder.encode(senha));
        
        Role roleAdmin = roleRepository.buscarPorNome("ROLE_ADMIN");
        if (roleAdmin != null) {
            novoAdmin.getRoles().add(roleAdmin);
        }

        usuarioRepository.salvar(novoAdmin);

        return "redirect:/clientes?adminCadastrado=true";
    }
}
