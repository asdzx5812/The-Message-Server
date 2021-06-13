package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.Main;
import org.foop.finalproject.theMessageServer.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    public ResponseEntity playGameCard(String roomId, String playerId, int gameCardIdx, String targetId) {
        try {
            Player player = Main.getPlayer(roomId, playerId);
            if (player == null) {
                throw new Exception("Player not found");
            }
            Player target = Main.getPlayer(roomId, targetId);
            player.setActionToPerform(gameCardIdx, target);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    public ResponseEntity passIntelligence(String roomId, String playerId, int gameCardIdx) {
        try {
            Player player = Main.getPlayer(roomId, playerId);
            if (player == null) {
                throw new Exception("Player not found");
            }
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
            Player player = Main.getPlayer(roomId, playerId);
            if (player == null) {
                throw new Exception("Player not found");
            }
            // TODO
//            player.onReceivedIntelligence();

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
