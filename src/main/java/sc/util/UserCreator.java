package sc.util;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import sc.model.User;
import sc.service.UserService;

/**
 * Fills the user database on startup
 *
 * @author Paul Kulitski
 */
@Component
@DependsOn("userServiceJpa")
public class UserCreator {

    @Autowired
    UserService userService;
    @Autowired
    @Qualifier("sha1Encryptor")
    PasswordEncoder passwordEncoder;

    protected final static Logger logger = LoggerFactory.getLogger(UserCreator.class);
    
    @PostConstruct
    public void fillUserDatabase() {
        try {
            List<User> users = new ArrayList<User>(10);
            User user = new User("user", null, "User", "User", null);
            user.setPassword(passwordEncoder.encodePassword("user", null));
            users.add(user);
            User admin = new User("admin", "admin", "Admin", "Admin", null);
            admin.setPassword(passwordEncoder.encodePassword("admin", null));
            users.add(admin);
            User bill = new User("billy", null, "Bill", "Clinton", "en");
            bill.setPassword(passwordEncoder.encodePassword("dominance", null));
            users.add(bill);
            User paul = new User("paul", null, "Paul", "Krzyzecki", "be");
            paul.setPassword(passwordEncoder.encodePassword("greenway", null));
            users.add(paul);
            for (User u : users) {
                userService.addUser(u);
            }
        } catch (DataAccessException ex) {
            logger.error("Cannot fill up a user database. There will be zero users in it.");
        }
    }
}
