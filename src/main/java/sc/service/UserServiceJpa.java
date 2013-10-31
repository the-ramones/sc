package sc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sc.model.User;
import sc.repository.UserRepository;

/**
 * JPA implementation of {@link UserService}
 *
 * @author Paul Kulitski
 */
@Service
public class UserServiceJpa implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean checkCredentails(String username, String passwordAttempt) {
        String password = userRepository.lookupPassword(username);
        if (password.equals(passwordAttempt)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getLanguage(String username) {
        return userRepository.getLanguage(username);
    }

    @Override
    public String getFullName(String username) {
        User user = userRepository.getUser(username);
        StringBuilder sb = new StringBuilder(512);
        sb.append(user.getFirstname()).append(' ').append(user.getLastname());
        return sb.toString();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        userRepository.saveUser(user);
    }
}
