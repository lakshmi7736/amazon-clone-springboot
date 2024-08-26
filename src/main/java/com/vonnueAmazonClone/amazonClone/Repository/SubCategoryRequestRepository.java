package com.vonnueAmazonClone.amazonClone.Repository;

import com.vonnueAmazonClone.amazonClone.Model.CategoryRequest;
import com.vonnueAmazonClone.amazonClone.Model.SubCategoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRequestRepository extends JpaRepository<SubCategoryRequest,Long> {
    @Query("SELECT u FROM SubCategoryRequest u WHERE u.suggestedSubCategoryName = :suggestedSubCategoryName")
    SubCategoryRequest findByName(@Param("suggestedSubCategoryName") String suggestedSubCategoryName);
}
