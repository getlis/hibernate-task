package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/db_users";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = System.getenv("PS_DB_MYSQL");

	public static Connection createConnection() throws ClassNotFoundException, SQLException {

		Class.forName( DB_DRIVER );
		return DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD );

	}

}
