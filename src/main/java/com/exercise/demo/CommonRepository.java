package com.exercise.demo;

import java.util.Collection;

public interface CommonRepository<T>{
    public  T save(T domain);
    public Iterable<T> save(Collection<T> domain);
    public void delete(T domain);
    public  T findById(String id);
    public Iterable<T> findAll();
}
