package org.foop.finalproject.theMessageServer;

import org.foop.finalproject.theMessageServer.utils.Utility;

import java.util.ArrayList;

public class Room {
    private String id;
    private ArrayList<User> users;
    private int minPlayerNum = 2;
    private int maxPlayerNum = 9;
    private Game game;
    private boolean isPlaying = false;

    public Room(String id, User creator) {
        this.id = id;
        this.users = new ArrayList<>();
        this.users.add(creator);
    }

    public void joinRoom(User user) throws Exception {
        if (isPlaying) {
            throw new Exception("The room is playing a game.");
        }
        if (users.size() == maxPlayerNum) {
            throw new Exception(String.format("This room (id: %s) is full", id));
        } else {
            users.add(user);
        }
    }

    public void startGame() throws Exception {
        if (isPlaying) {
            return;
        }
        if (users.size() < minPlayerNum) {
            throw new Exception("There are not enough players. (At least 3)");
        } else {
            isPlaying = true;
            game = new Game(users);
            game.start();
            // Thread gameThread = new Thread(game);
            // gameThread.start();
        }
    }

    public String getId() {
        return id;
    }

    public Player getPlayer(String playerId) throws Exception {
        if (!isPlaying) {
            throw new Exception("No playing game.");
        }
        try {
            return Utility.findInArrayList(game.players, player -> player.id.equals(playerId));
        }
        catch (Exception e) {
            throw new Exception("Player not found");
        }
    }

    public Game getGame(){
        return game;
    }
}
