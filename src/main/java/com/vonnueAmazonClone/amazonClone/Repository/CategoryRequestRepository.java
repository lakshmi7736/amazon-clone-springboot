package com.vonnueAmazonClone.amazonClone.Repository;

import com.vonnueAmazonClone.amazonClone.Model.Category;
import com.vonnueAmazonClone.amazonClone.Model.CategoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRequestRepository extends JpaRepository<CategoryRequest,Long> {
    @Query("SELECT u FROM CategoryRequest u WHERE u.suggestedCategoryName = :suggestedCategoryName")
    CategoryRequest findByName(@Param("suggestedCategoryName") String suggestedCategoryName);
}
