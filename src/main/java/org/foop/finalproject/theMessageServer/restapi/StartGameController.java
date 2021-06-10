package org.foop.finalproject.theMessageServer.restapi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.foop.finalproject.theMessageServer.Game;

import javax.annotation.PostConstruct;

// 用來開始執行程式 （用PostConstruct標記的方法會在一開始就執行）
public class StartGameController {
    //@PostConstruct
    public void startGame(){
        Game game = new Game();
        game.start();
    }
}