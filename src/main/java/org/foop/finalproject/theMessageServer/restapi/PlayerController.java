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
}
