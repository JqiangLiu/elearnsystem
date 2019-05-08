package com.example.elearnsystem.authority.service;

import com.example.elearnsystem.authority.domain.Authority;
import com.example.elearnsystem.authority.domain.Manager;
import com.example.elearnsystem.authority.domain.dto.AuthorityDTO;
import com.example.elearnsystem.authority.domain.dto.ManagerDTO;
import com.example.elearnsystem.authority.repository.AuthorityRepository;
import com.example.elearnsystem.authority.repository.ManagerRepository;
import com.example.elearnsystem.common.util.BeanUtils;
import com.example.elearnsystem.userRecorder.repository.UserRecorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthorityService implements IAuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private UserRecorderRepository userRecorderRepository;

    @Override
    public void saveOne(Authority authority) {
        authorityRepository.save(authority);
    }

    @Override
    public void saveManager(Manager manager) {
        managerRepository.save(manager);
    }

    @Override
    public Long getUserName(String userName) {
        return authorityRepository.getUserName(userName);
    }

    @Override
    public Long getManagerName(String userName) {
        return managerRepository.getManagerName(userName);
    }

    @Override
    public void changeDownload(Long id,Boolean download) {
        Authority authority = authorityRepository.findById(id).get();
        authority.setDownload(download);
        authorityRepository.save(authority);
    }

    @Override
    public void deleteUser(Long[] ids) {
        List<String> userList = authorityRepository.getAllUserName(ids);
        List<String> recorderPathList = userRecorderRepository.getAllUserName(userList);
        for (String s : recorderPathList) {
            File file = new File("E://eSystemResources"+s);
            file.delete();
        }
        authorityRepository.deleteAll(ids);
        userRecorderRepository.deleteAllByUserName(userList);
    }

    @Override
    public void deleteManager(Long[] ids) {
        managerRepository.deleteAll(ids);
    }

    @Override
    public Authority login(String userName) {
        return authorityRepository.findAllByUserName(userName);
    }

    @Override
    public Manager managerLogin(String userName) {
        return managerRepository.findAllByUserName(userName);
    }

    @Override
    public List<AuthorityDTO> findAllUser(String userName, String role, Pageable pageable) {
        List<AuthorityDTO> DTOList = new ArrayList<>();
        Page<Authority> res;
        List<Authority> list;
        long sum = 0;
        /*先行判断是否有条件*/
        if ((userName != null && !userName.equals("")) || (role != null && !role.equals(""))) {
            if (!(userName == null) && !(role == null)) {
                res = authorityRepository.findAllByUserNameAndRole(userName, role, pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            } else if (userName != null) {
                res = authorityRepository.findAllByUserName(userName, pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            } else {
                res = authorityRepository.findAllByRole(role, pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            }
        } else {
            res = authorityRepository.findAll(pageable);
            list = res.getContent();
            sum = res.getTotalElements();
        }
        for (Authority s : list
        ) {
            AuthorityDTO temp = new AuthorityDTO();
            BeanUtils.copyProperties(s, temp);
            temp.setSum(sum);
            DTOList.add(temp);
        }
        return DTOList;
    }

    @Override
    public List<ManagerDTO> findAllManager(String userName, String role, Pageable pageable) {
        List<ManagerDTO> DTOList = new ArrayList<>();
        Page<Manager> res;
        List<Manager> list;
        long sum = 0;
        /*先行判断是否有条件*/
        if ((userName != null && !userName.equals("")) || (role != null && !role.equals(""))) {
            if (!(userName == null) && !(role == null)) {
                res = managerRepository.findAllByUserNameAndRole(userName, role, pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            } else if (userName != null) {
                res = managerRepository.findAllByUserName(userName, pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            } else {
                res = managerRepository.findAllByRole(role, pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            }
        } else {
            res = managerRepository.findAll(pageable);
            list = res.getContent();
            sum = res.getTotalElements();
        }
        for (Manager s : list
        ) {
            ManagerDTO temp = new ManagerDTO();
            BeanUtils.copyProperties(s, temp);
            temp.setSum(sum);
            DTOList.add(temp);
        }
        return DTOList;
    }
}
