package com.example.elearnsystem.authority.repository;

import com.example.elearnsystem.authority.domain.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ManagerRepository extends JpaRepository<Manager, Long>, JpaSpecificationExecutor<Manager> {
    @Query("select a.id from Manager a where a.userName = ?1")
    Long getManagerName(String userName);

    /**
     * 登录
     */
    Manager findAllByUserName(String userName);

    Page<Manager> findAllByRole(String role, Pageable pageable);

    Page<Manager> findAllByUserName(String userName, Pageable pageable);

    Page<Manager> findAllByUserNameAndRole(String userName, String role, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Manager r where r.id in (?1)")
    public void deleteAll(Long[] ids);
}
