package br.edu.utfpr.sonode.dao;

import br.edu.utfpr.sonode.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public abstract class GenericDAO<T> {

    protected EntityManager em;
    private Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public T update(T entity) {
        em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        T updatedEntity = null;
        try {
            transaction.begin();
            updatedEntity = em.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return updatedEntity;
    }

    public void delete(T entity) {
        em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public T findById(Object id) {
        em = JPAUtil.getEntityManager();
        T entity = em.find(entityClass, id);
        em.close();
        return entity;
    }

    public List<T> findAll() {
        em = JPAUtil.getEntityManager();
        List<T> entities = em.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
        em.close();
        return entities;
    }
}