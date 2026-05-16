package com.example.projetopweb.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.projetopweb.model.entity.Pessoa;
import com.example.projetopweb.model.entity.Venda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class VendaRepository {

    @PersistenceContext
    private EntityManager em;

    public void salvar(Venda venda) {
        em.persist(venda);
    }

    public List<Venda> getVendas() {
        Query query = em.createQuery("SELECT v FROM Venda v");
        return query.getResultList();
    }

    public Optional<Venda> findById(Long id) {
        return Optional.ofNullable(em.find(Venda.class, id));
    }

    public List<Venda> buscarPorData(LocalDate data) {
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.atTime(LocalTime.MAX);

        Query query = em.createQuery("SELECT v FROM Venda v WHERE v.data >= :inicio AND v.data <= :fim ORDER BY v.data DESC");
        query.setParameter("inicio", inicio);
        query.setParameter("fim", fim);
        return query.getResultList();
    }

    public List<Venda> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(LocalTime.MAX);

        Query query = em.createQuery("SELECT v FROM Venda v WHERE v.data >= :inicio AND v.data <= :fim ORDER BY v.data DESC");
        query.setParameter("inicio", inicio);
        query.setParameter("fim", fim);
        return query.getResultList();
    }

    public List<Venda> buscarPorCliente(Pessoa cliente) {
        Query query = em.createQuery("SELECT v FROM Venda v WHERE v.cliente = :cliente ORDER BY v.data DESC");
        query.setParameter("cliente", cliente);
        return query.getResultList();
    }

    public List<Venda> buscarPorClienteEPeriodo(Pessoa cliente, LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(LocalTime.MAX);

        Query query = em.createQuery("SELECT v FROM Venda v WHERE v.cliente = :cliente AND v.data >= :inicio AND v.data <= :fim ORDER BY v.data DESC");
        query.setParameter("cliente", cliente);
        query.setParameter("inicio", inicio);
        query.setParameter("fim", fim);
        return query.getResultList();
    }

}
