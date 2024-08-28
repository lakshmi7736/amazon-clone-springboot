package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.Model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecifications {
    public static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true if no CategoryId is provided
            }
            return criteriaBuilder.equal(root.get("categoryId").get("id"), categoryId);
        };
    }


    public static Specification<Product> hasSubCategoryId(Long subCategoryId) {
        return (root, query, criteriaBuilder) -> {
            if (subCategoryId == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("subCategoryId").get("id"), subCategoryId);
        };
    }

    public static Specification<Product> hasNestedSubCategoryId(Long nestedSubCategoryId) {
        return (root, query, criteriaBuilder) -> {
            if (nestedSubCategoryId == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("nestedSubCategoryId").get("id"), nestedSubCategoryId);
        };
    }

    public static Specification<Product> hasSeller(String seller) {
        return (root, query, criteriaBuilder) -> {
            if (seller == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("seller").get("id"), seller);
        };
    }
    public static Specification<Product> hasRating(int averageRating) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("averageRating"), averageRating);
    }


    public static Specification<Product> hasBrand(String  brand) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("brand"), brand);
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

    public static Specification<Product> hasPriceGreaterThan(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("price"), minPrice);
    }

    public static Specification<Product> hasPriceLessThan(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}