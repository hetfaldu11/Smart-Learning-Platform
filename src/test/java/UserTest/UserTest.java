package UserTest;

import com.fm.smartlearningplatform.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import com.fm.smartlearningplatform.model.*;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserTest {
//    @Autowired
//    UserRepository userrepository;
    @Test
    public void createUser(){
        User user = new User();
        user.setId(1);
        user.setEmail("het@gmail.com");
       user.setRole(UserRole.USER);
        user.setPassword("{noop}123");
        user.setEnabled(1);
        user.setLastLoginAt(LocalDateTime.now());
//        user.setfailedLoginAttempt();
        user.setPasswordChangedAt(LocalDateTime.now());
//        user.setAccountLockedntil();
        user.setLastSeenAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt();
//        user.setDeletedAt();
        assertEquals(1,user.getId(),"User is not created.");
    }

    @Test
    public void addUser(){
        User user = new User();
        user.setId(1);
        user.setEmail("het@gmail.com");
       user.setRole(UserRole.USER);
        user.setPassword("{noop}123");
        user.setEnabled(1);
        user.setLastLoginAt(LocalDateTime.now());
        user.setPasswordChangedAt(LocalDateTime.now());
        user.setLastSeenAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
    }
}