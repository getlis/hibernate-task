package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection;

    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.createConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        String qrySql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(256) NOT NULL," +
                "lastName VARCHAR(256) NOT NULL," +
                "age TINYINT NOT NULL" +
                ")";

        try (PreparedStatement statement = connection.prepareStatement( qrySql ) ) {
            statement.execute( );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String qrySql = "DROP TABLE IF EXISTS users;";

        try( PreparedStatement statement = connection.prepareStatement( qrySql ) ) {
            statement.execute( );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String qrySql = "INSERT INTO users ( name, lastName, age) VALUES (?, ?, ?);";

        try( PreparedStatement statement = connection.prepareStatement( qrySql ) ) {
            statement.setString( 1, name );
            statement.setString( 2, lastName );
            statement.setInt( 3, age );
            statement.execute();
        } catch ( SQLException e ) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String qrySql = "DELETE FROM users WHERE id = ?;";

        try( PreparedStatement statement = connection.prepareStatement( qrySql ) ) {
            statement.setLong( 1, id );
            statement.execute();

        } catch ( SQLException e ) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {

        String qrySql = "SELECT * FROM users;";
        List<User> userList =  new ArrayList<>();
        try( PreparedStatement statement = connection.prepareStatement( qrySql ) ) {
            ResultSet resultSet = statement.executeQuery(  );

            while ( resultSet.next() ) {
                userList.add( new User(
                        resultSet.getString( "name" ),
                        resultSet.getString( "lastName" ),
                        (byte) resultSet.getInt( "age" )
                ) );
            }

        } catch ( SQLException e ) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public void cleanUsersTable() {
        String qrySql = "TRUNCATE TABLE users;";

        try( PreparedStatement statement = connection.prepareStatement( qrySql ) ) {
            statement.execute( );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
