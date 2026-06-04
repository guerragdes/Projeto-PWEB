package com.example.projetopweb.model.repository;

import org.springframework.stereotype.Repository;

import com.example.projetopweb.model.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager em;
    
    // Método para buscar um usuário pelo login
    public Usuario usuario(String login) {
        try {
            Query query = em.createQuery("SELECT u FROM Usuario u JOIN FETCH u.roles WHERE u.login = :login");
            query.setParameter("login", login);
            return (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // Método para salvar um novo usuário
    public void salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            em.persist(usuario);
        } else {
            em.merge(usuario);
        }
    }
}
