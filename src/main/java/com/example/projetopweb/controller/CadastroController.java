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
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Exibe o formulário de cadastro
    @GetMapping
    public String exibirFormulario() {
        return "login/cadastro";
    }

    // Processa o cadastro de novo usuário
    @PostMapping
    public String cadastrar(
            @RequestParam String login,
            @RequestParam String senha,
            @RequestParam String confirmaSenha,
            Model model) {

        // Verifica se as senhas coincidem
        if (!senha.equals(confirmaSenha)) {
            model.addAttribute("erro", "As senhas não coincidem.");
            return "login/cadastro";
        }

        // Verifica se o login já existe
        if (usuarioRepository.usuario(login) != null) {
            model.addAttribute("erro", "Este login já está em uso. Escolha outro.");
            return "login/cadastro";
        }

        // Cria o novo usuário
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(login);
        novoUsuario.setSenha(passwordEncoder.encode(senha));

        // Atribui o papel ROLE_USER (apenas usuários comuns podem se cadastrar)
        Role roleUser = roleRepository.buscarPorNome("ROLE_USER");
        if (roleUser != null) {
            novoUsuario.getRoles().add(roleUser);
        }

        usuarioRepository.salvar(novoUsuario);

        // Redireciona para o login com mensagem de sucesso
        return "redirect:/login?cadastrado";
    }
}
