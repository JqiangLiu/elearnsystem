package com.example.elearnsystem.authority.service;

import com.example.elearnsystem.authority.domain.Authority;
import com.example.elearnsystem.authority.domain.Manager;
import com.example.elearnsystem.authority.domain.dto.AuthorityDTO;
import com.example.elearnsystem.authority.domain.dto.ManagerDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAuthorityService {
    //注册，保存单条
    public void saveOne(Authority authority);

    //添加管理员账号
    public void saveManager(Manager manager);

    //返回所有账号到前台，用于注册账号重复校验
    public Long getUserName(String userName);

    //返回所有账号到前台，用于注册账号重复校验
    public Long getManagerName(String managerName);

    //删除账号，管理员管理账号
    public void deleteUser(Long[] ids);

    //登录校验
    public Authority login(String userName);

    //管理员登录
    public Manager managerLogin(String userName);

    /**管理用户下载权限*/
    public void changeDownload(Long id,Boolean download);

    //后台查找用户账号
    public List<AuthorityDTO> findAllUser(String userName, String role, Pageable pageable);

    /**后台查找管理员账号*/
    public List<ManagerDTO> findAllManager(String userName, String role, Pageable pageable);

    //删除管理员账号
    public void deleteManager(Long[] ids);
}
