package sc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sc.model.Token;
import sc.repository.TokenRepository;

/**
 *
 * @author Paul Kulitski
 */
@Service
public class TokenServiceJpa implements TokenService {

    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    @Qualifier("md5Hasher")
    PasswordEncoder md5Hasher;
    protected static final Logger logger = LoggerFactory.getLogger(TokenServiceJpa.class);

    @Override
    public Token findToken(String username) {
        Token token = tokenRepository.getToken(username);
        System.out.println("IN SERVICE: username " + username);
        System.out.println("TOKED IN SERVICE: " + token);
        return token;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addToken(String username) {
        Token token = new Token();
        String hashedUsername = md5Hasher.encodePassword(username, null);
        token.setUsername(hashedUsername);
        String tokenValue = username;
        for (int i = 0; i < 10; i++) {
            tokenValue = md5Hasher.encodePassword(tokenValue, Math.random() * 1000 % 1);
        }
        token.setToken(tokenValue);
        System.out.println("GENERATED: {" + hashedUsername + " , " + tokenValue + "}");
        tokenRepository.addToken(token);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeToken(Token token) {
        tokenRepository.removeToken(token);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeToken(String username) {
        String hashed = hashUsername(username);
        tokenRepository.removeToken(hashed);
    }

    @Override
    public boolean checkToken(Token token) {
        Token origin = tokenRepository.getToken(token.getUsername());
        if (origin.getToken().equals(token.getToken())) {
            return true;
        }
        return false;
    }

    private String hashUsername(String username) {
        String hashed = null;
        try {
            hashed = md5Hasher.encodePassword(username, null);
        } catch (DataAccessException ex) {
            logger.debug("Cannot hash a username due to DataAccessError");
        }
        return hashed;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateToken(String username) {
        Token token = findToken(username);

        String hashedUsername = md5Hasher.encodePassword(username, null);
        String tokenValue = username;
        for (int i = 0; i < 10; i++) {
            tokenValue = md5Hasher.encodePassword(tokenValue, Math.random() * 1000 % 1);
        }
        token.setToken(tokenValue);
        System.out.println("GENERATED: {" + hashedUsername + " , " + tokenValue + "}");
        tokenRepository.updateToken(token);

    }
}
