package com.torres.service;

import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MakingFriendRelation {

    @Resource(name = "redisTemplate") private SetOperations<String, String> setOperations;

    public void insertData(String userA, String userB){
        setOperations.add("user:"+userA+":friends", userB);
        setOperations.add("user:"+userB+":friends", userA);
    }
}
