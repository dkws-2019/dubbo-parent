package com.liuchao.provider.service;

import com.alibaba.dubbo.config.annotation.Service;

import com.liuchao.dubboapi.service.UserLogin;
import com.liuchao.dubboapi.service.UserService;
import com.liuchao.dubbodomain.domain.User;
import com.liuchao.dubbomapper.service.UserMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqbTemplate;

    @Override
    public User findUser() {
        User user=new User();
        user.setUserName("张三");
        user.setPassword("12432432");
        return user;
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
        amqbTemplate.convertAndSend("user.add.exchange","user.insert",user.getId());

    }

    @Override
    public User findOne(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    public static void main(String[] args) {
        User user=new User();
        user.setId(1);
        user.setUserName("aaa");

        User user2=new User();
        user2.setId(2);
        user2.setUserName("bbb");
        List<User> users = Arrays.asList(user, user2);
        Map<Integer, User> collect = users.stream().collect(Collectors.toMap(use -> user.getId(), use -> user));

    }
}
