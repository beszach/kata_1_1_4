package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import java.sql.*;
import java.util.Properties;

public class Util {

    private String URL;
    private String HOSTNAME;
    private String PORT;
    private String LOGIN;
    private String PASSWORD;
    private String TABLENAME;
    private String DATABASENAME;

    public Util() {
    }

    public Connection getConnection() throws SQLException {
        String URL = "jdbc:mysql://"+HOSTNAME+":"+PORT+"/"+DATABASENAME+"?useSSL=false";
        return DriverManager.getConnection(URL, LOGIN, PASSWORD);
    }

    public Session getSession() throws PersistenceException{
        return new Configuration().setProperties(getPropertiesConnection()).
                addAnnotatedClass(User.class).buildSessionFactory().getCurrentSession();

    }
    private Properties getPropertiesConnection(){
        Properties properties= new Properties();
        String URL = "jdbc:mysql://"+HOSTNAME+":"+PORT+"/"+DATABASENAME+"?useSSL=false";
        properties.setProperty("hibernate.connection.url", URL);
        properties.setProperty("hibernate.connection.username", LOGIN);
        properties.setProperty("hibernate.connection.password", PASSWORD);
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.current_session_context_class",
                "org.hibernate.context.internal.ThreadLocalSessionContext");
        return properties;
    }

    public String getHOSTNAME() {
        return HOSTNAME;
    }

    public void setHOSTNAME(String HOSTNAME) {
        this.HOSTNAME = HOSTNAME;
    }

    public String getPORT() {
        return PORT;
    }

    public void setPORT(String PORT) {
        this.PORT = PORT;
    }

    public String getLOGIN() {
        return LOGIN;
    }

    public void setLOGIN(String LOGIN) {
        this.LOGIN = LOGIN;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getTABLENAME() {
        return TABLENAME;
    }

    public void setTABLENAME(String TABLENAME) {
        this.TABLENAME = TABLENAME;
    }

    public String getDATABASENAME() {
        return DATABASENAME;
    }

    public void setDATABASENAME(String DATABASENAME) {
        this.DATABASENAME = DATABASENAME;
    }

}
