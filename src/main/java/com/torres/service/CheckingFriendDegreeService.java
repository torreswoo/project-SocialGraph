package com.torres.service;


import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CheckingFriendDegreeService {

    @Resource(name = "redisTemplate")
    private RedisOperations redisOperations;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valusOps;

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOperations;

    public int checkFriendDegree(String userA, String userB){
        if (areFirstDegree(userA, userB) == true)
            return 1;

        if (areSecondDegree(userA, userB) == true)
            return 2;

        if (areThirdDegree(userA, userB) == true)
            return 3;

        return 0;
    }
    // 1.
    public Set<String> getFriends(String user){

        Set<String> friends = setOperations.members("user:"+user+":friends");
        return friends;
    }

    // 2.
    public boolean areFirstDegree(String userA, String userB){

        Set<String> friends = setOperations.members("user:"+userA+":friends");

        return checkUserInFriendlist(userB, friends);
    }

    // 3.
    public boolean areSecondDegree(String userA, String userB){

        Set<String> firstDegreeFriends = setOperations.members("user:"+userA+":friends");
        Set<String> secondDegreeFriends = findNextDegreeFriends(firstDegreeFriends);

        return checkUserInFriendlist(userB, secondDegreeFriends);
    }


    // 4.
    public boolean areThirdDegree(String userA, String userB){

        Set<String> firstDegreeFriends = setOperations.members("user:"+userA+":friends");
        Set<String> secondDegreeFriends = findNextDegreeFriends(firstDegreeFriends);
        Set<String> thirdDegreeFriends = findNextDegreeFriends(secondDegreeFriends);

        return checkUserInFriendlist(userB, thirdDegreeFriends);
    }


    //
    private boolean checkUserInFriendlist(String userB, Set<String> friends) {
        Set<String> filter = friends
            .stream()
            .filter(friend -> friend.equals(userB))
            .collect(Collectors.toSet());
        return filter.size() != 0 ? true : false;
    }

    private Set<String> findNextDegreeFriends(Set<String> firstDegreeFriends) {
        Set<String> secondDegreeFriends = new HashSet<String>();
        firstDegreeFriends.forEach(friend -> {
            secondDegreeFriends.addAll(setOperations.members("user:"+friend+":friends"));
        });
        return secondDegreeFriends;
    }




}