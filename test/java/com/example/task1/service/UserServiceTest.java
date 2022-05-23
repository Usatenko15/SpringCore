package com.example.task1.service;

import com.example.task1.DAO.UserDAO;
import com.example.task1.model.UserImpl;
import model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserDAO userDAO;

    @Test
    void getUserById() {
        userService.getUserById(1);
        Mockito.verify(userDAO, Mockito.times(2)).getUserById(1);
    }

    @Test
    void getUserByIdWhereNoUserWithSuchId() {
        Mockito.when(userDAO.getUserById(1)).thenReturn(null);
        User user = userService.getUserById(1);
        Mockito.verify(userDAO, Mockito.times(2)).getUserById(1);
        assertNull(user);
    }

    @Test
    void getUserByEmail() {
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        userService.getUserByEmail(user.getEmail());
        Mockito.verify(userDAO, Mockito.times(1)).getUserByEmail(user.getEmail());
    }

    @Test
    void getUsersByName() {
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        userService.getUsersByName(user.getName());
        Mockito.verify(userDAO, Mockito.times(1)).getUsersByName(user.getName());
    }

    @Test
    void createUser() {
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        userService.createUser(user);
        Mockito.verify(userDAO, Mockito.times(1)).createUser(user);
    }
    @Test
    void createUserWhereUserWithSuchEmailExist() {
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Mockito.when(userDAO.getUserByEmail(user.getEmail())).thenReturn(user);
        User expectedUser = userService.createUser(user);
        Mockito.verify(userDAO, Mockito.times(1)).getUserByEmail(user.getEmail());
        Mockito.verify(userDAO, Mockito.times(0)).createUser(user);
        assertNull(expectedUser);
    }

    @Test
    void updateUserWhereNoUserWithSuchId() {
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Mockito.when(userDAO.getUserById(user.getId())).thenReturn(null);
        User expectedUser = userService.updateUser(user);
        Mockito.verify(userDAO, Mockito.times(1)).getUserById(user.getId());
        Mockito.verify(userDAO, Mockito.times(0)).getUserByEmail(user.getEmail());
        Mockito.verify(userDAO, Mockito.times(0)).updateUser(user);
        assertNull(expectedUser);
    }

    @Test
    void updateUserWhereUserWithSuchEmailExist() {
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        User userWithAnotherId = new UserImpl("Nikita", "nikita@gmail.com");
        Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
        Mockito.when(userDAO.getUserByEmail(user.getEmail())).thenReturn(userWithAnotherId);
        User expectedUser = userService.updateUser(user);
        Mockito.verify(userDAO, Mockito.times(1)).getUserById(user.getId());
        Mockito.verify(userDAO, Mockito.times(1)).getUserByEmail(user.getEmail());
        Mockito.verify(userDAO, Mockito.times(0)).updateUser(user);
        assertNull(expectedUser);
    }

    @Test
    void updateUser() {
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
        Mockito.when(userDAO.getUserByEmail(user.getEmail())).thenReturn(user);
        userService.updateUser(user);

        Mockito.verify(userDAO, Mockito.times(1)).updateUser(user);
    }

    @Test
    void deleteUser() {
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
        boolean isDelete = userService.deleteUser(user.getId());
        Mockito.verify(userDAO, Mockito.times(1)).deleteUser(user.getId());
        assertTrue(isDelete);
    }

    @Test
    void deleteUserWhereNoUserWithSuchId() {
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Mockito.when(userDAO.getUserById(user.getId())).thenReturn(null);
        boolean isDelete = userService.deleteUser(user.getId());
        Mockito.verify(userDAO, Mockito.times(0)).deleteUser(user.getId());
        assertFalse(isDelete);
    }
}