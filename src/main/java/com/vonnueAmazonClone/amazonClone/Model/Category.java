package com.vonnueAmazonClone.amazonClone.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Column(columnDefinition = "LONGBLOB",length = Integer.MAX_VALUE)
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
