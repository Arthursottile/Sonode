// GenericDAO.java
package br.edu.utfpr.sonode.dao;

import br.edu.utfpr.sonode.util.JPAUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.Serializable;
import java.util.List;

public class GenericDAO<T, ID extends Serializable> {
    private final Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
    }
    protected Session getSession() {
        return JPAUtil.getSessionFactory().openSession();
    }
    public void salvar(T entity) {
        Session s = getSession();
        Transaction tx = s.beginTransaction();
        s.persist(entity);
        tx.commit();
        s.close();
    }
    public void atualizar(T entity) {
        Session s = getSession();
        Transaction tx = s.beginTransaction();
        s.merge(entity);
        tx.commit();
        s.close();
    }
    public void remover(T entity) {
        Session s = getSession();
        Transaction tx = s.beginTransaction();
        s.remove(entity);
        tx.commit();
        s.close();
    }
    @SuppressWarnings("unchecked")
    public T buscarPorId(ID id) {
        Session s = getSession();
        T obj = s.get(clazz, id);
        s.close();
        return obj;
    }
    @SuppressWarnings("unchecked")
    public List<T> listarTodos() {
        Session s = getSession();
        List<T> list = s.createQuery("from " + clazz.getSimpleName()).list();
        s.close();
        return list;
    }
}
