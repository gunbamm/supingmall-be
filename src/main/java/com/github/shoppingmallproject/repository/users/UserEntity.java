package com.github.shoppingmallproject.repository.users;

import com.github.shoppingmallproject.repository.userRoles.UserRoles;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(of = "userId")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "gender", nullable = false)
    private String gender;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "image_url", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'url'")
    private String imageUrl;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "join_date", nullable = false)
    private LocalDateTime createdAt;

//    @OneToOne
//    @JoinColumn(name = "cart_id", nullable = false)
//    private CartEntity cartEntity;

    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'normal'")
    private String status = "normal";

    @Column(name = "failure_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int failureCount = 0;
    @Column(name = "deletion_date")
    private LocalDateTime deletionDate;
    @Column(name = "lock_date")
    private LocalDateTime lockDate;

    @OneToMany(mappedBy = "userEntity")
    private Collection<UserRoles> userRoles;

}
