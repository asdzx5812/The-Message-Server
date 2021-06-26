package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.*;
import org.foop.finalproject.theMessageServer.action.GameCardAction;
import org.foop.finalproject.theMessageServer.action.IntelligenceAction;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/websocket")    //宣告這是一個Socket服務 ws://localhost:8080/websocket
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
        System.out.println(message);
        if (message != null){
            String[] messages = message.split("-",2);
            switch (messages[0]) {
                case "start": //Todo 創建user start-{username}
                    User user = new User(messages[1], session);
                    Main.addUser(user);
                    // Todo 看之後怎麼接
                    sendMessage(null, "userId:" + user.getId(), "USER_ID", session);
                    System.out.println("receive start and sent userId!!");
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

    public void sendMessage(JSONObject jsonObject, String message, String type,Session session) throws Exception {
        if(jsonObject == null){
            jsonObject = new JSONObject();
        }
        jsonObject.put("message", message);
        jsonObject.put("type",type);
        session.getBasicRemote().sendText(jsonObject.toString());   //向客戶端傳送資料
    }

    public void broadcastMessage(JSONObject jsonObject, String message, String type, Game game) throws Exception {
        if(jsonObject == null){
            jsonObject = new JSONObject();
        }
        jsonObject.put("message", message);
        jsonObject.put("type", type);
        ArrayList<Session> sessions = getAllSessionsFromGame(game);
        for(Session session:sessions){
            session.getBasicRemote().sendText(jsonObject.toString());    //向客戶端傳送資料
        }
    }

    //type:INFORM_INTELLIGENCE_INFORMATION
    //破譯用 告知單位player情報資訊
    public void sendIntelligenceInformationToPlayer(GameCard gameCard, Player player) throws Exception {
        //Todo gameCard.toJsonObject()
        sendMessage(gameCard.toJsonObject(), "", "INTELLIGENCE_INFORMATION", getPlayerSessionFromGame(player));
    }
    private Session getPlayerSessionFromGame(Player player){
        return player.getUser().getSession();
    }

    //TYPE:BROADCAST_PLAYER_INFORMATION
    //初始化Game時，廣播所有人的手牌數量...等簡化的資訊給所有人
    public void broadcastPlayerInformation(Game game, ArrayList<Player> players) throws Exception {
        broadcastMessage(jsonService.getPlayersInformationObj(players), "", "PLAYER_INFORMATION",game);
    }

    //TYPE:BROADCAST_ROUND_START_MESSAGE
    //廣播每種回合開始的訊息（-----XXXXX回合 開始-----）
    public void broadcastRoundStartMessage(Game game) throws Exception {
        broadcastMessage(null, "--- " + game.getRound().getName() + " start ---", "ROUND_START_MESSAGE", game);
    }
    //TYPE:BROADCAST_TURN_START_MESSAGE
    //廣播每個Turn開始的訊息（MainRound:『玩家』負責派情報 or IntelligenceRound: 情報抵達『玩家』面前）
    public void broadcastTurnStartMessage(Game game, Player currentPlayer) {
    }
    //TYPE:INFORM_PLAYER_SELECT_ACTION_MESSAGE
    //告知Player選要做的行為（打功能牌|Pass|接情報|派情報）（這邊會傳給client可以打的牌）
    public void informPlayerToSelectAction(Game game, Player currentPlayer) {
    }
    //TYPE:BROADCAST_ACTION_BEEN_PLAYED_MESSAGE
    //廣播『玩家』做了什麼行為(XXX選擇PASS, xxx選擇接收)
    public void broadcastActionBeenPlayedMessage(Game game, Action action) {

    }
    //TYPE:BROADCAST_PLAYER_DIE_MESSAGE
    //廣播誰死去
    public void broadcastPlayerDie(Game game, Player player) {

    }
    //TYPE:BROADCAST_PLAYER_LOSE_MESSAGE
    //廣播誰輸了
    public void broadcastPlayerLose(Game game, Player player) {
    }
    //TYPE:BROADCAST_GAME_START_MESSAGE
    //廣播遊戲開始的訊息
    public void broadCastGameStartMessage(Game game){

    }
    //TYPE:BROADCAST_GAME_OVER_MESSAGE
    //廣播遊戲結束
    public void broadcastGameOverMessage(Game game) {
    }

    //TYPE:INFORM_PLAYER_INFORMATION
    //一開始初始化後，告知玩家『詳細』資訊
    public void informPlayerInformation(Game game, Player player) {
    }
    //TYPE:BROADCAST_PLAYER_CARDNUM_INFORMATION
    //每位玩家抽牌後，廣播這位player目前手牌數
    public void BroadcastPlayerCardNumInformation(Game game, Player player) {
    }
    //TYPE:INFORM_PLAYER_CARD_INFORMATION
    //抽玩牌，通知player目前手牌
    public void informPlayerCardInformation(Game game, Player player) {

    }
}
