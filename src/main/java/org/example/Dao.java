package org.example;

import java.util.List;
    public interface Dao <T> {
        T save (T entity);
        T update (T entity);
        void delete (long id);
        T get (long id);
        List<T> getAll();
    }
