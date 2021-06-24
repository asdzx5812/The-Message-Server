package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.Main;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Room;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    private MessageService messageService;

    public String createRoom(String userId) throws Exception{
        User user = Main.getUser(userId);
        return Main.createRoom(user);
    }

    public void joinRoom(String roomId, String userId) throws Exception {
        User user = Main.getUser(userId);
        Main.joinRoom(roomId, user);
    }

    public void startGame(String roomId) throws Exception{
        Room room = Main.getRoom(roomId);
        Game game = room.getGame();
        //messageService.sendPlayerIdToAllUser(game);
        room.startGame();
    }

    public void leaveRoom(String userId) throws Exception{
        User user = Main.getUser(userId);
        user.leaveRoom();
    }
}
