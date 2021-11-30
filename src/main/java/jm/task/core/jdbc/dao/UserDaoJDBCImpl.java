package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.util.UtilConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Util util;
    private final String createTable;
    private final String dropTable;
    private final String insertValues;
    private final String removeUser;
    private final String getAllUsers;
    private final String clearlUsers;


    public UserDaoJDBCImpl() {
        this(new UtilConfig().getUtil());
    }

    public UserDaoJDBCImpl(Util util) {
        this.util = util;
        createTable = "CREATE TABLE `" + util.getDATABASENAME() + "`.`" + util.getTABLENAME() + "` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` TINYINT NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;";
        dropTable = "DROP TABLE " + util.getTABLENAME();
        insertValues = "INSERT INTO " + util.getTABLENAME() + " VALUES(default,?,?,?);";
        removeUser = "DELETE FROM " + util.getTABLENAME() + " where id=";
        getAllUsers = "SELECT * FROM " + util.getTABLENAME();
        clearlUsers = "DELETE FROM " + util.getTABLENAME();
    }


    public void createUsersTable() {
        try (
                Connection connection = util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(createTable);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (
                Connection connection = util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(dropTable);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (
                Connection connection = util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertValues);
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (
                Connection connection = util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(removeUser);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (
                Connection connection = util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getAllUsers);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User newUser = new User(resultSet.getString(2),
                        resultSet.getString(3), resultSet.getByte(4));
                newUser.setId(resultSet.getLong(1));
                userList.add(newUser);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (
                Connection connection = util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(clearlUsers);
        ) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
