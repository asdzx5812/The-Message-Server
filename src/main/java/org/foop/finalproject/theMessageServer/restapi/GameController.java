package org.foop.finalproject.theMessageServer.restapi;

import org.foop.finalproject.theMessageServer.*;
import org.foop.finalproject.theMessageServer.enums.GameState;
import org.foop.finalproject.theMessageServer.service.GameService;
import org.foop.finalproject.theMessageServer.service.RoomService;
import org.foop.finalproject.theMessageServer.action.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room/{roomId}/game/")
//@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private RoomService roomService;

    @PostMapping("/player/{playerId}/pass")
    public ResponseEntity receivePass(@PathVariable String roomId, @PathVariable String playerId) throws Exception {
        Game game;
        Player player = Main.getPlayer(roomId, playerId);
        try {
            game = Main.getRoom(roomId).getGame();
        } catch (Exception e) {
            throw new Exception("Room not found.");
        }
        Action pass = new PassAction(game, player);
        gameService.onReceiveAction(pass);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/player/{playerId}/game-card/{gameCardIdx}")
    public ResponseEntity receiveGameCard(@PathVariable String roomId,
                                          @PathVariable String playerId,
                                          @PathVariable int gameCardIdx) throws Exception {
        Game game = Main.getRoom(roomId).getGame();
        Player player = Main.getPlayer(roomId, playerId);
        if (player == null) {
            throw new Exception("Performer player not found");
        }

        GameCard gameCard = player.getCardByIndex(gameCardIdx);
        Action action = new GameCardAction(game, player, gameCard, null);
        gameService.onReceiveAction(action);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/player/{playerId}/target/{targetPlayerId}")
    public ResponseEntity receiveTarget(@PathVariable String roomId,
                                          @PathVariable String playerId,
                                          @PathVariable String targetPlayerId) throws Exception {
        Game game = Main.getRoom(roomId).getGame();
        Player player = Main.getPlayer(roomId, playerId);
        if (player == null) {
            throw new Exception("Performer player not found");
        }
        Player targetPlayer;
        if (targetPlayerId.equals(null)){
            targetPlayer = null;
        }
        else{
            targetPlayer = Main.getPlayer(roomId, targetPlayerId);
        }
        Action action = new GameCardAction(game, player, null, targetPlayer);
        gameService.onReceiveAction(action);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/start")
    public ResponseEntity startGame(@PathVariable String roomId) {
        try {
            roomService.startGame(roomId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
