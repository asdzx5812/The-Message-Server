package org.foop.finalproject.theMessageServer.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @RequestMapping("/hello/{id}/ex/{game}")
    public String firstControllerResponse(@PathVariable String id, @PathVariable String game){
        return "擇哥小ㄐㄐ" + id + "/" + game;
    }
}
