package sc.repository;

import sc.model.User;

/**
 * COntract for User repositories
 *
 * @author Paul Kulitski
 */
public interface UserRepository {

    public String lookupPassword(String username);

    public String getLanguage(String username);

    public User getUser(String username);
}
