package com.vonnueAmazonClone.amazonClone.Repository;

import com.vonnueAmazonClone.amazonClone.Model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<Subcategory,Long> {
    @Query("SELECT u FROM Subcategory u WHERE u.subCategoryName = :subCategoryName")
    Subcategory findByName(@Param("subCategoryName") String subCategoryName);
}
