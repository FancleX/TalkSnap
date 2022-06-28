package com.dev.userservice.repository;

import com.dev.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT id FROM user_info WHERE email = :userEmail", nativeQuery = true)
    Long getUserIdByEmail(@Param("userEmail") String email);

    @Query(value = "SELECT salt FROM user_info WHERE id = :userId", nativeQuery = true)
    String getUserSaltById(@Param("userId") Long id);

    @Modifying
    @Query(value = "UPDATE user_info SET nickname = :name WHERE id = :userId", nativeQuery = true)
    void updateNickname(@Param("name") String name, @Param("userId") Long id);

    @Modifying
    @Query(value = "UPDATE user_info SET email = :email WHERE id = :userId", nativeQuery = true)
    void updateEmail(@Param("email") String email, @Param("userId") Long id);

    @Modifying
    @Query(value = "UPDATE user_info SET password = :password WHERE id = :userId", nativeQuery = true)
    void updatePassword(@Param("password") String password, @Param("userId") Long id);

    @Modifying
    @Query(value = "UPDATE user_info SET bio = :bio WHERE id = :userId", nativeQuery = true)
    void updateBio(@Param("bio") String bio, @Param("userId") Long id);

    @Query(value = "SELECT id, nickname FROM user_info WHERE nickname LIKE CONCAT('%',:username,'%') AND id != :userId", nativeQuery = true)
    List<Map<String, Object>> searchUser(@Param("userId") Long id, @Param("username") String username);

    @Query(value = "SELECT password FROM user_info WHERE id = :userId", nativeQuery = true)
    String getPasswordById(@Param("userId") Long id);

    @Query(value = "SELECT nickname, email, bio, profile_img, bg_img, created_time FROM user_info WHERE id = :userId", nativeQuery = true)
    Map<String, Object> fetchUserProfileById(@Param("userId") Long id);
}
