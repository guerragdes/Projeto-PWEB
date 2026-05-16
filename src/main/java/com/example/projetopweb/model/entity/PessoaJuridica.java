package com.example.projetopweb.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@DiscriminatorValue("J")
public class PessoaJuridica extends Pessoa {

    @NotBlank
    private String razaoSocial;

    @NotBlank(message = "{NotBlank.empresa.cnpj}")
    @CNPJ(message = "{CNPJ.empresa.cnpj}")
    private String cnpj;

    public PessoaJuridica() {
    }

    public PessoaJuridica(String email, String telefone, String razaoSocial, String cnpj) {
        super(email, telefone);
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
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

    @Override
    public String getDisplayName() {
        return this.razaoSocial != null ? this.razaoSocial : "";
    }
}
