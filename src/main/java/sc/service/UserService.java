package sc.service;

import sc.model.User;

/**
 * Contract for User services
 *
 * @author Paul Kulitski
 */
public interface UserService {
    
    public boolean checkCredentails(String username, String password);
    
    public String getLanguage(String username);
    
    public String getFullName(String username);
    
    public void addUser(User user);
}
