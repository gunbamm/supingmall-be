package com.github.shoppingmallproject.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpa extends JpaRepository<UserEntity, Integer> {
    boolean existsByEmail(String email);

}
