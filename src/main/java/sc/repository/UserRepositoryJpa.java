package sc.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import sc.model.User;

/**
 * JPA implementation of {@link UserRepository}
 *
 * @author Paul Kulitski
 */
@Repository
public class UserRepositoryJpa implements UserRepository {

    @PersistenceContext(name = "scPU")
    EntityManager em;

    private String LOOKUP_PASSWORD_QUERY = "select u.password from User u where u.login = :username";
    private String GET_LANGUAGE_QUERY = "select u.language from User u where u.login = :username";
    private String GET_FULLNAME_QUERY = "select u from User u where u.username= :username";
    
    @Override
    public String lookupPassword(String username) {
        Query q = em.createQuery(LOOKUP_PASSWORD_QUERY);
        q.setParameter("username", username);
        return (String) q.getSingleResult();
    }

    @Override
    public String getLanguage(String username) {
        Query q = em.createQuery(GET_LANGUAGE_QUERY);
        q.setParameter("username", username);
        return (String) q.getSingleResult();
    }

    @Override
    public User getUser(String username) {
        User user = em.find(User.class, username);
        return user;
    }

    @Override
    public void saveUser(User user) {
        System.out.println("SAVE USER: " + user);
        em.merge(user);
    }
    
    
}
