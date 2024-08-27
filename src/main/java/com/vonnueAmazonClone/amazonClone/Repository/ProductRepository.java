package com.vonnueAmazonClone.amazonClone.Repository;

import com.vonnueAmazonClone.amazonClone.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
//public interface ProductRepository extends JpaRepository<Product,Long> {
//    Page<Product> findByPrime(boolean prime, Pageable pageable);
//    Page<Product> findByMadeForAmazon(boolean madeForAmazon, Pageable pageable);
//
//    Page<Product> findByCod(boolean cod, Pageable pageable);
//
//    @Query("SELECT p FROM Product p WHERE p.categoryId.id = :categoryId")
//    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
//
//    @Query("SELECT p FROM Product p WHERE p.subCategoryId.id = :subCategoryId")
//    Page<Product> findBySubCategoryId(@Param("subCategoryId") Long subCategoryId, Pageable pageable);
//}
