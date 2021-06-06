package org.foop.finalproject.theMessageServer.restapi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.foop.finalproject.theMessageServer.Game;

@RestController
public class StartGameController {
    @PostMapping("/start")
    public String startGame(){
        Game game = new Game();
        game.start();
        return "";
    }
}