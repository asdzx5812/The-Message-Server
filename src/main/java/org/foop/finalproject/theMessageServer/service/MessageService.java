package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.*;
import org.foop.finalproject.theMessageServer.enums.MessageType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.*;

@Service
public class MessageService{
    @Autowired
    private JsonService jsonService;

    private ArrayList<Session> getAllSessionsFromGame(Game game){
        ArrayList<Player> players = game.getPlayers();
        ArrayList<Session> sessions = new ArrayList<>();
        for(Player player:players){
            sessions.add(player.getUser().getSession());
        }
        return sessions;
    }

    public void sendMessage(JSONObject body, Session session) {
        try {
            session.getBasicRemote().sendText(body.toString());   //向客戶端傳送資料
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception: "+e);
        }
    }

    public void broadcastMessage(JSONObject body, Game game) {
        ArrayList<Session> sessions = getAllSessionsFromGame(game);
        for(Session session:sessions){
            sendMessage(body, session);    //向客戶端傳送資料
        }
    }

    public JSONObject getBody(JSONObject payload, String message, MessageType type){
        JSONObject body = new JSONObject();
        body.put("payload", payload);
        body.put("message", message);
        body.put("type", type.type);
        return body;
    }

    // type:INFORM_USER_ID
    public void informUserId(User user) {
        JSONObject payload = new JSONObject();
        payload.put("userId", user.getId());
        JSONObject body = getBody(payload, "", MessageType.INFORM_USER_ID);
        sendMessage(body, user.getSession());
    }

    //type:INFORM_INTELLIGENCE_INFORMATION
    //破譯用 告知單位player情報資訊
    public void sendIntelligenceInformationToPlayer(GameCard gameCard, Player player) {
        //Todo gameCard.toJsonObject()
        JSONObject body = getBody(gameCard.toJsonObject(), "", MessageType.INFORM_INTELLIGENCE_INFORMATION);
        sendMessage(body , getPlayerSessionFromGame(player));
    }
    private Session getPlayerSessionFromGame(Player player){
        return player.getUser().getSession();
    }

    //TYPE:BROADCAST_PLAYER_INFORMATION
    //初始化Game時，廣播所有人的手牌數量...等簡化的資訊給所有人
    public void broadcastPlayerInformation(Game game, ArrayList<Player> players) {
        JSONObject payload = new JSONObject();
        payload.put("players", jsonService.getPlayersInformationObj(players));
        JSONObject body = getBody(payload, "", MessageType.BROADCAST_PLAYER_INFORMATION);
        broadcastMessage(body, game);
    }

    //TYPE:BROADCAST_ROUND_START_MESSAGE
    //廣播每種回合開始的訊息（-----XXXXX回合 開始-----）
    public void broadcastRoundStartMessage(Game game) {
        JSONObject body = getBody(null, "--- " + game.getRound().getName() + " start ---", MessageType.BROADCAST_ROUND_START_MESSAGE);
        broadcastMessage(body, game);
    }
    //TYPE:BROADCAST_TURN_START_MESSAGE
    //廣播每個Turn開始的訊息（MainRound:『玩家』負責派情報 or IntelligenceRound: 情報抵達『玩家』面前）
    public void broadcastTurnStartMessage(Game game, Player currentPlayer) {
        JSONObject payload = new JSONObject();
        payload.put("player", currentPlayer.getId());
        JSONObject body = getBody(payload, "", MessageType.BROADCAST_TURN_START_MESSAGE);
        broadcastMessage(body, game);
    }
    //TYPE:INFORM_PLAYER_SELECT_ACTION_MESSAGE
    //告知Player選要做的行為（打功能牌|Pass|接情報|派情報）（這邊會傳給client可以打的牌）
    public void informPlayerToSelectAction(Game game, Player currentPlayer) {
        JSONObject payload = new JSONObject();
        // get can play cards
        JSONObject body = getBody(payload, "", MessageType.INFORM_PLAYER_SELECT_ACTION_MESSAGE);
        broadcastMessage(body, game);
    }
    //TYPE:BROADCAST_ACTION_BEEN_PLAYED_MESSAGE
    //廣播『玩家』做了什麼行為(XXX選擇PASS, xxx選擇接收)
    public void broadcastActionBeenPlayedMessage(Game game, Action action) {
        JSONObject payload = action.toJsonObject();
        JSONObject body = getBody(payload, "", MessageType.BROADCAST_ACTION_BEEN_PLAYED_MESSAGE);
        broadcastMessage(body, game);
    }
    //TYPE:BROADCAST_PLAYER_STATE_CHANGE_MESSAGE
    //廣播誰死去

    public void broadcastPlayerStateChangeMessage(Game game, Player player) {
        JSONObject payload = new JSONObject();
        payload.put("status", player.getStatus().status);
        JSONObject body = getBody(payload, "", MessageType.BROADCAST_PLAYER_STATUS_CHANGE_MESSAGE);
        broadcastMessage(body, game);
    }
    //TYPE:BROADCAST_GAME_START_MESSAGE
    //廣播遊戲開始的訊息
    public void broadCastGameStartMessage(Game game) {
        JSONObject body = getBody(null, "--- Game Start ---", MessageType.BROADCAST_PLAYER_STATUS_CHANGE_MESSAGE);
        broadcastMessage(body, game);
    }
    //TYPE:BROADCAST_GAME_OVER_MESSAGE
    //廣播遊戲結束
    public void broadcastGameOverMessage(Game game) {
        JSONObject body = getBody(null, "--- Game Start ---", MessageType.BROADCAST_PLAYER_STATUS_CHANGE_MESSAGE);
        broadcastMessage(body, game);
    }

    //TYPE:INFORM_PLAYER_INFORMATION
    //一開始初始化後，告知玩家『詳細』資訊
    public void informPlayerInformation(Game game, Player player){
        JSONObject payload = new JSONObject();
        System.out.println("send player information! 1");
        payload.put("playerId", player.getId());
        System.out.println("send player information! 2");
        payload.put("handcardsNumber", player.getHandcardsNum());
        System.out.println("send player information! 3");
        payload.put("player", player.toJsonObject());
        System.out.println("send player information! 4");
        JSONObject body = getBody(payload, "", MessageType.INFORM_PLAYER_INFORMATION);
        System.out.println("send player information! 5");
        sendMessage(body, player.getUser().getSession());
    }
    //TYPE:BROADCAST_PLAYER_CARDNUM_INFORMATION
    //每位玩家抽牌後，廣播這位player目前手牌數
    public void BroadcastPlayerCardNumInformation(Game game, Player player) {
        JSONObject payload = new JSONObject();
        payload.put("playerId", player.getId());
        payload.put("handcardsNumber", player.getHandcardsNum());


    }
    //TYPE:INFORM_PLAYER_CARD_INFORMATION
    //抽玩牌，通知player目前手牌
    public void informPlayerCardInformation(Game game, Player player) {

    }
}
