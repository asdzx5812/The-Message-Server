package org.foop.finalproject.theMessageServer;
import javax.websocket.Session;

import static org.foop.finalproject.theMessageServer.utils.Utility.generateUserId;

public class User {
    private String name;
    private String id;
    private Room currentRoom;
    private Session session;
    public User(String name, Session session) {
        this.name = name;
        this.id = generateUserId();
        this.currentRoom = null;
        this.session = session;
    }

    public String getName() {
        return name;
    }
    public Session getSession(){
        return session;
    }
    public String getId() {
        return id;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void leaveRoom() {
        currentRoom.onUserLeave(this);
        currentRoom = null;
    }
}
