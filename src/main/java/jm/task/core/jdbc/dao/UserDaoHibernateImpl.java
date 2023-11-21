package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.util.Util;

public class UserDaoHibernateImpl implements UserDao {

	private final SessionFactory sessionFactory;
	private final List<User> users;

	public UserDaoHibernateImpl() {

		try {
			this.sessionFactory = Util.createSessionFactory();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		this.users = new ArrayList<>();
	}


	@Override
	public void createUsersTable() {
		String qrySql = "CREATE TABLE IF NOT EXISTS users (" +
				"id BIGINT PRIMARY KEY AUTO_INCREMENT," +
				"name VARCHAR(256) NOT NULL," +
				"lastName VARCHAR(256) NOT NULL," +
				"age TINYINT NOT NULL" +
				")";

		try ( Session session = sessionFactory.openSession() ) {
			session.beginTransaction();

			try{
				session.createSQLQuery( qrySql ).executeUpdate();
				session.getTransaction().commit();
			} catch ( RuntimeException ex ) {
				session.getTransaction().rollback();
			}

		}
	}

	@Override
	public void dropUsersTable() {
		String qrySql = "DROP TABLE IF EXISTS users;";

		try ( Session session = sessionFactory.openSession() ) {
			session.beginTransaction();

			try{
				session.createSQLQuery( qrySql ).executeUpdate();
				session.getTransaction().commit();
			} catch ( RuntimeException ex ) {
				session.getTransaction().rollback();
			}
		}
	}

	@Override
	public void saveUser(String name, String lastName, byte age) {

		try ( Session session = sessionFactory.openSession() ) {
			session.beginTransaction();

			try{
				session.save( new User( name, lastName, age) );
				session.getTransaction().commit();
			} catch ( RuntimeException ex ) {
				session.getTransaction().rollback();
			}
		}
	}

	@Override
	public void removeUserById(long id) {

		try ( Session session = sessionFactory.openSession() ) {
			session.beginTransaction();

			try{
				User user = session.get(User.class, id);
				session.delete(user);
				session.getTransaction().commit();
			} catch ( RuntimeException ex ) {
				session.getTransaction().rollback();
			}
		}
	}

	@Override
	public List<User> getAllUsers() {

		String hql = "FROM User";

		try (Session session = sessionFactory.openSession()) {
			return session.createQuery(hql, User.class).getResultList();
		}
	}

	@Override
	public void cleanUsersTable() {
		String qrySql = "TRUNCATE TABLE users;";

		try ( Session session = sessionFactory.openSession() ) {
			session.beginTransaction();

			try{
				session.createSQLQuery( qrySql ).executeUpdate();
				session.getTransaction().commit();
			} catch ( RuntimeException ex ) {
				session.getTransaction().rollback();
			}
		}
	}
}
