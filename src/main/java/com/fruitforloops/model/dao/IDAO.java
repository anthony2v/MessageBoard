package com.fruitforloops.model.dao;

import java.util.List;

public interface IDAO<T> 
{
    public T get(Long id);
     
    public List<T> getAll();
     
    public boolean save(T object);
     
    public boolean update(T object);
     
    public boolean delete(Long id);
}
