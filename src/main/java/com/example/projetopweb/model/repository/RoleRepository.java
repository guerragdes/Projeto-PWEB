package com.example.projetopweb.model.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.projetopweb.model.entity.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
@Transactional
public class RoleRepository {
    
    @PersistenceContext
    private EntityManager em;

    // Método para buscar uma role pelo nome
    public Role buscarPorNome(String nome) {
        try {
            Query query = em.createQuery("SELECT r FROM Role r WHERE r.nome = :nome");
            query.setParameter("nome", nome);
            return (Role) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
