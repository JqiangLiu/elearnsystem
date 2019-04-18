package com.example.elearnsystem.authority.service;

import com.example.elearnsystem.authority.domain.Authority;
import com.example.elearnsystem.authority.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthorityService implements IAuthorityService{

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void saveOne(Authority authority) {
         authorityRepository.save(authority);
    }

    @Override
    public Long getUserName(String userName) {
        return authorityRepository.getUserName(userName);
    }

    @Override
    public void delete(Long id) {
        authorityRepository.deleteById(id);
    }

    @Override
    public Authority login(String userName) {
        return authorityRepository.findAllByUserName(userName);
    }
}
