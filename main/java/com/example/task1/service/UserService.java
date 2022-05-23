package com.example.task1.service;

import com.example.task1.DAO.UserDAO;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;

    public User getUserById(long userId) {
        if (userDAO.getUserById(userId) == null) {
            logger.error("No such user with id: {}", userId);
        }
        return userDAO.getUserById(userId);
    }

    /**
     * Gets user by its email. Email is strictly matched.
     *
     * @return User.
     */
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    /**
     * Get list of users by matching name. Name is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param name     Users name or it's part.
     * @param pageSize Pagination param. Number of users to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of users.
     */
    public List<User> getUsersByName(String name) {
        return userDAO.getUsersByName(name);
    }

    /**
     * Creates new user. User id should be auto-generated.
     *
     * @param user User data.
     * @return Created User object.
     */
    public User createUser(User user) {
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            logger.error("User with email " + user.getEmail() + " exist.");
            return null;
        }
        return userDAO.createUser(user);
    }

    /**
     * Updates user using given data.
     *
     * @param user User data for update. Should have id set.
     * @return Updated User object.
     */
    public User updateUser(User user) {
        if (userDAO.getUserById(user.getId()) == null) {
            logger.error("No such user with id: " + user.getId());
            return null;
        }
        if (userDAO.getUserByEmail(user.getEmail()).getId()!= user.getId()) {
            logger.error("User with email \"" + user.getEmail() + "\" exist.");
            return null;
        }
        return userDAO.updateUser(user);
    }

    /**
     * Deletes user by its id.
     *
     * @param userId User id.
     * @return Flag that shows whether user has been deleted.
     */
    public boolean deleteUser(long userId) {
        if (userDAO.getUserById(userId) == null) {
            logger.error("No such user with id: " + userId);
            return false;
        }
        userDAO.deleteUser(userId);
        return true;
    }
}
