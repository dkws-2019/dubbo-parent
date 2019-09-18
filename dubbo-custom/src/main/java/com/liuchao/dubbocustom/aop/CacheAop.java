package com.liuchao.dubbocustom.aop;

import com.liuchao.dubbocustom.annotation.MyCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
public class CacheAop {
    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut(value = "@annotation(com.liuchao.dubbocustom.annotation.MyCache)")
    public void annotationPointCut() {
    }

    @Around("annotationPointCut()")
    public Object before(ProceedingJoinPoint pdj) throws Throwable {
        Class<?> classTarget = pdj.getTarget().getClass();
        Class<?>[] pts = ((MethodSignature)pdj.getSignature()).getParameterTypes();
        Method objMethod = classTarget.getDeclaredMethod(pdj.getSignature().getName(),pts);
        Class<?> returnType = objMethod.getReturnType();
        MyCache myCache = objMethod.getAnnotation(MyCache.class);

        if(myCache!=null){
            String key=classTarget.getName()+"."+objMethod.getName();
            Object o = redisTemplate.opsForValue().get(key);
            if(o!=null){
                return o;
            }else{
                Object proceed = pdj.proceed();
                redisTemplate.opsForValue().set(classTarget.getName()+"."+objMethod.getName(),proceed);
                return proceed;
            }

        }else{
            return pdj.proceed();
        }
        // Object[] args = joinPoint.getArgs();
        //String name = joinPoint.getSignature().getName();


    }
}
