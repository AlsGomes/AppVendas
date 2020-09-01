package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Model;

public class DAOImpl<T extends Model> implements DAO<T> {

	private static EntityManagerFactory emf;
	private static EntityManager entityManager;

	private EntityManagerFactory getEMF() {
		if (emf == null) {
			System.out.println("Criando nova fábrica de entidades");
			emf = Persistence.createEntityManagerFactory("vendas");
		}

		return emf;
	}

	private EntityManager getEM() {
		if (entityManager == null) {
			return getEMF().createEntityManager();
		}

		if (!entityManager.isOpen()) {
			return getEMF().createEntityManager();
		} else {
			entityManager.close();
			return getEMF().createEntityManager();
		}

	}

	@Override
	public boolean persist(T object) {

		EntityManager em = getEM();

		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println("PERSIST: " + e.getMessage());
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean persistAll(List<T> objects) {
		EntityManager em = getEM();

		try {
			em.getTransaction().begin();
			for (T t : objects) {
				em.persist(t);
			}
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println("PERSIST: " + e.getMessage());
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean merge(T object, Class<T> classe, long id) {
		EntityManager em = getEM();

		T t = null;

		try {
			t = em.find(classe, id);

			t = object;

			em.getTransaction().begin();
			em.merge(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println("MERGE: " + e.getMessage());
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean remove(Class<T> classe, long id) {
		EntityManager em = getEM();

		T t = null;

		try {
			t = em.find(classe, id);
			if (t != null) {
				em.getTransaction().begin();
				em.remove(t);
				em.getTransaction().commit();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("REMOVE: " + e.getMessage());
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public List<T> select(String hql, Class<T> classe) {

		EntityManager em = getEM();
		List<T> list = null;

		try {
			list = em.createQuery(hql).getResultList();
			return list;
		} catch (Exception e) {
			System.out.println("SELECT: " + e.getMessage());
			e.printStackTrace();
			return list;
		} finally {
			em.close();
		}
	}

	@Override
	public T get(long id, Class<T> classe, boolean keepSessionOpened) {
		EntityManager em = getEM();

		T t = null;

		try {
			t = em.find(classe, id);
			return t;
		} catch (Exception e) {
			System.out.println("GET: " + e.getMessage());
			e.printStackTrace();
			return t;
		} finally {
			if (!keepSessionOpened) {
				em.close();
			}
		}

	}

	@Override
	public void start() {
		Thread th = new Thread(() -> {
			getEMF();
		});
		th.start();
	}

}
