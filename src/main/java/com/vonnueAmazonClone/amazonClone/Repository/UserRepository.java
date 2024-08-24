package com.vonnueAmazonClone.amazonClone.Repository;

import com.vonnueAmazonClone.amazonClone.Model.Seller;
import com.vonnueAmazonClone.amazonClone.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
