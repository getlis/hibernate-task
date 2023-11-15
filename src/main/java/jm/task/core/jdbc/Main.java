package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser(  "Thomas", "Anderson", (byte) 25);
        userDaoJDBC.saveUser(  "Truman", "Burbank", (byte) 25);
        userDaoJDBC.saveUser(  "Hermione", "Granger", (byte) 25);
        userDaoJDBC.saveUser(  "Natasha", "Romanoff", (byte) 25);

        userDaoJDBC.getAllUsers().forEach( System.out::println );

        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();

    }
}
