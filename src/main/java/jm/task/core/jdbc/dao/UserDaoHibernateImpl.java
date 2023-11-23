package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {

	private final SessionFactory sessionFactory;

	public UserDaoHibernateImpl() {

		try {
			this.sessionFactory = Util.createSessionFactory();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public void createUsersTable() {
		String qrySql = "CREATE TABLE IF NOT EXISTS users (" +
				"id BIGINT PRIMARY KEY AUTO_INCREMENT," +
				"name VARCHAR(256) NOT NULL," +
				"lastName VARCHAR(256) NOT NULL," +
				"age TINYINT NOT NULL" +
				")";

		Transaction tx = null;

		try ( Session session = sessionFactory.openSession() ) {
			tx = session.beginTransaction();
			session.createSQLQuery( qrySql ).executeUpdate();
			tx.commit();

		} catch ( Exception e ) {
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	@Override
	public void dropUsersTable() {
		String qrySql = "DROP TABLE IF EXISTS users;";

		Transaction tx = null;

		try ( Session session = sessionFactory.openSession() ) {
			tx = session.beginTransaction();
			session.createSQLQuery( qrySql ).executeUpdate();
			tx.commit();

		} catch ( Exception e ) {
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	@Override
	public void saveUser(String name, String lastName, byte age) {
		Transaction tx = null;

		try ( Session session = sessionFactory.openSession() ) {
			tx = session.beginTransaction();
			session.save( new User( name, lastName, age) );
			tx.commit();

		} catch ( Exception e ) {
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	@Override
	public void removeUserById(long id) {
		Transaction tx = null;

		try ( Session session = sessionFactory.openSession() ) {
			tx = session.beginTransaction();
			User user = session.get(User.class, id);
			session.delete(user);
			tx.commit();

		} catch ( Exception e ) {
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	@Override
	public List<User> getAllUsers() {
		String hql = "FROM User";

		try (Session session = sessionFactory.openSession()) {
			return  session.createQuery(hql, User.class).getResultList();
		}
	}

	@Override
	public void cleanUsersTable() {
		String qrySql = "TRUNCATE TABLE users;";

		Transaction tx = null;

		try ( Session session = sessionFactory.openSession() ) {
			tx = session.beginTransaction();
			session.createSQLQuery( qrySql ).executeUpdate();
			tx.commit();

		} catch ( Exception e ) {
			if (tx != null) {
				tx.rollback();
			}
		}
	}
}
