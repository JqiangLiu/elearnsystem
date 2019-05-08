package com.example.elearnsystem.authority.repository;

import com.example.elearnsystem.authority.domain.Authority;
import com.example.elearnsystem.authority.domain.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface AuthorityRepository extends JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority> {
    @Query("select a.id from Authority a where a.userName = ?1")
    Long getUserName(String userName);

    Authority findAllByUserName(String userName);

    Page<Authority> findAllByRole(String role, Pageable pageable);

    Page<Authority> findAllByUserName(String userName, Pageable pageable);

    Page<Authority> findAllByUserNameAndRole(String userName, String role, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Authority r where r.id in (?1)")
    public void deleteAll(Long[] ids);

    @Transactional
    @Query("select userName from Authority r where r.id in (?1)")
    public List<String> getAllUserName(Long[] ids);

}
