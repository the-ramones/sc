package sc.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import sc.model.Token;

/**
 *
 * @author Paul Kulitski
 */
@Repository
public class TokenRepositoryJpa implements TokenRepository {

    @PersistenceContext(name = "scPU")
    EntityManager em;

    @Override
    public Token getToken(String username) {
        return em.find(Token.class, username);
    }

    @Override
    public void addToken(Token token) {
        em.persist(token);
    }

    @Override
    public void removeToken(Token token) {
        em.remove(em.merge(token));
    }

    @Override
    public void removeToken(String username) {
        Token token = em.find(Token.class, username);
        if (token != null) {
            em.remove(token);
        }
    }

    @Override
    public void updateToken(Token token) {
        em.merge(token);
    }
}
