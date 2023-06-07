package com.crudproject.springboot.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudproject.springboot.model.User;
import com.crudproject.springboot.repository.UserRepo;
import com.crudproject.springboot.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo myUserRepo;

    @Override
    public List<User> searchUsers(String keyword) {
        return myUserRepo.search(keyword);
    }
    
}

