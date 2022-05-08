package com.tweetapp.service;

import com.tweetapp.dao.UserRepository;
import com.tweetapp.model.ActiveStatus;
import com.tweetapp.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            return new User();
        }

        user.setActiveStatus(ActiveStatus.ONLINE.name());
        return userRepository.save(user);
    }

    public User login(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            User u = userOptional.get();

            if (u.getPassword() != null && u.getPassword().equals(user.getPassword())) {
                u.setActiveStatus(ActiveStatus.ONLINE.name());
                userRepository.save(u);
                return u;
            }

        }

        return null;
    }


    public boolean logOut(String email) {
        log.info("email: ", email);

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActiveStatus(ActiveStatus.OFFLINE.name());
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public User resetPassword(User user) {

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            User dbUser = userOptional.get();

            dbUser.setPassword(user.getPassword());

            userRepository.save(dbUser);

            return dbUser;

        }

        return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
