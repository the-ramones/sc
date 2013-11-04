package sc.service;

import sc.model.Token;

/**
 *
 * @author Paul Kulitski
 */
public interface TokenService {

    public Token findToken(String username);

    public void addToken(String username);

    public void removeToken(Token token);

    public void removeToken(String username);

    public boolean checkToken(Token token);
}
