package sc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sc.model.Token;
import sc.repository.TokenRepository;

/**
 *
 * @author Paul Kulitski
 */
public class TokenServiceJpa implements TokenService {

    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    @Qualifier("md5Hasher")
    PasswordEncoder md5Hasher;

    @Override
    public Token findToken(String username) {
        return tokenRepository.getToken(username);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addToken(String username) {
        Token token = new Token();
        String hashedUsername = md5Hasher.encodePassword(username, null);
        token.setUsername(hashedUsername);
        String tokenValue = username;
        for (int i = 0; i < 10; i++) {
            tokenValue = md5Hasher.encodePassword(tokenValue, Math.random() * 100 % 1);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeToken(Token token) {
        tokenRepository.removeToken(token);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeToken(String username) {
        tokenRepository.removeToken(username);
    }

    @Override
    public boolean checkToken(Token token) {
        Token origin = tokenRepository.getToken(token.getUsername());
        if (origin.getToken().equals(token.getToken())) {
            return true;
        }
        return false;
    }
}
