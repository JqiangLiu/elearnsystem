package com.example.elearnsystem.authority.repository;

import com.example.elearnsystem.authority.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface AuthorityRepository extends JpaRepository<Authority,Long>, JpaSpecificationExecutor<Authority> {
    @Query("select a.id from Authority a where a.userName = ?1")
    public Long getUserName(String userName);

    public Authority findAllByUserName(String userName);
}
