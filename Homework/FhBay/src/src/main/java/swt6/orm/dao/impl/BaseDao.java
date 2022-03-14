package swt6.orm.dao.impl;

import swt6.util.JpaUtil;

import java.util.List;

public abstract class BaseDao<T> implements swt6.orm.dao.BaseDao<T> {
    protected abstract Class<T> getType();

    @Override
    public T getById(Long id) {
        try {
            var em = JpaUtil.getTransactedEntityManager();
            return em.find(getType(), id);
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    @Override
    public void insert(T entity) {
        try {
            var em = JpaUtil.getTransactedEntityManager();
            em.persist(entity);
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    @Override
    public T update(T entity) {
        try {
            var em = JpaUtil.getTransactedEntityManager();
            return em.merge(entity);
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    @Override
    public abstract List<T> getAll();

    @Override
    public boolean remove(T entity) {
        try {
            var em = JpaUtil.getTransactedEntityManager();
            entity = em.merge(entity);
            try {
                em.remove(entity);
            } catch (Exception ex) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }
}
