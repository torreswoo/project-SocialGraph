package com.torres.service;


import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Service
public class CheckingFriendDegreeService {

    @Resource(name = "redisTemplate") private RedisOperations redisOperations;
    @Resource(name = "redisTemplate") private ValueOperations<String, String> valusOps;
    @Resource(name = "redisTemplate") private SetOperations<String, String> setOperations;

    // 1. Find all first degree connections of UserA (my direct friends).
    public Set<String> getFriends(String user){

        Set<String> friends = setOperations.members("user:"+user+":friends");
        return friends;
    }

    public Set<String> getFriendsOfFriends(Set<String> firstDegreeFriends) {
        Set<String> secondDegreeFriends = new HashSet<String>();
        firstDegreeFriends.forEach(friend -> {
            secondDegreeFriends.addAll(getFriends(friend));
        });
        return secondDegreeFriends;
    }

    // 2. Find if UserA and UserB are 1st degree connected (direct friends).
    public boolean areFirstDegree(String userA, String userB){

        Set<String> friends = getFriends(userA);

        return checkUserInFriendlist(userB, friends);
    }

    // 3. Find if UserA and UserB are 2nd degree connected (friends of friends).
    public boolean areSecondDegree(String userA, String userB){

        Set<String> firstDegreeFriends = getFriends(userA);
        Set<String> secondDegreeFriends = getFriendsOfFriends(firstDegreeFriends);

        return checkUserInFriendlist(userB, secondDegreeFriends);
    }


    // 4. Find if UserA and UserB are 3rd degree connected (friends of friends of friends).
    public boolean areThirdDegree(String userA, String userB){

        Set<String> firstDegreeFriends = getFriends(userA);
        Set<String> secondDegreeFriends = getFriendsOfFriends(firstDegreeFriends);
        Set<String> thirdDegreeFriends = getFriendsOfFriends(secondDegreeFriends);

        return checkUserInFriendlist(userB, thirdDegreeFriends);
    }


    //
    private boolean checkUserInFriendlist(String user, Set<String> friends) {
        return friends.contains(user) ? true : false;
    }


    // for API
    public int checkFriendDegree(String userA, String userB){
        if (areFirstDegree(userA, userB) == true)
            return 1;

        if (areSecondDegree(userA, userB) == true)
            return 2;

        if (areThirdDegree(userA, userB) == true)
            return 3;

        return 0;
    }


}