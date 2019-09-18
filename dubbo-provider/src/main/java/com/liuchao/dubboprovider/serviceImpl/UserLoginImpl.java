package com.liuchao.dubboprovider.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.liuchao.dubboapi.service.UserLogin;
import com.liuchao.dubboapi.service.UserService;
import com.liuchao.dubbodomain.domain.User;
import com.liuchao.dubbomapper.service.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass=UserLogin.class)
public class UserLoginImpl implements UserLogin {

    @Reference
    private UserService userService;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Value("${other.name}")
    private String otherName;


    public boolean login(User user) {
        List<User> admin = userMapper.findByUserName("admin");

        User user1 = userService.findUser();
        System.out.println("**************"+otherName);
        System.out.println(user1);

        if(user.getUserName().equals("admin") && "123456".equals(user.getPassword())){
            return true;
        }
        return false;
    }



}
