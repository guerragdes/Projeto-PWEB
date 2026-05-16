package com.example.projetopweb.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

@Entity
@Table(name = "tb_pessoa")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$")
    private String telefone;

    public Pessoa() {
    }

    public Pessoa(String email, String telefone) {
        this.email = email;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Nome exibido para opções (pode ser nome ou razão social)
    public String getDisplayName() {
        return "";
    }
}
