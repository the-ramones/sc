package sc.service;

import sc.model.Token;

/**
 *
 * @author Paul Kulitski
 */
public interface TokenService {

    public Token findToken(String username);

    public Token findTokenByHash(String hash);

    public void addToken(String username);
    
    public void addTokenByHash(String hash);

    public void removeToken(Token token);

    public void removeToken(String username);

    public void removeTokenByHash(String hash);

    public boolean checkToken(Token token);

    public void updateToken(String username);
}
