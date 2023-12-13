package com.github.shoppingmallproject.repository.users;

import com.github.shoppingmallproject.repository.user_roles.UserRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "userEntity")
    private Collection<UserRoles> userRoles;

}
