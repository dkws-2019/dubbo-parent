package com.liuchao.dubboprovider.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.liuchao.dubboapi.service.UserService;
import com.liuchao.dubbodomain.domain.User;
import com.sun.xml.internal.ws.api.model.ExceptionType;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class UserListener {

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @Reference(check = false)
    private UserService userService;

    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(name="user.add.queue",durable = "true"),
            exchange = @Exchange(name="user.add.exchange",type= ExchangeTypes.TOPIC,
                    ignoreDeclarationExceptions="true"),
            key = {"user.insert"}
    ))
    public void listenUserInsert(Integer id){
        User one = userService.findOne(id);
        ParserConfig.getGlobalInstance().addAccept("com.xxxx.blog");
       redisTemplate.opsForSet().add("users",one);
        Set<User> set = redisTemplate.opsForSet().members("users");
        if(set!=null){

            for(Iterator<User> iterator =set.iterator();iterator.hasNext();){
                User next = iterator.next();
                if(next!=null){
                    System.out.println(next.getId()+"  "+next.getUserName()+" "+next.getPassword());

                }
            }
        }


    }
}
