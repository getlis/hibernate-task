package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
		//
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser(  "Thomas", "Anderson", (byte) 25);
        userService.saveUser(  "Truman", "Burbank", (byte) 25);
        userService.saveUser(  "Hermione", "Granger", (byte) 25);
        userService.saveUser(  "Natasha", "Romanoff", (byte) 25);
		System.out.println(777);
        userService.removeUserById( 1 );

        userService.getAllUsers().forEach( System.out::println );

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
