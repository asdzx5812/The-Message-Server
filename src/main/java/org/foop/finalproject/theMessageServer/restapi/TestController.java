package org.foop.finalproject.theMessageServer.restapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("t")
public class TestController {

    @RequestMapping("test")
    public String test() {
        return "Hello World";
    }
}