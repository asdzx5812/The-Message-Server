package org.foop.finalproject.theMessageServer;

import org.foop.finalproject.theMessageServer.utils.Utility;

import java.util.ArrayList;

public class Main {
    static public ArrayList<Room> rooms = new ArrayList<>();
    static public ArrayList<User> users = new ArrayList<>();

    static public String createRoom(User user) {
        String roomId;
        do {
            roomId = Utility.generateRoomId();
        } while (checkRoomIdExist(roomId));
        rooms.add(new Room(roomId, user));
        return roomId;
    }

    static public void joinRoom(String roomId, User user) throws Exception {
        Room roomToJoin = Utility.findInArrayList(rooms, room -> room.getId().equals(roomId));
        if (roomToJoin == null) {
            throw new Exception("Room not found.");
        }
        roomToJoin.joinRoom(user);
    }

    static public Room getRoom(String roomId) throws Exception {
        try {
            return Utility.findInArrayList(rooms, room -> room.getId().equals(roomId));
        } catch (Exception e) {
            throw new Exception("Room not found.");
        }
    }

    static public User getUser(String userId) throws Exception {
        try {
            return Utility.findInArrayList(users, user -> user.getId().equals(userId));
        } catch (Exception e) {
            throw new Exception("User not found.");
        }
    }

    static public Player getPlayer(String roomId, String playerId) throws Exception {
        Room room;
        try {
            room = Utility.findInArrayList(rooms, r -> r.getId().equals(roomId));
        } catch (Exception e) {
            throw new Exception("Room not found.");
        }

        return room.getPlayer(playerId);
    }

    static private boolean checkRoomIdExist(String roomId) {
        for (Room room : rooms) {
            if (room.getId().equals(roomId))
                return true;
        }
        return false;
    }
}

