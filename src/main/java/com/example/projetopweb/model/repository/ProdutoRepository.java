package com.example.projetopweb.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.projetopweb.model.entity.Produto;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager em;

    public void salvar(Produto produto) {
        em.persist(produto);
    }

    public List<Produto> listar() {
        Query query = em.createQuery("SELECT p FROM Produto p");
        return query.getResultList();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Produto.class, id));
    }

    public List<Produto> buscarPorDescricao(String descricao) {
        Query query = em.createQuery("SELECT p FROM Produto p WHERE LOWER(p.descricao) LIKE LOWER(CONCAT('%', :descricao, '%')) ORDER BY p.descricao");
        query.setParameter("descricao", descricao);
        return query.getResultList();
    }
}