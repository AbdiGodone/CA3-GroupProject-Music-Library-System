package WebPatterns.persistence;

import WebPatterns.business.User;
import lombok.extern.slf4j.Slf4j;

import WebPatterns.business.User;

import java.io.FileNotFoundException;
import java.sql.*;

@Slf4j
public class UserDaoImpl extends MySQLDao implements UserDao {
    public UserDaoImpl() {super();}
    public UserDaoImpl(String propertiesFile){
        super(propertiesFile);
    }

    public UserDaoImpl(Connection c) {
        super(c);
    }




    @Override
    public boolean register(User user) {
        boolean added = false;

        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO users VALUES(?, ?, ?, ?, ?)")) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstname());
            ps.setString(4, user.getLastname());
            ps.setString(5, user.getEmail());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                added = true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            log.error("SQLIntegrityConstraintViolationException occurred when attempting to register new User", e);
        } catch (SQLException e) {
            log.error("SQLException occurred when attempting to register new User", e);
        }

        super.freeConnection(c);

        return added;
    }

    @Override
    public User login(String username, String password) {
        User user = null;
        Connection c = super.getConnection();
        try (PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            ps.setString(1, username);
            ps.setString(2, password);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    user = mapRow(rs);
                }
            }catch (SQLException e) {
                log.error("SQLException occurred when processing login query resultset", e);
            }
        }catch (SQLException e) {
            log.error("SQLException occurred when attempting to login User", e);
        }
        super.freeConnection(c);

        return user;
    }

    private static User mapRow(ResultSet rs) throws SQLException {
        return User.builder()
                .username(rs.getString("username"))
                .firstname(rs.getString("firstName"))
                .lastname(rs.getString("lastName"))
                .email(rs.getString("email"))
                .build();
    }
    private boolean  validUser(User u){
        if(u == null){
            return false;
        }
        if(u.getUsername() == null || u.getUsername().isBlank()){
            return false;
        }
        if(u.getPassword() == null || u.getPassword().isBlank()){
            return false;
        }
        if(u.getEmail() == null || u.getEmail().isBlank()){
            return false;
        }
        return true;
    }


    public static void main(String[] args) throws FileNotFoundException {
        UserDao userDao = new UserDaoImpl("database.properties");
        User user = new User("julieOla", "j123", "Julie", "Ola", "julie@Ola.com");
        boolean added = userDao.register(user);
        if(added){
            System.out.println(user + " was added correctly");
        }else{
            System.out.println(user + "could not be added.");
        }

        String username = "julieOla";
        String password = "j123";
        User loggedIn = userDao.login(username, password);
        if(loggedIn != null){
            System.out.println("Logged in as "+ loggedIn);
        }else{
            System.out.println("User with username " + username + " could not be logged in");
        }
    }
}
