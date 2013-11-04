package sc.repository;

import sc.model.Token;

/**
 * Contract for {@link Token} repositories
 * 
 * @author Paul Kulitski
 */
public interface TokenRepository {

    public Token getToken(String username);
    
    public void addToken(Token token);
    
    public void removeToken(Token token);

    public void removeToken(String username);

    public void updateToken(Token token);
}
