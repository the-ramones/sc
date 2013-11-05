package sc.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sc.controller.HomeController;
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
        protected static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Override
    public String lookupPassword(String username) {
        String result = null;
        try {
            Query q = em.createQuery(LOOKUP_PASSWORD_QUERY);
            q.setParameter("username", username);
            List<String> l = q.getResultList();
            if (l != null && !l.isEmpty()) {
                result = l.get(0);
            }
        } catch (Exception ex) {
            logger.debug("Cannot lookup a password due to error", ex);
        }
        return result;
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
