package org.foop.finalproject.theMessageServer.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @RequestMapping("/hello")
    public String firstControllerResponse(){
        return "EG小ㄐㄐ";
    }
}
