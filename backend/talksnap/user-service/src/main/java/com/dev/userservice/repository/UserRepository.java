package com.dev.userservice.repository;

import com.dev.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT id FROM user_info WHERE email = :userEmail", nativeQuery = true)
    Long getUserIdByEmail(@Param("userEmail") String email);
}
