package com.example.elearnsystem.authority.controller;

import com.example.elearnsystem.authority.domain.Authority;
import com.example.elearnsystem.authority.domain.Manager;
import com.example.elearnsystem.authority.domain.MyResult;
import com.example.elearnsystem.authority.domain.dto.AuthorityDTO;
import com.example.elearnsystem.authority.domain.dto.ManagerDTO;
import com.example.elearnsystem.authority.service.IAuthorityService;
import com.example.elearnsystem.common.page.MyPageRequest;
import com.example.elearnsystem.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class AuthorityController {
    @Autowired
    private IAuthorityService iAuthorityService;

    @PostMapping("/register")
    public String register(@RequestBody Authority authority) {
        try {
            User user = new User();
            user.setAvatorImgPath("/img/headImg/defineImg.png");
            authority.setDownload(true);
            authority.setRole("user");
            authority.setUserMsg(user);
            iAuthorityService.saveOne(authority);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @GetMapping("/getUserName")
    public int getUserName(@RequestParam(name = "userName") String userName) {
        if (iAuthorityService.getUserName(userName) == null) {
            return 1;
        } else {
            return 0;
        }
    }

    @GetMapping("/getManagerName")
    public int getManagerName(@RequestParam(name = "userName") String userName) {
        if (iAuthorityService.getManagerName(userName) == null) {
            return 1;
        } else {
            return 0;
        }
    }

    @PostMapping("/login")
    public MyResult login(@RequestBody Authority authority, HttpSession session) {
        String token = UUID.randomUUID().toString().replace("-", "");
        String userName = authority.getUserName();
        String password = authority.getPassword();
        Authority entity = iAuthorityService.login(userName);
        MyResult myResult = null;
        if (entity != null && entity.getPassword().equals(password)) {
            session.setAttribute("id", entity.getId());
            session.setAttribute("userName", entity.getUserName());
            session.setAttribute("password", entity.getPassword());
            myResult = new MyResult(entity, token, "success");
        } else {
            myResult = new MyResult(entity, "", "false");
        }
        return myResult;
    }

    @PostMapping("/changePassword")
    public String changePassword() {
        return null;
    }

    @PostMapping("/managerLogin")
    public MyResult managerLogin(@RequestBody Manager manager, HttpSession session) {
        String token = UUID.randomUUID().toString().replace("-", "");
        String userName = manager.getUserName();
        String password = manager.getPassword();
        Manager entity = iAuthorityService.managerLogin(userName);
        MyResult myResult = null;
        Authority authority = null;
        if (entity != null && entity.getPassword().equals(password)) {
            authority = new Authority();
            User e = new User();
            e.setName(entity.getName());
            authority.setRole(entity.getRole());
            authority.setUserMsg(e);
            session.setAttribute("id", entity.getId());
            session.setAttribute("userName", entity.getUserName());
            session.setAttribute("password", entity.getPassword());
            myResult = new MyResult(authority, token, "success");
        } else {
            myResult = new MyResult(authority, "", "false");
        }
        return myResult;
    }

    @PostMapping("/addManageer")
    public String addManager(@RequestBody Manager manager) {
        try {
            iAuthorityService.saveManager(manager);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @GetMapping("/findAllUser")
    public List<AuthorityDTO> findAllUser(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, String userName, String role) {
        return utilsFind(page, limit, userName, role);
    }

    @GetMapping("/findAllManager")
    public List<ManagerDTO> findAllManager(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, String userName, String role) {
        return managerUtilsFind(page, limit, userName, role);
    }

    @DeleteMapping("/deleteAllUser")
    public List<AuthorityDTO> deleteAllUser(@RequestParam(value = "ids[]") Long[] ids, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, String userName, String role) {
        iAuthorityService.deleteUser(ids);
        return utilsFind(page, limit, userName, role);
    }

    @DeleteMapping("/deleteAllManager")
    public List<ManagerDTO> deleteAllManager(@RequestParam(value = "ids[]") Long[] ids, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, String userName, String role) {
        iAuthorityService.deleteManager(ids);
        return managerUtilsFind(page, limit, userName, role);
    }

    @PostMapping("/changeDownload")
    public List<AuthorityDTO> changeDownload(@RequestParam(value = "id") Long id, @RequestParam(value = "download") Boolean download, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, String userName, String role) {
        iAuthorityService.changeDownload(id, download);
        return utilsFind(page, limit, userName, role);
    }

    public List<AuthorityDTO> utilsFind(int page, int limit, String userName, String role) {
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(page);
        pageReq.setLimit(limit);
        pageReq.setSort("id");
        pageReq.setDir("ASC");
        if (userName != null && userName.equals("")) {
            userName = null;
        }
        if (role != null && role.equals("")) {
            role = null;
        }
        return iAuthorityService.findAllUser(userName, role, pageReq.getPageable());
    }

    public List<ManagerDTO> managerUtilsFind(int page, int limit, String userName, String role) {
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(page);
        pageReq.setLimit(limit);
        pageReq.setSort("id");
        pageReq.setDir("ASC");
        if (userName != null && userName.equals("")) {
            userName = null;
        }
        if (role != null && role.equals("")) {
            role = null;
        }
        return iAuthorityService.findAllManager(userName, role, pageReq.getPageable());
    }
}
