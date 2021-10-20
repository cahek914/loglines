package org.kao.loglines.service;

import java.util.List;

public interface GenericService<T> {

    T get(Long id);

    List<T> getList();

    T create(T entity);

    T update(Long id, T entity);

    void deleteById(Long id);

}
