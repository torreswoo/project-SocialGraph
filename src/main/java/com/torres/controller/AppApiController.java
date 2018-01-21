package com.torres.controller;

import com.torres.domain.FriendCheckResponse;
import com.torres.service.CheckingFriendDegreeService;
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

    // API - checking find friend degree
    @ApiOperation(value = "find friend degree - userA, userB")
    @RequestMapping(
        value="/v1/{userA}/{userB}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success to check fraud detection"),
        @ApiResponse(code = 400, message = "Bad Request - No exist this URL. check the URL again"),
        @ApiResponse(code = 404, message = "Not Found - Not Found. check user_id"),
        @ApiResponse(code = 500, message = "Internal Server Error") })
    public @ResponseBody
    FriendCheckResponse checkFriendDegree(
        @PathVariable String userA,
        @PathVariable String userB){

        int degree = 0;
        log.info("[REQ] {}, {}", userA, userB);

        try {
            degree = this.checkingFriendDegreeService.checkFriendDegree(userA, userB);
        }catch (Exception ex){
            log.error("(SEND EXCEPTION) 처리되지않은 오류 발생 : {}", ex.getMessage());
        }

        FriendCheckResponse friendCheckResponse = new FriendCheckResponse(userA, userB, degree);
        log.info("[RES] {}, {}, degree: {}",
            friendCheckResponse.getUserA(),
            friendCheckResponse.getUserB(),
            friendCheckResponse.getDegree());

        return friendCheckResponse;
    }

}
