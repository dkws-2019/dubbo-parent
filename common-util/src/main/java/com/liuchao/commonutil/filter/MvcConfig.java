package com.liuchao.commonutil.filter;

import com.alibaba.fastjson.JSON;
import com.liuchao.commonutil.properties.JwtProperties;
import com.liuchao.commonutil.util.JwtUtil;
import com.liuchao.commonutil.util.KeyUtil;

import com.liuchao.dubbodomain.domain.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.security.PublicKey;
import java.util.List;

@Component
@EnableConfigurationProperties({JwtProperties.class})
public class MvcConfig implements WebMvcConfigurer {
    @Autowired(required = false)
    private JwtProperties jwtProperties;
    private static ThreadLocal<User> userThreadLocal=new ThreadLocal<User>();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String requestURI = request.getRequestURI();
                List<String> allows = jwtProperties.getAllows();
                for(String allow:allows){
                    if(allow.equals(requestURI)){
                        return true;
                    }
                }
                try {
                    String token="";
                    //String token = request.getHeader("token");
                    Cookie[] cookies = request.getCookies();
                    if(null==cookies){
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.getOutputStream().write("toke为空".getBytes());
                        return false;
                    }
                    for(Cookie cookie:cookies){
                        if("token".equals(cookie.getName())){
                            token=cookie.getValue();

                        }
                    }
                    if(token==null ||"".equals(token)){
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.getOutputStream().write("toke为空".getBytes());
                        return false;
                    }
                    PublicKey publicKey = KeyUtil.getPublicKey(jwtProperties.getKeytoolsPath(), jwtProperties.getKeytoolsAlias(), jwtProperties.getKeytoolsPassword());

                    Claims claims = JwtUtil.parseToken(token, publicKey);
                    if( claims==null){
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.getOutputStream().write("toke无效".getBytes());
                        return false;
                    }
                    User user = JSON.parseObject(JSON.toJSONString(claims.get("user")), User.class);
                    userThreadLocal.set(user);
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }


            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                userThreadLocal.remove();
            }
        });
    }

        public static User getUser(){
            return userThreadLocal.get();
        }
}
