package com.fruitforloops.model.dao;

import java.util.List;

public interface IDAO<T> 
{
    public T get(long id);
     
    public List<T> getAll();
     
    public boolean save(T object);
     
    public void update(T object);
     
    public void delete(T object);
}
