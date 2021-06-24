package org.foop.finalproject.theMessageServer.restapi;

import org.foop.finalproject.theMessageServer.*;
import org.foop.finalproject.theMessageServer.enums.GameState;
import org.foop.finalproject.theMessageServer.service.GameService;
import org.foop.finalproject.theMessageServer.service.RoomService;
import org.foop.finalproject.theMessageServer.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room/{roomId}/game/")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private RoomService roomService;
    public ResponseEntity informAllPlayersStartRound(Game game) {
        game.setState(GameState.valueOf("beforePassingIntelligence"));
        // Todo 廣播

        return ResponseEntity.ok().build();
    }

    @PostMapping("/pass")
    public ResponseEntity receivePass(@PathVariable String roomId) throws Exception {
        Game game;
        try {
            game = Main.getRoom(roomId).getGame();
        } catch (Exception e) {
            throw new Exception("Room not found.");
        }
        gameService.onReceivePass(game);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/player/{playerId}/game-card/{gameCardIdx}")
    public ResponseEntity receiveGameCard(@PathVariable String roomId,
                                          @PathVariable String playerId,
                                          @PathVariable int gameCardIdx,
                                          @RequestParam("targetPlayerId") String targetPlayerId) throws Exception {
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
        GameCard gameCard = player.getCardByIndex(gameCardIdx);
        Action action = new Action(player, gameCard, targetPlayer, game);

        gameService.onReceiveGameCard(game, action);
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
