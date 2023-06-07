package com.crudproject.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crudproject.springboot.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	@Query(value = "select * from user where email=?1", nativeQuery = true)
	User checkEmail(String email);

	@Query(value = "select * from user where email=?1 and password=?2", nativeQuery = true)
	User checkLogin(String email, String password);

    @Query(value = "SELECT u FROM User u WHERE u.name LIKE %:keyword% OR u.email LIKE %:keyword% OR u.phno LIKE %:keyword%")
    List<User> search(@Param("keyword") String keyword);





}
