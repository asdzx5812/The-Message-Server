package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.Main;
import org.foop.finalproject.theMessageServer.User;
import org.foop.finalproject.theMessageServer.Player;
import java.io.IOException;
import java.util.Date;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket")    //宣告這是一個Socket服務
public class MessageService{
    /**
     * 連線建立成功呼叫的方法
     * @param session  可選的引數
     * @throws Exception
     */
    @OnOpen
    public void onOpen(Session session) throws Exception {
        // Main.openSession(session);
    }
    /**
     * 連線關閉呼叫的方法
     * @throws Exception
     */
    @OnClose
    public void onClose() throws Exception {
        // Main.closeSessions();
    }
    /**
     * 發生錯誤時呼叫
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 收到訊息後呼叫的方法
     * @param message 客戶端傳送過來的訊息
     * @param session 可選的引數
     * @throws Exception
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        if (message != null){
            String[] messages = message.split("-",1);
            switch (messages[0]) {
                case "start": //Todo 創建user
                    User user = new User(messages[1],session);
                    Main.addUser(user);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 傳送訊息方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);   //向客戶端傳送資料
    }

    public void sendIntelligenceInformationToPlayer(Game game, Player targetPlayer){
        ArrayList<Session> sessions = Main.getSessions(game)
        //Todo
    }
}
