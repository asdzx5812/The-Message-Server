package org.foop.finalproject.theMessageServer;

public class User {
    private String name;
    private String id;
    private Room currentRoom;

    public User(String name) {
        this.name = name;
        this.id = "test-id";
        this.currentRoom = null;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
