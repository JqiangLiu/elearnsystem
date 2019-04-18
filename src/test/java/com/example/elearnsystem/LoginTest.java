package com.example.elearnsystem;

import com.example.elearnsystem.authority.domain.Authority;
import com.example.elearnsystem.authority.service.IAuthorityService;
import com.example.elearnsystem.common.util.MD5Utils;
import com.example.elearnsystem.user.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class LoginTest {
    @Autowired
    private IAuthorityService iAuthorityService;

    @Test
    public void register() {
         Authority authority = new Authority();
         User user = new User();
         authority.setUserName("123654");
         authority.setPassword("user");
         authority.setUserMsg(user);
         iAuthorityService.saveOne(authority);
    }
    @Test
    public void MD5(){
        String r1 = MD5Utils.MD5Encode("123abc","");
        System.out.println(r1);
    }
    @Test
    public void find(){
        Authority entity = iAuthorityService.login("123654");
    }
}
