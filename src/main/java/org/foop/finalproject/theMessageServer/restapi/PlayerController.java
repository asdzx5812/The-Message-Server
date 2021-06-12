package org.foop.finalproject.theMessageServer.restapi;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Player;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room/{room_id}/player/{player_id}")
public class PlayerController {
//    @PostMapping("/character-card")
//    public CharacterCard setCharacterCard(@PathVariable String id) {
//        Player player = Game.getPlayerById(id);
//        if (player == null) {
//            throw new Error("Player not found.");
//        }
//        player.setCharacter();
//        return
//    }
    @PostMapping("/game-card/{idx}")
    public void playGameCard(@PathVariable String room_id, @PathVariable String player_id, @PathVariable int idx) {
        Player player = Game.getPlayerById(player_id);
        if (player == null) {
            throw new Error("Player not found.");
        }
        player.removeHandCardByIndex(idx);
    }

    @PostMapping("/intelligence/{idx}")
    public void sendIntelligence(@PathVariable String room_id, @PathVariable String player_id, @PathVariable int idx) {
        Player player = Game.getPlayerById(player_id);
        if (player == null) {
            throw new Error("Player not found");
        }
//        GameCard intelligence = player.getIntelligenceByIdx()
//        Game.passIntelligence(player, intelligence);
    }

    @PatchMapping("/intelligence")
    public void receiveIntelligence(@PathVariable String id) {
        Player player = Game.getPlayerById(id);
        if (player == null) {
            throw new Error("Player not found");
        }
        player.onReceivedIntelligence(Game.getPassingIntelligence());
    }
}
