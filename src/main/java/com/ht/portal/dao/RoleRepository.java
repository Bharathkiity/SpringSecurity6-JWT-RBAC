package com.ht.portal.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ht.portal.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
    Optional<Role> findByRoleName(String roleName); // Ensure the method name matches the field name
}