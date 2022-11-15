//package ru.nidecker.nbanews.service;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.nidecker.nbanews.AbstractTest;
//import ru.nidecker.nbanews.entity.User;
//import ru.nidecker.nbanews.repository.UserRepository;
//
//import java.util.Optional;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserServiceTest extends AbstractTest {
//
//    @Autowired
//    private UserService userService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private ConfirmationTokenService confirmationTokenService;
//
//    @Test(expected = IllegalArgumentException.class)
//    public void signUpWithExistsEmail() {
//        User user = new User();
//        user.setEmail(Mockito.anyString());
//        Mockito.when(userRepository
//                .findByEmail(user.getEmail()).isPresent())
//                .thenReturn(true);
//
//        userService.signUpUser(user);
//    }
//
//}