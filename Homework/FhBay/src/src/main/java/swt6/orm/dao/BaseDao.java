package swt6.orm.dao;

import java.util.List;

public interface BaseDao<T> {
    T getById(Long id);
    void insert(T entity);
    T update(T entity);
    List<T> getAll();
    boolean remove(T entity);
}
