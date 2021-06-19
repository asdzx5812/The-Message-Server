package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    public ResponseEntity playGameCard(String roomId, String playerId, int gameCardIdx, String targetId) {
        try {
            Game game = Main.getRoom(roomId).getGame();
            Player player = Main.getPlayer(roomId, playerId);
            if (player == null) {
                throw new Exception("Player not found");
            }
            Player target = Main.getPlayer(roomId, targetId);
            Action action = player.setActionToPerform(gameCardIdx, target);
            game.takeAction(action);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    public ResponseEntity passIntelligence(String roomId, String playerId, int gameCardIdx) {
        try {
            Game game = Main.getRoom(roomId).getGame();
            Player player = Main.getPlayer(roomId, playerId);
            if (player == null) {
                throw new Exception("Player not found");
            }
            GameCard intelligenceCard = player.getIntelligence(gameCardIdx);
            game.passIntelligence(player, intelligenceCard);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
// Todo
//        GameCard intelligence = player.getIntelligenceByIdx()
//        Game.passIntelligence(player, intelligence);
    }

    public ResponseEntity receivePassingIntelligence(String roomId, String playerId) {
        try {
            Game game = Main.getRoom(roomId).getGame();
            Player player = Main.getPlayer(roomId, playerId);
            if (player == null) {
                throw new Exception("Player not found");
            }
            game.onReceiveIntelligence(player);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
