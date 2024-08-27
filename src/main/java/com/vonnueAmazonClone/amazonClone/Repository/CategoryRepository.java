package com.vonnueAmazonClone.amazonClone.Repository;

import com.vonnueAmazonClone.amazonClone.Model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT u FROM Category u WHERE u.name = :name")
    Category findByName(@Param("name") String name);


}
