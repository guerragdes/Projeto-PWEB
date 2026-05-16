package com.example.projetopweb.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.projetopweb.model.entity.Pessoa;
import com.example.projetopweb.model.entity.PessoaFisica;
import com.example.projetopweb.model.entity.PessoaJuridica;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PessoaRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Pessoa> buscarTodas() {
        Query query = em.createQuery("SELECT p FROM Pessoa p ORDER BY p.id");
        return query.getResultList();
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Pessoa.class, id));
    }

    public List<PessoaFisica> buscarPFporNome(String nome) {
        Query query = em.createQuery("SELECT p FROM PessoaFisica p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY p.nome");
        query.setParameter("nome", nome);
        return query.getResultList();
    }

    public List<PessoaJuridica> buscarPJporNome(String razaoSocial) {
        Query query = em.createQuery("SELECT p FROM PessoaJuridica p WHERE LOWER(p.razaoSocial) LIKE LOWER(CONCAT('%', :razaoSocial, '%')) ORDER BY p.razaoSocial");
        query.setParameter("razaoSocial", razaoSocial);
        return query.getResultList();
    }

    public List<PessoaFisica> buscarTodasPF() {
        Query query = em.createQuery("SELECT p FROM PessoaFisica p ORDER BY p.nome");
        return query.getResultList();
    }

    public List<PessoaJuridica> buscarTodasPJ() {
        Query query = em.createQuery("SELECT p FROM PessoaJuridica p ORDER BY p.razaoSocial");
        return query.getResultList();
    }
}
