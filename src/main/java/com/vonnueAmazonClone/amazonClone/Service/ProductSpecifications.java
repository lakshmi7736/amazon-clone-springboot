package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.Model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true if no subCategoryId is provided
            }
            return criteriaBuilder.equal(root.get("categoryId").get("id"), categoryId);
        };
    }



    public static Specification<Product> hasSubCategoryId(Long subCategoryId) {
        return (root, query, criteriaBuilder) -> {
            if (subCategoryId == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true if no subCategoryId is provided
            }
            return criteriaBuilder.equal(root.get("subCategoryId").get("id"), subCategoryId);
        };
    }

    public static Specification<Product> isPrime(boolean prime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("prime"), prime);
    }

    public static Specification<Product> isCodAvailable(boolean cod) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("cod"), cod);
    }

    public static Specification<Product> isMadeForAmazon(boolean madeForAmazon) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("madeForAmazon"), madeForAmazon);
    }
}