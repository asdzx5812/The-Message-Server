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
        //若room不存在
        Room room = Main.getRoom(roomId);
        if(room == null) {
            throw new Exception("當前房間不存在");
        }
         //Game game = room.getGame();
        //messageService.sendPlayerIdToAllUser(game);
        room.startGame();
    }

    public void leaveRoom(String roomId, String userId) {
        Room room = Main.getRoom(roomId);
        User user = Main.getUser(userId);
        room.deleteUser(user);
        user.leaveRoom();
    }
}
