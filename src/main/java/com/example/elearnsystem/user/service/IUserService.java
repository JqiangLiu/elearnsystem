package com.example.elearnsystem.user.service;

import com.example.elearnsystem.user.domain.User;

public interface IUserService {

    public User find(Long id);

    public User update(User user);
}
