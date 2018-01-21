package com.torres.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendCheckResponse {

    String userA;
    String userB;
    int degree;
}
