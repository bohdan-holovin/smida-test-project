package com.holovin.smidatestproject.model;

import com.holovin.smidatestproject.repository.mapper.RoleSetMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    @Convert(converter = RoleSetMapper.class)
    private Set<Role> roles;
}
