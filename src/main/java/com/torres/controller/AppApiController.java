package com.torres.controller;

import com.torres.domain.FriendCheckResponse;
import com.torres.service.CheckingFriendDegreeService;
import com.torres.service.MakingFriendRelation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class AppApiController {

    @Autowired
    private CheckingFriendDegreeService checkingFriendDegreeService;

    @Autowired
    private MakingFriendRelation makingFriendRelation;

    // API - checking find friend degree
    @ApiOperation(value = "find friend degree - userA, userB")
    @RequestMapping(
        value="/v1/friendDegree",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success to check Friend degree"),
        @ApiResponse(code = 400, message = "Bad Request - No exist this URL. check the URL again"),
        @ApiResponse(code = 404, message = "Not Found - Not Found. check user name"),
        @ApiResponse(code = 500, message = "Internal Server Error") })
    public @ResponseBody
    FriendCheckResponse checkFriendDegree(
        @RequestParam String userA,
        @RequestParam String userB){

        int degree = 0;
        log.info("[REQ] {}, {}", userA, userB);

        try {
            degree = checkingFriendDegreeService.checkFriendDegree(userA, userB);
        }catch (Exception ex){
            log.error("(SEND EXCEPTION) : {}", ex.getMessage());
        }

        FriendCheckResponse friendCheckResponse = new FriendCheckResponse(userA, userB, degree);
        log.info("[RES] {}, {}, degree: {}", friendCheckResponse.getUserA(), friendCheckResponse.getUserB(), friendCheckResponse.getDegree());

        return friendCheckResponse;
    }

    // API - insert friend relation
    @ApiOperation(value = "insert friend relation - userA, userB")
    @RequestMapping(
        value="/v1/insertFriend",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    FriendCheckResponse insertFriends(
        @RequestParam String userA,
        @RequestParam String userB){

        int degree = 0;
        log.info("[REQ insert] {}, {}", userA, userB);

        makingFriendRelation.insertData(userA, userB);

        FriendCheckResponse friendCheckResponse = new FriendCheckResponse(userA, userB, degree);
        log.info("[RES insert] {}, {}, degree: {}", friendCheckResponse.getUserA(), friendCheckResponse.getUserB(), 1);

        return friendCheckResponse;
    }

}
