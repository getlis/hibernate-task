package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/db_users";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = System.getenv("PS_DB_MYSQL");

	private static SessionFactory sessionFactory;

	public static Connection createConnection() throws ClassNotFoundException, SQLException {

		Class.forName( DB_DRIVER );
		return DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD );

	}

	public static SessionFactory createSessionFactory() throws ClassNotFoundException {

		if (sessionFactory == null) {

			try {
				Configuration configuration = new Configuration();

				configuration.setProperty( "hibernate.connection.driver_class", DB_DRIVER );
				configuration.setProperty("hibernate.connection.url", DB_URL );
				configuration.setProperty("hibernate.connection.username", DB_USERNAME );
				configuration.setProperty("hibernate.connection.password",  DB_PASSWORD );

				configuration.setProperty("hibernate.show_sql", "true");
				configuration.setProperty("hibernate.format_sql", "true");
				configuration.setProperty("hibernate.highlight_sql", "true");
				//	configuration.setProperty("hibernate.hbm2ddl.auto", "create");

				configuration.addAnnotatedClass( User.class );

				sessionFactory = configuration.buildSessionFactory();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sessionFactory;
	}

	public static void closeSessionFactory() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

}
