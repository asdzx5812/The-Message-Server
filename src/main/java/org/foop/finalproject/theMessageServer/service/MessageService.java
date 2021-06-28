package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.*;
import org.foop.finalproject.theMessageServer.action.GameCardAction;
import org.foop.finalproject.theMessageServer.action.IntelligenceAction;
import org.foop.finalproject.theMessageServer.action.PassAction;
import org.foop.finalproject.theMessageServer.action.ReceiveAction;
import org.foop.finalproject.theMessageServer.enums.MessageType;
import org.foop.finalproject.theMessageServer.round.CounteractRound;
import org.foop.finalproject.theMessageServer.round.GameCardRound;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.websocket.*;

@Service
public class MessageService {

    private JsonService jsonService = new JsonService();

    private ArrayList<Session> getAllSessionsFromGame(Game game) {
        ArrayList<Player> players = game.getPlayers();
        ArrayList<Session> sessions = new ArrayList<>();
        for (Player player : players) {
            sessions.add(player.getUser().getSession());
        }
        return sessions;
    }

    private ArrayList<Session> getAllSessionsFromRoom(Room room) {
        ArrayList<User> users = room.getUsers();
        ArrayList<Session> sessions = new ArrayList<>();
        for (User user : users) {
            sessions.add(user.getSession());
        }
        return sessions;
    }

