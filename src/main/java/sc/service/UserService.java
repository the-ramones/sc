package sc.service;

/**
 * Contract for User services
 *
 * @author Paul Kulitski
 */
public interface UserService {
    
    public boolean checkCredentails(String username, String password);
    
    public String getLanguage(String username);
    
    public String getFullName(String username);
}
