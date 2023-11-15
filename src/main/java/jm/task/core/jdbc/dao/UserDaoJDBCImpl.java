package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.print.attribute.standard.MediaSize;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection;
    private final List<User> users;
    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.createConnection();
            this.users = new ArrayList<>();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        try {

            String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "name VARCHAR(256) NOT NULL," +
                    "lastName VARCHAR(256) NOT NULL," +
                    "age TINYINT NOT NULL" +
                    ")";

            Statement statement = connection.createStatement();
            statement.execute(createUsersTableQuery);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try( Statement statement = this.connection.createStatement() ) {
            statement.execute( "DROP TABLE IF EXISTS users;" );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {

            try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users ( name, lastName, age) VALUES (?, ?, ?)")) {
                insertStatement.setString( 1, name );
                insertStatement.setString( 2, lastName );
                insertStatement.setInt( 3, age );
                insertStatement.execute();
                System.out.println( "User с именем " + name + " " + lastName + " добавлен в базу данных");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {

        try {

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery( "SELECT * FROM users" );


                while ( resultSet.next() ) {
                    this.users.add( new User(
                            resultSet.getString( "name" ),
                            resultSet.getString( "lastName" ),
                            (byte) resultSet.getInt( "age" )
                    ) );
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.users;
    }

    public void cleanUsersTable() {
        try( Statement statement = this.connection.createStatement() ) {
            statement.execute( "TRUNCATE TABLE users;" );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
