package com.github.shoppingmallproject.repository.review;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "reviewId")
@Entity
@Table(name = "review")
public class ReviewEntity {

    @Id @Column(name = "review_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity userEntity;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductEntity productEntity;

    @Column(name = "review_contents")
    private String reviewContents;

    @Column(name = "create_at", nullable = false)
    private String createAt;

    @Column(name = "score", nullable = false, columnDefinition = "CHECK(score >= 0 AND score <= 5")
    private Integer score;

}
