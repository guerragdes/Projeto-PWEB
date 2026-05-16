package com.example.projetopweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projetopweb.model.entity.Pessoa;
import com.example.projetopweb.model.entity.PessoaFisica;
import com.example.projetopweb.model.entity.Venda;
import com.example.projetopweb.model.repository.PessoaRepository;
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
    private PessoaRepository pessoaRepository;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String data,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim,
            Model model) {

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
            model.addAttribute("venda", venda.get());
            return "venda/detail";
        }

        return "redirect:/vendas";
    }

    @GetMapping("/cliente/{clienteId}")
    public String vendasPorCliente(
            @PathVariable Long clienteId,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim,
            Model model) {

        Optional<Pessoa> cliente = pessoaRepository.buscarPorId(clienteId);

        if (cliente.isEmpty()) {
            return "redirect:/vendas";
        }

        List<Venda> listaVendas;

        if (dataInicio != null && !dataInicio.isEmpty() && dataFim != null && !dataFim.isEmpty()) {
            LocalDate inicio = LocalDate.parse(dataInicio);
            LocalDate fim = LocalDate.parse(dataFim);
            listaVendas = vendaRepository.buscarPorPeriodo(inicio, fim);
            model.addAttribute("dataInicio", dataInicio);
            model.addAttribute("dataFim", dataFim);
        } else {
            listaVendas = vendaRepository.buscarPorCliente(cliente.get());
        }

        model.addAttribute("listaVendas", listaVendas);
        model.addAttribute("cliente", cliente.get());

        String nomeCliente = "";
        if (cliente.get() instanceof com.example.projetopweb.model.entity.PessoaFisica) {
            nomeCliente = ((com.example.projetopweb.model.entity.PessoaFisica) cliente.get()).getNome();
        } else if (cliente.get() instanceof com.example.projetopweb.model.entity.PessoaJuridica) {
            nomeCliente = ((com.example.projetopweb.model.entity.PessoaJuridica) cliente.get()).getRazaoSocial();
        }
        model.addAttribute("nomeCliente", nomeCliente);

        return "venda/vendas-por-cliente";
    }

    @GetMapping("/empresa/{empresaId}")
    public String vendasPorEmpresa(
            @PathVariable Long empresaId,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim,
            Model model) {

        Optional<Pessoa> empresaOpt = pessoaRepository.buscarPorId(empresaId);
        if (empresaOpt.isEmpty() || !(empresaOpt.get() instanceof com.example.projetopweb.model.entity.PessoaJuridica)) {
            return "redirect:/vendas";
        }
        com.example.projetopweb.model.entity.PessoaJuridica empresa = (com.example.projetopweb.model.entity.PessoaJuridica) empresaOpt.get();

        List<Venda> listaVendas;
        if (dataInicio != null && !dataInicio.isEmpty() && dataFim != null && !dataFim.isEmpty()) {
            LocalDate inicio = LocalDate.parse(dataInicio);
            LocalDate fim = LocalDate.parse(dataFim);
            // Ideal: buscar vendas por empresa e período
            listaVendas = vendaRepository.buscarPorClienteEPeriodo(empresa, inicio, fim);
            model.addAttribute("dataInicio", dataInicio);
            model.addAttribute("dataFim", dataFim);
        } else {
            // Ideal: buscar vendas por empresa
            listaVendas = vendaRepository.buscarPorCliente(empresa);
        }
        model.addAttribute("listaVendas", listaVendas);
        model.addAttribute("empresa", empresa);
        model.addAttribute("nomeEmpresa", empresa.getRazaoSocial());
        return "venda/vendas-por-empresa";
    }
}
