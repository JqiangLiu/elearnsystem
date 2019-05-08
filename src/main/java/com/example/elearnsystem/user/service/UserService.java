package com.example.elearnsystem.user.service;

import com.example.elearnsystem.user.domain.User;
import com.example.elearnsystem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User find(Long id) {
        return userRepository.findById(id).get();

    }

    @Override
    public String findAvator(Long id) {
        return userRepository.findAvator(id);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }
}
