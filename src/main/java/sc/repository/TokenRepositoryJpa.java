package sc.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    private static final String GET_BY_HASHED_USERNAME_QUERY =
            "select t from Token t where t.hashedUsername = :hashedUsername";

    @Override
    public Token getTokenByHash(String hash) {
        Query query = em.createQuery(GET_BY_HASHED_USERNAME_QUERY);
        query.setParameter("hashedUsername", hash);
        List<Token> l = query.getResultList();
        if (l != null && l.size() >= 1) {
            return l.get(0);
        }
        return null;
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
    public void removeTokenByHash(String hash) {
        Token token = getTokenByHash(hash);
        if (token != null) {
            removeToken(token);
        }
    }

    @Override
    public void updateToken(Token token) {
        em.merge(token);
    }
}
