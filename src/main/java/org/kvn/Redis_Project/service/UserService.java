package org.kvn.Redis_Project.service;


import org.kvn.Redis_Project.model.User;
import org.kvn.Redis_Project.repository.UserCacheRepository;
import org.kvn.Redis_Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCacheRepository userCacheRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        User user = userCacheRepository.getUser(id);
        if (user != null)
            return user;
        user = userRepository.findById(id).orElse(null);
        if (user != null)
            userCacheRepository.setUser(id, user);
        return user;
    }

    public User createUser(User user) {
        userRepository.save(user);
        userCacheRepository.setUser(user.getId(), user);
        return user;
    }

    public User updateUser(int id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            userRepository.save(user);
            userCacheRepository.setUser(user.getId(), user);
            return user;
        }
        return null;
    }

    public void deleteUser(int id) {
        userCacheRepository.deleteUser(id);
        userRepository.deleteById(id);
    }
}
