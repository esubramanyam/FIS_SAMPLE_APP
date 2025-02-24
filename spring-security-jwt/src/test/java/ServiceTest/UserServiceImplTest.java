package ServiceTest;

import com.fis.deloitte.planOnboarding.dto.UserDto;
import com.fis.deloitte.planOnboarding.dto.UserRequest;
import com.fis.deloitte.planOnboarding.entity.User;
import com.fis.deloitte.planOnboarding.exception.EmailAlreadyExistsException;
import com.fis.deloitte.planOnboarding.exception.UserException;
import com.fis.deloitte.planOnboarding.exception.UsernameAlreadyExistsException;
import com.fis.deloitte.planOnboarding.repository.UserRepository;
import com.fis.deloitte.planOnboarding.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Logger logger;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private List<User> mockUsers;

    private UserRequest userRequest;
    private User user;

    @BeforeEach
    void setUp() {
        userServiceImpl = spy(userServiceImpl);
        mockUsers = new ArrayList<>();
        mockUsers.add(User.builder().username("akshita")
                .password("akshita")
                .email("akshitaswami@gmail.com")
                .city("Gurgaon")
                .contactNo("6397929899")
                .build());
        mockUsers.add(User.builder().username("sarah")
                .password("sarahjohn")
                .email("sarahjohn@gmail.com")
                .city("bangalore")
                .contactNo("6397929889")
                .build());
        userRequest = new UserRequest("akshita","akshita@gmail.com","password","Gurgaon","1234567899");
        user = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password("encodedPassword")
                .contactNo(userRequest.getContactNo())
                .city(userRequest.getCity())
                .lastLogin(LocalDateTime.now())
                .build();
    }

    @Test
    void getUsersValidTest(){
    when(userRepository.findAll()).thenReturn(mockUsers);
    List<UserDto> userDtos= userServiceImpl.getUsers();
    assertNotNull(userDtos);
    assertEquals(2, userDtos.size());
    assertEquals("akshita", userDtos.get(0).getUsername());
    assertEquals("sarah", userDtos.get(1).getUsername());
    verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUsers_EmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        List<UserDto> userDtos= userServiceImpl.getUsers();
        assertNotNull(userDtos);
        assertTrue(userDtos.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUsers_ExceptionHandling() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));
        UserException exception= assertThrows(UserException.class, () -> userServiceImpl.getUsers());
        assertEquals("Error fetching users", exception.getMessage());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void createUser_Success() throws Exception {
        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userServiceImpl.createUser(userRequest);

        assertNotNull(createdUser);
        assertEquals(userRequest.getUsername(), createdUser.getUsername());
        assertEquals(userRequest.getEmail(), createdUser.getEmail());
        verify(userServiceImpl,times(1)).createUser(any());
    }

    @Test
    void createUser_UsernameAlreadyExists() throws Exception {
        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(true);
        UsernameAlreadyExistsException thrown = assertThrows(UsernameAlreadyExistsException.class, () -> {
            userServiceImpl.createUser(userRequest);
        });
        assertEquals("Username "+ userRequest.getUsername() + " already exists", thrown.getMessage());
        verify(userServiceImpl, times(1)).createUser(any(UserRequest.class));
    }

    @Test
    void createUser_EmailAlreadyExists() throws Exception {
        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(true);
        EmailAlreadyExistsException thrown = assertThrows(EmailAlreadyExistsException.class, () -> {
            userServiceImpl.createUser(userRequest);
        });
        assertEquals("Email "+ userRequest.getEmail() + " already exists", thrown.getMessage());
        verify(userServiceImpl, times(1)).createUser(any(UserRequest.class));
    }

    @Test
    void createUser_GenericException() throws Exception {
        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        Exception thrown = assertThrows(Exception.class, () -> {
            userServiceImpl.createUser(userRequest);
        });
        assertEquals("java.lang.RuntimeException: Database error" ,thrown.getMessage());
        verify(userServiceImpl, times(1)).createUser(any(UserRequest.class));
    }

    @Test
    void getLastLoginTime_UserExists() {
        when(userRepository.findByUsername(userRequest.getUsername())).thenReturn(Optional.of(user));
        LocalDateTime lastLoginTime = userServiceImpl.getLastLoginTime(userRequest.getUsername());
        assertNotNull(lastLoginTime);
        assertEquals(user.getLastLogin(),lastLoginTime);
    }

    @Test
    void getLastLoginTime_UserDoesNotExists() {
        when(userRepository.findByUsername(userRequest.getUsername())).thenReturn(Optional.empty());
        LocalDateTime lastLoginTime = userServiceImpl.getLastLoginTime(userRequest.getUsername());
        assertNull(lastLoginTime);
    }

    @Test
    void saveLastLogin_UserExists() {
        when(userRepository.findByUsername(userRequest.getUsername())).thenReturn(Optional.of(user));
        userServiceImpl.saveLastLogin(userRequest.getUsername());
        verify(userRepository, times(1)).save(user);
        assertNotNull(user.getLastLogin());
    }

    @Test
    void saveLastLogin_UserDoesNotExist() {
        when(userRepository.findByUsername(userRequest.getUsername())).thenReturn(Optional.empty());
        userServiceImpl.saveLastLogin(userRequest.getUsername());
        verify(userRepository,never()).save(any(User.class));
    }
}
