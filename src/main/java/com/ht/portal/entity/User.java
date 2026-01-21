package com.ht.portal.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    
    private String userPassword;

    @ManyToOne
    //@JsonManagedReference  // Forward reference: Will serialize role properly
    @JoinColumn(name = "role_id") // Foreign Key column in users table
    private Role role;

	
    
}