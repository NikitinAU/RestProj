package DAO;

import entity.Entity;

import java.util.List;

public interface DAO<E extends Entity> {
    public int create(E entity);
    public E readById(int id);
    public int update(E entity);
    public int delete(int id);
    public List<E> readAll();
}
