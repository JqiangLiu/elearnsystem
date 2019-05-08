package com.example.elearnsystem.user.repository;

import com.example.elearnsystem.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    @Query("select u.avatorImgPath from User u where u.id = ?1")
    public String findAvator(Long id);
}
