package com.chess.chessApp;

import com.chess.chessApp.entity.Friends;
import com.chess.chessApp.entity.User;
import com.chess.chessApp.repository.FriendsRepository;
import com.chess.chessApp.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChessAppApplication.class)
public class DataBaseTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendsRepository friendsRepository;


    @Test
    public void saveAndFindUserTest() {
        User user = new User();
        user.setUsername("testUser1");
        user.setEmail("testUser1@gmail.com");
        user.setPassword("testUser11!");
        user.setGameCode("TESTGAMECODE");
        userRepository.save(user);

        User userSaved = userRepository.findByUsername("testUser1");
        User userSaved2 = userRepository.findByEmail("testUser1@gmail.com");
        User userSaved3 = userRepository.findByGameCode("TESTGAMECODE");


        assertEquals(user.getUsername(), userSaved.getUsername());
        assertEquals(user.getEmail(), userSaved.getEmail());
        assertEquals(user.getPassword(), userSaved.getPassword());

        assertEquals(user.getUsername(), userSaved2.getUsername());
        assertEquals(user.getEmail(), userSaved2.getEmail());
        assertEquals(user.getPassword(), userSaved2.getPassword());

        assertEquals(user.getUsername(), userSaved3.getUsername());
        assertEquals(user.getEmail(), userSaved3.getEmail());
        assertEquals(user.getPassword(), userSaved3.getPassword());

        userRepository.delete(user);
    }

    @Test
    public void saveAndFindFriends() {
        User user = new User();
        user.setUsername("testUser2");
        user.setEmail("testUser2@gmail.com");
        user.setPassword("testUser11!");
        userRepository.save(user);

        User user2 = new User();
        user2.setUsername("testUser3");
        user2.setEmail("testUser3@gmail.com");
        user2.setPassword("testUser11!");
        userRepository.save(user2);

        Friends friends = new Friends(user, user2, true);
        friendsRepository.save(friends);

        Friends friendsResult = friendsRepository.findBySenderAndRecipient("testUser2", "testUser3");

        assertEquals(friends.getSender().getUsername(), friendsResult.getSender().getUsername());
        assertEquals(friends.getRecipient().getUsername(), friendsResult.getRecipient().getUsername());

        friendsRepository.delete(friends);

        userRepository.delete(user);
        userRepository.delete(user2);
    }

}
