package com.ht.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ht.portal.entity.Role;
import com.ht.portal.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin("http://localhost:4200") // angular url acess by angular or any
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Admin can view all roles
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Admin can create new roles (optional, based on your system design)
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }
}
