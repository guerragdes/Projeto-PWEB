package com.example.projetopweb.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.projetopweb.model.entity.PessoaFisica;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ClienteRepository {

    @PersistenceContext
    private EntityManager em;

    public void salvar(PessoaFisica cliente) {
        em.persist(cliente);
    }

    public List<PessoaFisica> buscar() {
        Query q = em.createQuery("SELECT p FROM PessoaFisica p");
        return q.getResultList();
    }

    public Optional<PessoaFisica> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(PessoaFisica.class, id));
    }

    public List<PessoaFisica> buscarPorNome(String nome) {
        Query q = em.createQuery("SELECT p FROM PessoaFisica p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY p.nome");
        q.setParameter("nome", nome);
        return q.getResultList();
    }
}
