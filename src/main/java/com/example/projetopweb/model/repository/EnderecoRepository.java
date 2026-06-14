package com.example.projetopweb.model.repository;

import com.example.projetopweb.model.entity.Endereco;
import com.example.projetopweb.model.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class EnderecoRepository {

    @PersistenceContext
    private EntityManager em;

    public void salvar(Endereco endereco) {
        if (endereco.getId() == null) {
            em.persist(endereco);
        } else {
            em.merge(endereco);
        }
    }

    public Optional<Endereco> buscarPorId(Long id) {
        Endereco endereco = em.find(Endereco.class, id);
        return Optional.ofNullable(endereco);
    }

    public List<Endereco> buscarPorUsuario(Usuario usuario) {
        String jpql = "SELECT e FROM Endereco e WHERE e.usuario = :usuario";
        TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }

    public void remover(Long id) {
        Endereco endereco = em.find(Endereco.class, id);
        if (endereco != null) {
            em.remove(endereco);
        }
    }
}
