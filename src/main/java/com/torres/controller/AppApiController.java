package com.torres.controller;

import com.torres.domain.FraudDetectResponse;
import com.torres.service.RedisService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@Slf4j
public class AppApiController {

    // API test
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/v1/count", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");
        model.addObject("count", redisService.getVisitCount());
        return model;
    }


    //
    @ApiOperation(value = " API using user_id, friend_id")
    @RequestMapping(
        value="/v1/{userId01}/{userId02}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success to check fraud detection"),
        @ApiResponse(code = 400, message = "Bad Request - No exist this URL. check the URL again"),
        @ApiResponse(code = 404, message = "Not Found - Not Found. check user_id"),
        @ApiResponse(code = 500, message = "Internal Server Error") })
    public @ResponseBody
    FraudDetectResponse fraudDetectResponse(
        @PathVariable Long userId01,
        @PathVariable Long userId02){

        // TODO: check user_id is LONG?
        log.info("[REQ] {} - start check Fraud Detection.", userId01);

        try {
            // TEST: check all UserAction
            Date testTime = new Date();
            log.info(" --- time : {}", testTime.getTime());



        }catch (Exception ex){
            log.error("(SEND EXCEPTION during meta) 처리되지않은 오류 발생 : {}", ex.getMessage());
        }
        //


        FraudDetectResponse fraudDetectResponse = new FraudDetectResponse(userId01, Boolean.TRUE, "RuleA,RuleB");

//        log.info("test {} ", creatingUserActionLogService.test(user_id));
        log.info("[RES] {} - fraud result : {}", userId01, fraudDetectResponse.getIs_fraud());
        return fraudDetectResponse;
    }

}
