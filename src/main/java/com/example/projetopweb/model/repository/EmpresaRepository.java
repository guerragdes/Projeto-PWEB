package com.example.projetopweb.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.projetopweb.model.entity.PessoaJuridica;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class EmpresaRepository {

    @PersistenceContext
    private EntityManager em;

    public void salvar(PessoaJuridica empresa) {
        em.persist(empresa);
    }

    public List<PessoaJuridica> listar() {
        Query query = em.createQuery("SELECT p FROM PessoaJuridica p");
        return query.getResultList();
    }

    public Optional<PessoaJuridica> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(PessoaJuridica.class, id));
    }

    public List<PessoaJuridica> buscarPorRazaoSocial(String razaoSocial) {
        Query query = em.createQuery("SELECT p FROM PessoaJuridica p WHERE LOWER(p.razaoSocial) LIKE LOWER(CONCAT('%', :razaoSocial, '%')) ORDER BY p.razaoSocial");
        query.setParameter("razaoSocial", razaoSocial);
        return query.getResultList();
    }
}
