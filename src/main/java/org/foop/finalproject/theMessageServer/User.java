package org.foop.finalproject.theMessageServer;
import javax.websocket.Session;

public class User {
    private String name;
    private String id;
    private Room currentRoom;
    private Session session;
    public User(String name, Session session) {
        this.name = name;
        this.id = "test-id";
        this.currentRoom = null;
        this.session = session;
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
