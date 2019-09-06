package com.liuchao.dubboprovider.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liuchao.dubboapi.entity.User;
import com.liuchao.dubboapi.service.UserLogin;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass=UserLogin.class)
public class UserLoginImpl implements UserLogin {
    @Override
    public boolean login(User user) {
        if(user.getUserName().equals("admin") && "123456".equals(user.getPassword())){
            return true;
        }
        return false;
    }
}
