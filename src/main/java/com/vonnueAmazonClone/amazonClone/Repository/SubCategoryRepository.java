package com.vonnueAmazonClone.amazonClone.Repository;

import com.vonnueAmazonClone.amazonClone.Model.Subcategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

@Repository
public interface SubCategoryRepository extends JpaRepository<Subcategory,Long> {
    @Query("SELECT u FROM Subcategory u WHERE u.subCategoryName = :subCategoryName")
    Subcategory findByName(@Param("subCategoryName") String subCategoryName);


    @Query("SELECT u FROM Subcategory u WHERE u.categoryId.id = :categoryId")
    Page<Subcategory> findByCategory(@Param("categoryId") Long categoryId, Pageable pageable );
}
