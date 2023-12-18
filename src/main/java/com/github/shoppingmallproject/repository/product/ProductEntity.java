package com.github.shoppingmallproject.repository.product;

import com.github.shoppingmallproject.repository.users.UserEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @Column(name = "description_photo", length = 255)
    private String descriptionPhoto;

    @Column(name = "product_price", nullable = false)
    private int productPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "product_status", length = 30, nullable = false, columnDefinition = "varchar(30) default '판매중'")
    private String productStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finish_at")
    private Date finishAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    public enum Category {
        상의("상의"),
        하의("하의"),
        신발("신발");

        private final String value;

        Category(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
