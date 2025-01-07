package WebPatterns.persistence;

import WebPatterns.business.User;

public interface UserDao {
    public boolean register(User user);
    public User login(String username, String password);
}
