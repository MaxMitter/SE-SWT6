package swt6.orm.dao.impl;

import swt6.util.JpaUtil;

import java.util.List;

public abstract class BaseDao<T> implements swt6.orm.dao.BaseDao<T> {
    protected abstract Class<T> getType();

    @Override
    public T getById(Long id) {
        var em = JpaUtil.getTransactedEntityManager();
        return em.find(getType(), id);
    }

    @Override
    public void insert(T entity) {
        var em = JpaUtil.getTransactedEntityManager();
        em.persist(entity);
    }

    @Override
    public T update(T entity) {
        var em = JpaUtil.getTransactedEntityManager();
        return em.merge(entity);
    }

    @Override
    public abstract List<T> getAll();

    @Override
    public boolean remove(T entity) {
        var em = JpaUtil.getTransactedEntityManager();
        entity = em.merge(entity);
        try {
            em.remove(entity);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
