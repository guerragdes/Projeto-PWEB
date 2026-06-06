package com.example.projetopweb.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@DiscriminatorValue("J")
public class PessoaJuridica extends Pessoa {

    @NotBlank
    private String razaoSocial;

    @NotBlank(message = "{NotBlank.empresa.cnpj}")
    @CNPJ(message = "{CNPJ.empresa.cnpj}")
    private String cnpj;

    @NotBlank
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$")
    private String telefone;

    public PessoaJuridica() {
    }

    public PessoaJuridica(String email, String razaoSocial, String cnpj, String telefone) {
        super(email);
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String getDisplayName() {
        return this.razaoSocial != null ? this.razaoSocial : "";
    }
}
