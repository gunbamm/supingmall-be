package com.github.shoppingmallproject.repository.userRoles;

import com.github.shoppingmallproject.repository.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "user_roles")
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_roles_id")
    private Integer userRolesId;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "role_id",nullable = false)
    private Roles roles;
}
