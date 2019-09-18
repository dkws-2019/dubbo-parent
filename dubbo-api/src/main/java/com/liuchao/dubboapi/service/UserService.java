package com.liuchao.dubboapi.service;



import com.liuchao.dubbodomain.domain.User;

import java.util.List;

public interface UserService {

    public  User findUser();

   public List<User> findAllUser();

   public void addUser(User user);

   public User findOne(Integer id);

}
