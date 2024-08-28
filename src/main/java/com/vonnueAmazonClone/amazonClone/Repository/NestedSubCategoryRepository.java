package com.vonnueAmazonClone.amazonClone.Repository;

import com.vonnueAmazonClone.amazonClone.Model.NestedSubCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NestedSubCategoryRepository extends JpaRepository<NestedSubCategory,Long> {
    @Query("SELECT u FROM NestedSubCategory u WHERE u.nestedSubCategoryName = :nestedSubCategoryName")
    NestedSubCategory findByName(@Param("nestedSubCategoryName") String nestedSubCategoryName);


    @Query("SELECT u FROM NestedSubCategory u WHERE u.subCategoryId.id = :subCategoryId")
    Page<NestedSubCategory> findBySubCategory(@Param("subCategoryId") Long subCategoryId, Pageable pageable );
}