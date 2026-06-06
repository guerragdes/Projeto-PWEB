package com.example.projetopweb.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@DiscriminatorValue("F")
public class PessoaFisica extends Pessoa {

    @NotBlank
    private String nome;

    @NotBlank(message = "{NotBlank.cliente.cpf}")
    @CPF(message = "{CPF.cliente.cpf}")
    private String cpf;

    @NotBlank
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$")
    private String telefone;

    public PessoaFisica() {
    }

    public PessoaFisica(String email, String nome, String cpf, String telefone) {
        super(email);
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String getDisplayName() {
        return this.nome != null ? this.nome : "";
    }
}
