package com.liuchao.dubbocustom.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liuchao.dubboapi.entity.User;
import com.liuchao.dubboapi.service.UserLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Reference
    private UserLogin userLogin;

    @RequestMapping("/userLogin")
    public boolean userLogin(@RequestParam("userName")String userName,
                          @RequestParam("password")String password){
        User user=new User();
        user.setPassword(password);
        user.setUserName(userName);
        boolean login = userLogin.login(user);
       return login;

    }
}
