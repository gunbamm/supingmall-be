package com.github.shoppingmallproject.repository.review;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
public class ReviewEntitySihu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "review_contents", columnDefinition = "LONGTEXT")
    private String reviewContents;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "score", nullable = false)
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
}
