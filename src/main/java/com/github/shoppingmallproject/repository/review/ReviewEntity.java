package com.github.shoppingmallproject.repository.review;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "reviewId")
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Table(name = "review")
public class ReviewEntity {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private UserEntity userEntity;

    @Column(name = "review_contents", columnDefinition = "LONGTEXT")
    private String reviewContents;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "score", nullable = false, columnDefinition = "CHECK(score >= 1 AND score <= 5")
    private Integer score;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne
    private ProductEntity productEntity;

}
