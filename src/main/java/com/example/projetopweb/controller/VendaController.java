package com.example.projetopweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projetopweb.model.entity.Usuario;
import com.example.projetopweb.model.entity.Venda;
import com.example.projetopweb.model.repository.UsuarioRepository;
import com.example.projetopweb.model.repository.VendaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("vendas")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String data,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim,
            Model model) {

        Object principal = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            String username = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
            Usuario loggedUser = usuarioRepository.usuario(username);
            boolean isAdmin = loggedUser.getRoles().stream().anyMatch(role -> role.getNome().equals("ROLE_ADMIN"));
            
            if (!isAdmin) {
                return "redirect:/vendas/cliente/" + loggedUser.getId();
            }
        } else {
            return "redirect:/login";
        }

        List<Venda> listaVendas;

        if (dataInicio != null && !dataInicio.isEmpty() && dataFim != null && !dataFim.isEmpty()) {
            LocalDate inicio = LocalDate.parse(dataInicio);
            LocalDate fim = LocalDate.parse(dataFim);
            listaVendas = vendaRepository.buscarPorPeriodo(inicio, fim);
            model.addAttribute("dataInicio", dataInicio);
            model.addAttribute("dataFim", dataFim);
        } else if (data != null && !data.isEmpty()) {
            LocalDate filtro = LocalDate.parse(data);
            listaVendas = vendaRepository.buscarPorData(filtro);
            model.addAttribute("data", data);
        } else {
            listaVendas = vendaRepository.getVendas();
        }

        model.addAttribute("listaVendas", listaVendas);
        return "venda/list";
    }

    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        Optional<Venda> venda = vendaRepository.findById(id);

        if (venda.isPresent()) {
            Object principal = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
                String username = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
                Usuario loggedUser = usuarioRepository.usuario(username);
                boolean isAdmin = loggedUser.getRoles().stream().anyMatch(role -> role.getNome().equals("ROLE_ADMIN"));
                
                if (!isAdmin && !loggedUser.getId().equals(venda.get().getCliente().getId())) {
                    return "redirect:/produtos/loja";
                }
            } else {
                return "redirect:/login";
            }

            model.addAttribute("venda", venda.get());
            return "venda/detail";
        }

        return "redirect:/produtos/loja";
    }

    @GetMapping("/cliente/{clienteId}")
    public String vendasPorCliente(
            @PathVariable Long clienteId,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim,
            Model model) {

        Object principal = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            String username = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
            Usuario loggedUser = usuarioRepository.usuario(username);
            boolean isAdmin = loggedUser.getRoles().stream().anyMatch(role -> role.getNome().equals("ROLE_ADMIN"));
            
            if (!isAdmin && !loggedUser.getId().equals(clienteId)) {
                return "redirect:/produtos/loja";
            }
        } else {
            return "redirect:/login";
        }

        Optional<Usuario> cliente = usuarioRepository.buscarPorId(clienteId);

        if (cliente.isEmpty()) {
            return "redirect:/produtos/loja";
        }

        List<Venda> listaVendas;

        if (dataInicio != null && !dataInicio.isEmpty() && dataFim != null && !dataFim.isEmpty()) {
            LocalDate inicio = LocalDate.parse(dataInicio);
            LocalDate fim = LocalDate.parse(dataFim);
            listaVendas = vendaRepository.buscarPorClienteEPeriodo(cliente.get(), inicio, fim);
            model.addAttribute("dataInicio", dataInicio);
            model.addAttribute("dataFim", dataFim);
        } else {
            listaVendas = vendaRepository.buscarPorCliente(cliente.get());
        }

        model.addAttribute("listaVendas", listaVendas);
        model.addAttribute("cliente", cliente.get());
        model.addAttribute("nomeCliente", cliente.get().getNome());

        return "venda/vendas-por-cliente";
    }
}
