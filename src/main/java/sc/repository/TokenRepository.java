package sc.repository;

import sc.model.Token;

/**
 * Contract for {@link Token} repositories
 *
 * @author Paul Kulitski
 */
public interface TokenRepository {

    public Token getToken(String username);

    public Token getTokenByHash(String hash);

    public void addToken(Token token);

    public void removeToken(Token token);

    public void removeToken(String username);

    public void removeTokenByHash(String hash);

    public void updateToken(Token token);
}
