package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Petr", "Yan", (byte) 15);
        userService.saveUser("Magnus", "Karlsen", (byte) 26);
        userService.saveUser("Alexandr", "Pushkin", (byte) 35);
        userService.saveUser("Michail", "Lermontov", (byte) 42);
        List<User> userList = userService.getAllUsers();
        userList.forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }


}
