package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.*;
import org.foop.finalproject.theMessageServer.action.GameCardAction;
import org.foop.finalproject.theMessageServer.action.IntelligenceAction;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Service
@ServerEndpoint(value = "/websocket")    //宣告這是一個Socket服務
public class MessageService{
    @Autowired
    private JsonService jsonService;
    /**
     * 連線建立成功呼叫的方法
     * @param session  可選的引數
     * @throws Exception
     */
    @OnOpen
    public void onOpen(Session session){
        // Main.openSession(session);
    }
    /**
     * 連線關閉呼叫的方法
     * @throws Exception
     */
    @OnClose
    public void onClose(){
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
    public void onMessage(String message, Session session) throws Exception{
        if (message != null){
            String[] messages = message.split("-",1);
            switch (messages[0]) {
                case "start": //Todo 創建user
                    User user = new User(messages[1], session);
                    Main.addUser(user);
                    // Todo 看之後怎麼接
                    sendMessage(null, "userId:" + user.getId(), session);
                    break;
                default:
                    break;
            }
        }
    }
    private ArrayList<Session> getAllSessionsFromGame(Game game){
        ArrayList<Player> players = game.getPlayers();
        ArrayList<Session> sessions = new ArrayList<>();
        for(Player player:players){
            sessions.add(player.getUser().getSession());
        }
        return sessions;
    }

    /**
     * 傳送訊息方法。
     * @param message
     * @throws IOException
     */

    public void sendMessage(JSONObject jsonObject, String message, Session session) throws Exception {
        if(jsonObject == null){
            jsonObject = new JSONObject();
        }
        jsonObject.put("Message", message);
        session.getBasicRemote().sendObject(jsonObject);   //向客戶端傳送資料
    }

    public void broadcastMessage(JSONObject jsonObject, String message, Game game) throws Exception {
        if(jsonObject == null){
            jsonObject = new JSONObject();
        }
        jsonObject.put("Message", message);
        ArrayList<Session> sessions = getAllSessionsFromGame(game);
        for(Session session:sessions){
            session.getBasicRemote().sendObject(jsonObject);    //向客戶端傳送資料
        }
    }

    public void sendIntelligenceInformationToPlayer(GameCard gameCard, Player player) throws Exception {
        //Todo gameCard.toJsonObject()
        sendMessage(gameCard.toJsonObject(), "", getPlayerSessionFromGame(player));
    }

    private Session getPlayerSessionFromGame(Player player){
        return player.getUser().getSession();
    }

    public void broadcastPlayerInformation(Game game, ArrayList<Player> players) throws Exception {
        broadcastMessage(jsonService.getPlayersInformationObj(players), "", game);
    }

    public void broadcastRoundStartMessage(Game game) throws Exception {
        broadcastMessage(null, "--- " + game.getRound().getName() + " start ---", game);
    }

    public void broadcastTurnStartMessage(Game game, Player currentPlayer) {
    }

    public void informPlayerToSelectAction(Game game, Player currentPlayer) {
    }

    public void broadcastActionBeenPlayedMessage(Game game, Action action) {

    }

    public void broadcastPlayerDie(Game game, Player player) {
    }

    public void broadcastPlayerLoss(Game game, Player player) {
    }

    public void broadCastGameStartMessage(Game game){

    }
    public void broadcastGameOverMessage(Game game) {
    }


}
