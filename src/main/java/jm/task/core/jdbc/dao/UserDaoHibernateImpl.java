package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.util.UtilConfig;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Util util;
    private final String createTable;
    private final String dropTable;
    private final String insertValues;
    private final String removeUser;
    private final String getAllUsers;
    private final String clearlUsers;


    public UserDaoHibernateImpl() {
        this(new UtilConfig().getUtil());
    }

    public UserDaoHibernateImpl(Util util) {
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
        getAllUsers = "from User";
        clearlUsers = "delete User";
    }

    @Override
    public void createUsersTable() {
        try (Session session = util.getSession();) {
            session.beginTransaction();
            session.createSQLQuery(createTable).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = util.getSession();) {
            session.beginTransaction();
            session.createSQLQuery(dropTable).executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = util.getSession();) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = util.getSession();) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = util.getSession();) {
            session.beginTransaction();
            userList = session.createQuery(getAllUsers).getResultList();
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = util.getSession();) {
            session.beginTransaction();
            session.createQuery(clearlUsers).executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }
}
