package org.foop.finalproject.theMessageServer.restapi;

import org.foop.finalproject.theMessageServer.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room/{roomId}/game/player/{playerId}")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

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
    public ResponseEntity playGameCard(
            @PathVariable String roomId,
            @PathVariable String playerId,
            @PathVariable int idx,
            @RequestParam("target_id") String targetId
    ) {
        playerService.playGameCard(roomId, playerId, idx, targetId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/intelligence/{idx}")
    public ResponseEntity passIntelligence(
            @PathVariable String roomId,
            @PathVariable String playerId,
            @PathVariable int idx
    ) {
        playerService.passIntelligence(roomId, playerId, idx);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/intelligence")
    public ResponseEntity receivePassingIntelligence(
            @PathVariable String roomId,
            @PathVariable String playerId,
            @PathVariable String id
    ) {
        playerService.receivePassingIntelligence(roomId, playerId);
        return ResponseEntity.ok().build();
    }
}
