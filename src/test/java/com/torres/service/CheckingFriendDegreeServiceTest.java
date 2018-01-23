package com.torres.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CheckingFriendDegreeServiceTest {


    @InjectMocks
    private CheckingFriendDegreeService checkingFriendDegreeService;

    @Mock
    private SetOperations<String, String> setOperations;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        assertThat(checkingFriendDegreeService).isNotNull();
        assertThat(setOperations).isNotNull();

        // insert friends
        // 1. Richard - Anthony
        // 2. Zuny - Mina - Mac - Torres
        List<List<String>> friends = new ArrayList<List<String>>();
        List<String> friend01 = new ArrayList<String>();friend01.add("Richard");friend01.add("Anthony");
        List<String> friend02 = new ArrayList<String>();friend02.add("Mina");   friend02.add("Marc");
        List<String> friend03 = new ArrayList<String>();friend03.add("Mina");   friend03.add("Zuny");
        List<String> friend04 = new ArrayList<String>();friend04.add("Marc");   friend04.add("Torres");
        friends.add(friend01);
        friends.add(friend02);
        friends.add(friend03);
        friends.add(friend04);

        friends.stream().forEach(friend -> {

            String userA = friend.get(0);
            String userB = friend.get(1);

            setOperations.add("user:"+userA+":friends", userB);
            setOperations.add("user:"+userB+":friends", userA);
        });
    }

    @Test
    public void test_getFriendsOfFriends(){

        // when

        Set<String> friendsOfMina  = new HashSet<String>();
        friendsOfMina.add("Mac");
        friendsOfMina.add("Zuny");
        Set<String> friendsOfMac  = new HashSet<String>();
        friendsOfMac.add("Mina");
        Set<String> friendsOfZuny  = new HashSet<String>();
        friendsOfZuny.add("Mina");
        when(checkingFriendDegreeService.getFriends("Mina")).thenReturn(friendsOfMina);
        when(checkingFriendDegreeService.getFriends("Mac")).thenReturn(friendsOfMac);
        when(checkingFriendDegreeService.getFriends("Zuny")).thenReturn(friendsOfZuny);


        Set<String> friends  = new HashSet<String>();
        friends.add("Mina");

        // give
        Set<String> friendsOfuser = checkingFriendDegreeService.getFriendsOfFriends(friends);

        // then
        assertThat(friendsOfuser.size()).isEqualTo(2);
        assertThat(friendsOfuser.contains("Mac")).isEqualTo(true);
        assertThat(friendsOfuser.contains("Zuny")).isEqualTo(true);
        assertThat(friendsOfuser.contains("Richard")).isEqualTo(false);
    }

    @Test
    public void test_areFirstDegree(){

        // when
        String userMina = "Mina";
        String userZuny = "Zuny";
        String userTorres = "Torres";

        Set<String> friendsOfMina  = new HashSet<String>();
        friendsOfMina.add("Mac");
        friendsOfMina.add("Zuny");
        Set<String> friendsOfMarc  = new HashSet<String>();
        friendsOfMarc.add("Mina");
        friendsOfMarc.add("Torres");
        Set<String> friendsOfZuny  = new HashSet<String>();
        friendsOfZuny.add("Mina");
        Set<String> friendsOfTorres  = new HashSet<String>();
        friendsOfZuny.add("Marc");

        when(checkingFriendDegreeService.getFriends("Mina")).thenReturn(friendsOfMina);
        when(checkingFriendDegreeService.getFriends("Marc")).thenReturn(friendsOfMarc);
        when(checkingFriendDegreeService.getFriends("Zuny")).thenReturn(friendsOfZuny);
        when(checkingFriendDegreeService.getFriends("Torres")).thenReturn(friendsOfTorres);

        // give
        boolean isFirstDegree01 = checkingFriendDegreeService.areFirstDegree(userMina, userZuny);
        boolean isFirstDegree02 = checkingFriendDegreeService.areFirstDegree(userZuny, userMina);
        boolean isFirstDegree03 = checkingFriendDegreeService.areFirstDegree(userMina, userTorres);

        // then
        assertThat(isFirstDegree01).isEqualTo(true);
        assertThat(isFirstDegree02).isEqualTo(true);
        assertThat(isFirstDegree03).isEqualTo(false);

    }

    @Test
    public void test_areSecondDegree(){

        // when
        String userMina = "Mina";
        String userZuny = "Zuny";
        String userTorres = "Torres";

        Set<String> friendsOfMina  = new HashSet<String>();
        friendsOfMina.add("Mac");
        friendsOfMina.add("Zuny");
        Set<String> friendsOfFriendsOfMina  = new HashSet<String>();
        friendsOfFriendsOfMina.add("Torres");


        when(checkingFriendDegreeService.getFriends(userMina)).thenReturn(friendsOfMina);
        when(checkingFriendDegreeService.getFriendsOfFriends(friendsOfMina)).thenReturn(friendsOfFriendsOfMina);

        // give
        boolean isFirstDegree01 = checkingFriendDegreeService.areSecondDegree(userMina, userZuny);
        boolean isFirstDegree02= checkingFriendDegreeService.areSecondDegree(userMina, userTorres);

        // then
        assertThat(isFirstDegree01).isEqualTo(false);
        assertThat(isFirstDegree02).isEqualTo(true);

    }

    @Test
    public void test_areThirdDegree(){

        // when
        String userMina = "Mina";
        String userZuny = "Zuny";
        String userTorres = "Torres";

        Set<String> friendsOfZuny  = new HashSet<String>();
        friendsOfZuny.add("Mina");
        Set<String> friendsOfFriendsOfZuny  = new HashSet<String>();
        friendsOfFriendsOfZuny.add("Marc");
        Set<String> friendOfFriendsOfFriendsOfZuny  = new HashSet<String>();
        friendOfFriendsOfFriendsOfZuny.add("Torres");

        when(checkingFriendDegreeService.getFriends(userZuny)).thenReturn(friendsOfZuny);
        when(checkingFriendDegreeService.getFriendsOfFriends(friendsOfZuny)).thenReturn(friendsOfFriendsOfZuny);
        when(checkingFriendDegreeService.getFriendsOfFriends(friendsOfFriendsOfZuny)).thenReturn(friendOfFriendsOfFriendsOfZuny);

        // give
        boolean isFirstDegree01 = checkingFriendDegreeService.areThirdDegree(userZuny, userTorres);
        boolean isFirstDegree02= checkingFriendDegreeService.areThirdDegree(userZuny, userMina);

        // then
        assertThat(isFirstDegree01).isEqualTo(true);
        assertThat(isFirstDegree02).isEqualTo(false);

    }


}