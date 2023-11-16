package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDaoJDBCImpl USER_DAO_JDBC;

    public UserServiceImpl() {
        USER_DAO_JDBC = new UserDaoJDBCImpl();
    }

    public void createUsersTable() {
        USER_DAO_JDBC.createUsersTable();
    }

    public void dropUsersTable() {
        USER_DAO_JDBC.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        USER_DAO_JDBC.saveUser( name, lastName, age );
        System.out.println( "User с именем " + name + " " + lastName + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        USER_DAO_JDBC.removeUserById( id );
    }

    public List<User> getAllUsers() {
        return USER_DAO_JDBC.getAllUsers();
    }

    public void cleanUsersTable() {
        USER_DAO_JDBC.cleanUsersTable();
    }
}
