package com.crudproject.springboot.service;

import java.util.List;

import com.crudproject.springboot.model.User;

public interface UserService {

    List<User> searchUsers(String keyword);
}
