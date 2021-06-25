package org.foop.finalproject.theMessageServer.service;

import org.foop.finalproject.theMessageServer.Player;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class JsonService {

    public JSONObject getPlayersInformationObj(ArrayList<Player> players) {
        JSONObject playersInformationObj = new JSONObject();
        ArrayList<JSONObject> playersObj = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            JSONObject playerObj = players.get(i).toJsonObject();
            playersObj.add(playerObj);
        }
        playersInformationObj.put("players", playersObj);
        return playersInformationObj;
    }

}
