package com.example.elearnsystem.authority.service;

import com.example.elearnsystem.authority.domain.Authority;

import java.util.List;

public interface IAuthorityService {
    //注册，保存单条
    public void saveOne(Authority authority);

    //返回所有账号到前台，用于注册账号重复校验
    public Long getUserName(String userName);

    //删除账号，管理员管理账号
    public void delete(Long id);

    //登录校验
    public Authority login(String userName);
}
