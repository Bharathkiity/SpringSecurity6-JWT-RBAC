package com.ht.portal.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ht.portal.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
	
	Optional<User> findByUserName(String userName); // Updated method to use 'userName'

    void deleteByUserName(String username);

    boolean existsByUserName(String username);

}