    public void sendMessage(JSONObject body, Session session) {
        try {
            session.getBasicRemote().sendText(body.toString());   //向客戶端傳送資料
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e);
        }
    }

    public void broadcastMessage(JSONObject body, Game game) {
        ArrayList<Session> sessions = getAllSessionsFromGame(game);
        for (Session session : sessions) {
            sendMessage(body, session);    //向客戶端傳送資料
        }
    }

    public void broadcastMessage(JSONObject body, Room room) {
        ArrayList<Session> sessions = getAllSessionsFromRoom(room);
        for (Session session : sessions) {
            sendMessage(body, session);    //向客戶端傳送資料
        }
    }

    public JSONObject getBody(JSONObject payload, String message, MessageType type) {
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

    // type: BROADCAST_ROOM_MEMBER_CHANGE
    public void broadcastRoomMemberChange(Room room) {
        ArrayList<User> users = room.getUsers();
        JSONObject payload = new JSONObject();
        payload.put("users", users.stream().map(User::toJsonObject).collect(Collectors.toList()));
        JSONObject body = getBody(payload, "", MessageType.BROADCAST_ROOM_MEMBER_CHANGE);
        broadcastMessage(body, room);
    }

    //type:INFORM_INTELLIGENCE_INFORMATION
    //破譯用 告知單位player情報資訊
    public void sendIntelligenceInformationToPlayer(GameCard gameCard, Player player) {
    //Todo gameCard.toJsonObject()
        JSONObject body = getBody(gameCard.toJsonObject(), "", MessageType.INFORM_INTELLIGENCE_INFORMATION);
        sendMessage(body, getPlayerSessionFromGame(player));
    }

    private Session getPlayerSessionFromGame(Player player) {
        return player.getUser().getSession();
    }




    //TYPE:BROADCAST_ALL_PLAYERS_INFO
    //初始化 抽牌、棄牌才會用
    public void broadcastGameInformation(Game game, ArrayList<Player> players) {
        JSONObject payload = new JSONObject();
        payload.put("players", jsonService.getPlayersInformationObj(players));
        payload.put("gamecardsOnBoard", jsonService.getActionsOnBoardInformationObj(game.getCurrentActionsOnBoard()));
        JSONObject body = getBody(payload, "", MessageType.BROADCAST_ALL_PLAYERS_INFO);
        broadcastMessage(body, game);
    }

    //TYPE:BROADCAST_ROUND_START_MESSAGE
    //廣播每種回合開始的訊息（-----XXXXX回合 開始-----）目前只給MainRound
    public void broadcastRoundStartMessage(Game game) {
        System.out.println("broadcastRoundStartMessage before getbody");
        JSONObject body = getBody(null, "--- " + game.getRound().getName() + " start ---", MessageType.BROADCAST_ROUND_START_MESSAGE);
        System.out.println("broadcastRoundStartMessage after getBody.");
        broadcastMessage(body, game);
    }

    //TYPE:BROADCAST_TURN_START_MESSAGE
    //廣播每個Turn開始的訊息（MainRound:『玩家』負責派情報 or IntelligenceRound: 情報抵達『玩家』面前）
    public void broadcastTurnStartMessage(Game game, Player currentPlayer) {
        JSONObject payload = new JSONObject();
        System.out.println("broadcastTurnStartMessage start");
        payload.put("player", currentPlayer.toJsonObject());
        JSONObject body = getBody(payload, "", MessageType.BROADCAST_TURN_START_MESSAGE);
        broadcastMessage(body, game);
    }

    //TYPE:BROADCAST_PLAYER_ON_INTELLIGENCE_IN_FRONT
    //廣播 IntelligenceRound: 情報抵達『玩家』面前 (從TurnStart抽離出來)
    public void broadcastPlayerOnIntelligenceInFront(Game game, Player currentPlayer) {
        JSONObject payload = new JSONObject();
        System.out.println("broadcastPlayerOnIntelligenceInFront start");
        payload.put("playerId", currentPlayer.getId());
        JSONObject body = getBody(payload, "", MessageType.BROADCAST_PLAYER_ON_INTELLIGENCE_IN_FRONT);
        broadcastMessage(body, game);
    }

    //TYPE:BROADCAST_PLAYER_START_SELECTING_GAMECARD_TO_PLAY
    //TYPE:BROADCAST_PLAYER_START_SELECTING_GAMECARD_TO_DISCARD
    //TYPE:BROADCAST_PLAYER_START_SELECTING_INTELLIGENCE
    //告知Player選要做的行為（打功能牌|Pass|接情報|派情報）（這邊會傳給client可以打的牌）
    //payload: {
    //        playerId,
    //        availableGamecardsId: []
    //    }
    public void broadcastPlayerToSelectAction(Game game, Player currentPlayer, MessageType messageType) {
        System.out.println("輪到"+ currentPlayer.getUser().getName() + "選擇行為" + messageType);
        JSONObject payload = new JSONObject();
        payload.put("playerId", currentPlayer.getId());
        // TODO type記得傳
        if(messageType == MessageType.BROADCAST_PLAYER_START_SELECTING_GAMECARD) {
            payload.put("availableGamecardsId", currentPlayer.getValidCardIndices(game));
            payload.put("isCounteract", game.getRound() instanceof CounteractRound);
        }
        else if(messageType == MessageType.BROADCAST_PLAYER_START_SELECTING_INTELLIGENCE){
            payload.put("availableIntelligencesId", currentPlayer.getValidIntelligence());
        }
        else if(messageType == MessageType.BROADCAST_PLAYER_START_SELECTING_RECEIVE){
            boolean pass = true;
            if(currentPlayer.isLockOn()){
                pass = false;
            }
            payload.put("canPass",pass);
        }
        else{
            System.out.println("no such type in broadcast select action");
        }
        JSONObject body = getBody(payload, "", messageType);
        broadcastMessage(body, game);
    }

    //TYPE:INFORM_PLAYER_START_SELECTING_TARGET
    public void informPlayerStartSelectTarget(Game game, Player currentPlayer) {
        //Todo
        System.out.println("開始通知player選target");
        JSONObject payload = new JSONObject();
        payload.put("playerId", currentPlayer.getId());
        payload.put("availableTargetsId", game.getTargetList(currentPlayer));
        System.out.println(currentPlayer.getUser().getName() + "得到availableTargetsId:" + game.getTargetList(currentPlayer));
        JSONObject body = getBody(payload, "",MessageType.INFORM_PLAYER_START_SELECTING_TARGET);
        sendMessage(body, currentPlayer.getUser().getSession());
    }

    //TYPE:BROADCAST_GAMECARD_PLAYED
    //TYPE:BROADCAST_PLAYER_PASSED
    //TYPE:BROADCAST_INTELLIGENCE_SENT
    //TYPE:BROADCAST_INTELLIGENCE_RECEIVED
    //廣播『玩家』做了什麼行為(XXX選擇PASS, xxx選擇接收)
    public void broadcastActionBeenPlayedMessage(Game game, Action action) {
        System.out.println(action.getPerformer().getUser().getName() + "做了" + action.toJsonObject().toString());
        MessageType messageType;
        if(action instanceof GameCardAction){
            messageType = MessageType.BROADCAST_GAMECARD_PLAYED;
        }
        else if(action instanceof PassAction){
            messageType = MessageType.BROADCAST_PLAYER_PASSED;
        }
        else if(action instanceof IntelligenceAction){
            messageType = MessageType.BROADCAST_INTELLIGENCE_SENT;
        }
        else if(action instanceof ReceiveAction){
            messageType = MessageType.BROADCAST_INTELLIGENCE_RECEIVED;
        }
        else{
            System.out.print("No such type action in broadcastActionBeenPlayedMessage");
            return;
        }
        JSONObject payload = action.toJsonObject();
        JSONObject body = getBody(payload, "", messageType);
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

    //TYPE:BROADCAST_GAME_START
    //廣播遊戲開始的訊息
    public void broadCastGameStartMessage(Game game) {
        System.out.println("broadcastGameStartMessage start!!!!");
        ArrayList<Player> players = game.getPlayers();
        JSONObject payload = new JSONObject();
        payload.put("players", jsonService.getPlayersInformationObj(players));
        JSONObject body = getBody(payload, "--- Game Start ---", MessageType.BROADCAST_GAME_START);
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
    public void informPlayerInformation(Game game, Player player) {
        JSONObject payload = new JSONObject();
        payload.put("playerId", player.getId());
        payload.put("handcardsNumber", player.getHandcardsNum());
        payload.put("player", player.toJsonObject());
        JSONObject body = getBody(payload, "", MessageType.INFORM_PLAYER_INFORMATION);
        sendMessage(body, player.getUser().getSession());
    }

    //TYPE:BROADCAST_PLAYER_CARDNUM_INFORMATION
    //每位玩家抽牌後，廣播這位player目前手牌數
    public void BroadcastPlayerCardNumInformation(Game game, Player player) {
        JSONObject payload = new JSONObject();
        payload.put("playerId", player.getId());
        payload.put("handcardsNumber", player.getHandcardsNum());


    }

    //TYPE:INFORM_PLAYER_ON_PROOF
    //抽玩牌，通知players
    public void informPlayerOnProve(Game game, Player player) {

    }
    //TYPE:INFORM_PLAYER_CARD_INFORMATION
    //抽玩牌，通知player目前手牌
    public void informPlayerCardInformation(Game game, Player player) {

    }
    //TYPE:BROADCAST_ACTION_PERFORMED
    public void broadcastActionPerformed(Game game, String message){
        //TODO

    }

    public void broadcastPlayerChooseOfProof() {
    }
}
