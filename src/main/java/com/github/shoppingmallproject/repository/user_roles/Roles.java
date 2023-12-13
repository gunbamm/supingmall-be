package com.github.shoppingmallproject.repository.user_roles;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "roles")
public class Roles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roles_id")
    private Integer roleId;

    @Column(name = "name",nullable = false)
    private String name;
}

