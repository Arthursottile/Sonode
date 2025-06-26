package br.edu.utfpr.sonode.dao;

import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProjetoDAO extends GenericDAO<Projeto> {

    public ProjetoDAO() {
        super(Projeto.class);
    }

    @Override
    public List<Projeto> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT p FROM Projeto p JOIN FETCH p.dono", Projeto.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Projeto> findByNomeOrUsuario(String searchTerm) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Projeto> query = em.createQuery(
                "SELECT DISTINCT p FROM Projeto p JOIN FETCH p.dono u WHERE p.nome LIKE :term OR u.nome LIKE :term", Projeto.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Projeto findCompletoById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Projeto> query = em.createQuery(
                "SELECT DISTINCT p FROM Projeto p " +
                "LEFT JOIN FETCH p.dono " +
                "LEFT JOIN FETCH p.versoes " +
                "LEFT JOIN FETCH p.comentarios c " +
                "LEFT JOIN FETCH c.autor " + 
                "LEFT JOIN FETCH p.colaboradores " +
                "WHERE p.id = :id", Projeto.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; 
        } finally {
            em.close();
        }
    }
}