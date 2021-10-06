package service.dbService;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    private final Connection connection;
    private final UsersDAO dao;

    public DBService(){
        connection = getMysqlConnection();
        dao = new UsersDAO(connection);
        try {
            dao.createTable();
        } catch (Exception e) {
            throw new RuntimeException("Can not create table");
        }
    }

    public UsersDataSet getUser(String login) throws DBException {
        try {
            return (dao.getUser(login));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean checkUserExists(String login) throws DBException {
        try {
            return (dao.checkUserExists(login));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void addUser(UsersDataSet usersDataSet) throws DBException {
        try {
            connection.setAutoCommit(false);
            dao.insertUser(usersDataSet.getLogin(), usersDataSet.getPass(), usersDataSet.getEmail());
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) { }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) { }
        }
    }


    public void cleanUp() throws DBException {
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();
            url.append("jdbc:mysql://localhost:3306/accounts?user=root&password=root&serverTimezone=UTC");
            System.out.println("URL: " + url + "\n");
            Connection con = DriverManager.getConnection(url.toString());
            System.out.println(con.toString());
            return DriverManager.getConnection(url.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can not create connection");
    }
}
