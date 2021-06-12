package org.foop.finalproject.theMessageServer.restapi;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Player;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/room")
public class RoomController {
    @GetMapping("/")
    public String createRoom(@PathVariable String id) {
        Player player = Game.getPlayerById(id);
        // createRoom(id)
        return ""; // getRandomId()
    }

    @PostMapping("/{room_id}")
    public void joinRoom(@PathVariable String id, @PathVariable String room_id) {
        Player player = Game.getPlayerById(id);
        // joinRoom(id)
    }
}