package com.github.shoppingmallproject.repository.product;

import com.github.shoppingmallproject.repository.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "product_status", length = 30, nullable = false, columnDefinition = "varchar(30) default '판매중'")
    private String productStatus;


    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;


    @Column(name = "finish_at")
    private LocalDate finishAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "productEntity")
    private List<ProductPhoto> productPhotos;
    @OneToMany(mappedBy = "productEntity")
    private List<ProductOption> productOptions;




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
