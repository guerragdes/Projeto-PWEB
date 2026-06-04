package com.example.projetopweb.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.projetopweb.model.entity.PessoaJuridica;
import com.example.projetopweb.model.entity.Role;
import com.example.projetopweb.model.repository.EmpresaRepository;
import com.example.projetopweb.model.repository.RoleRepository;

import java.util.List;

@Controller
@RequestMapping("empresa")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String razaoSocial,
            Model model) {

        List<PessoaJuridica> listaEmpresas;

        if (razaoSocial != null && !razaoSocial.isEmpty()) {
            listaEmpresas = empresaRepository.buscarPorRazaoSocial(razaoSocial);
            model.addAttribute("razaoSocial", razaoSocial);
        } else {
            listaEmpresas = empresaRepository.listar();
        }

        model.addAttribute("listaEmpresas", listaEmpresas);
        return "empresa/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("empresa", new PessoaJuridica());
        return "empresa/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute("empresa") @Valid PessoaJuridica empresa, BindingResult result) {
        if (result.hasErrors()) {
            return "empresa/form";
        }

        empresaRepository.salvar(empresa);
        return "redirect:/empresa";
    }
}
