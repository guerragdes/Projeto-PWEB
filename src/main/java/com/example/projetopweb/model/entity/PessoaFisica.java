package com.example.projetopweb.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@DiscriminatorValue("F")
public class PessoaFisica extends Pessoa {

    @NotBlank
    private String nome;

    @NotBlank(message = "{NotBlank.cliente.cpf}")
    @CPF(message = "{CPF.cliente.cpf}")
    private String cpf;

    public PessoaFisica() {
    }

    public PessoaFisica(String email, String telefone, String nome, String cpf) {
        super(email, telefone);
        this.nome = nome;
        this.cpf = cpf;
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

    @Override
    public String getDisplayName() {
        return this.nome != null ? this.nome : "";
    }
}
