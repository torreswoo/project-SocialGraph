package com.torres.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CheckingFriendDegreeServiceTest {


    @InjectMocks
    private CheckingFriendDegreeService checkingFriendDegreeService;

    @Resource(name = "redisTemplate")
//    @Mock
    private SetOperations<String, String> setOperations;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        assertThat(checkingFriendDegreeService).isNotNull();

        List<List<String>> friends = new ArrayList<List<String>>();
        List<String> friend01 = new ArrayList<String>();friend01.add("Richard");friend01.add("Anthony");
        List<String> friend02 = new ArrayList<String>();friend02.add("Mina");   friend02.add("Marc");
        List<String> friend03 = new ArrayList<String>();friend03.add("Mina");   friend03.add("Zuny");
        friends.add(friend01);
        friends.add(friend02);
        friends.add(friend03);

        friends.stream().forEach(friend -> {

            String userA = friend.get(0);
            String userB = friend.get(1);

            setOperations.add("user:"+userA+":friends", userB);
            setOperations.add("user:"+userB+":friends", userA);
        });
    }


    @Test
    public void test(){

        Set<String> str = setOperations.members("user:Mina:friends");

        str.forEach(s -> System.out.println(s));


        // 02.
        String userA = "Mina";
        String userB = "Torres";
        Set<String> friends = setOperations.members("user:"+userA+":friends");
        Set<String> filter = friends.stream().filter(friend -> friend.equals(userB)).collect(Collectors.toSet());
        System.out.println("-----------"+filter.size());
        boolean result = filter.size() != 0 ? true : false;
        System.out.println("-----------"+result);

        // 03
        Set<String> firstDegreeFriends = setOperations.members("user:"+userA+":friends");
        Set<String> secondDegreeFriends = new HashSet<String>();
        firstDegreeFriends.forEach(friend -> {
            secondDegreeFriends.addAll(setOperations.members("user:"+friend+":friends"));
        });

        boolean resu = secondDegreeFriends.size() != 0 ? true : false;


    }

//    @Test
//    public void test_getFriends(){
//
//        // getFriends
//        String userA = "Mina";
//        Set<String> friends = new HashSet<String>();
//        friends.add("Marc");
//        friends.add("Zuny");
//        when(setOperations.members("user:"+userA+":friends")).thenReturn(friends);
//
//        Set<String> friendsOfUserA = redisService.getFriends(userA);
//        assertThat(friendsOfUserA.size()).isEqualTo(2);
//
//    }


}