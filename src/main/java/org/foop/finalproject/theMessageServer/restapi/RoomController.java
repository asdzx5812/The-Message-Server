package org.foop.finalproject.theMessageServer.restapi;

import org.apache.coyote.Response;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Main;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.User;
import org.foop.finalproject.theMessageServer.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/{userId}/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/")
    public ResponseEntity createRoom(@PathVariable String userId) {
        try {
            String roomId = roomService.createRoom(userId);
            // Todo response maybe need to transform to json
            return ResponseEntity.ok().body(roomId);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/{roomId}")
    public ResponseEntity joinRoom(@PathVariable String userId, @PathVariable String roomId) throws Exception {
        roomService.joinRoom(roomId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity leaveRoom(@PathVariable String userId, @PathVariable String roomId) throws Exception {
        roomService.leaveRoom(userId);
        return ResponseEntity.ok().build();
    }

}