package com.torres;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@Slf4j
public class AppController {

    // Config - Swagger
    @ApiOperation(value = "Swagger UI main", hidden= true)
    @RequestMapping(value="/", method = RequestMethod.GET)
    public RedirectView swaggerRedirect(){
        return new RedirectView("/swagger-ui.html");
    }
//    // intro
//    // (Thymeleaf)
//    @RequestMapping(value="/intro", method = RequestMethod.GET)
//    public String intro(Model model){
//
//        model.addAttribute("title", "torres");
//        return "intro";
//    }

}
