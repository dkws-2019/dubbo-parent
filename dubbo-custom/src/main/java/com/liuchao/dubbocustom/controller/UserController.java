package com.liuchao.dubbocustom.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liuchao.commonutil.filter.MvcConfig;
import com.liuchao.commonutil.properties.JwtProperties;
import com.liuchao.commonutil.util.JwtUtil;
import com.liuchao.commonutil.util.KeyUtil;

import com.liuchao.dubboapi.service.UserLogin;

import com.liuchao.dubboapi.service.UserService;
import com.liuchao.dubbocustom.annotation.MyCache;
import com.liuchao.dubbocustom.util.VerifyCode;
import com.liuchao.dubbodomain.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class UserController {
    @Reference
    private UserLogin userLogin;

    @Reference
    private UserService userService;

    @Autowired(required = false)
    private JwtProperties jwtProperties;

    @RequestMapping("/userLogin")
    public Object userLogin(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("userName")String userName,
                             @RequestParam("password")String password){
        User user=new User();
        user.setPassword(password);
        user.setUserName(userName);
        String verCode="";
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("verCode")){
                verCode = cookie.getValue();
            }
        }

        // JwtUtil.generateToken()

        boolean login = userLogin.login(user);
        if(login){//生成token
            Map<String,Object> map=new HashMap<String ,Object>();
            map.put("user",user);
            PrivateKey privateKey = KeyUtil.getPrivateKey(jwtProperties.getKeytoolsPath(), jwtProperties.getKeytoolsAlias(), jwtProperties.getKeytoolsPassword());
            String token = JwtUtil.generateToken("subject", jwtProperties.getExpirationSeconds(), map, privateKey);
            Cookie cookie=new Cookie("token",token);
            cookie.setMaxAge(1000*60);
            response.addCookie(cookie);
            return token;


        }
       return 1;

    }

    @RequestMapping("/getSession")
    public User getSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        System.out.println(user);
        return user;

    }
    @RequestMapping("/getUser")
    public User getUser(){
        User user=new User();
        user.setPassword(12345+"");
        user.setUserName("张三");
        return user;

    }

    @RequestMapping("/findAllUser")
    @MyCache
    public List<com.liuchao.dubbodomain.domain.User> findAllUser(){
        List<com.liuchao.dubbodomain.domain.User> allUser = userService.findAllUser();
        User user = MvcConfig.getUser();
        return allUser;
    }

    @RequestMapping("/addUser")
    public String addUser(User user){
        userService.addUser(user);
        return "success";
    }

    @RequestMapping("/scyzm")
    public void scyzm(HttpServletResponse response) throws IOException {
        VerifyCode code = new VerifyCode();
        BufferedImage image = code.createImage();
        String text = code.getText();
        Cookie cookie=new Cookie("verCode",text);
        cookie.setMaxAge(1000*600);
        response.addCookie(cookie);
        ImageIO.write(image,"jpg",response.getOutputStream());

    }
}