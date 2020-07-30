package dao;

import java.util.List;

import model.Model;

public interface DAO<T extends Model> {

	public boolean persist(T object);

	public boolean merge(T object, Class<T> classe, long id);

	public boolean remove(Class<T> classe, long id);

	public List<T> select(String hql, Class<T> classe);

	public T get(long id, Class<T> classe, boolean keepSessionOpened);

	public void start();

}
