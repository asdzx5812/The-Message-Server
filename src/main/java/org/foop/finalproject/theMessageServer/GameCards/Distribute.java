package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Round;
import org.foop.finalproject.theMessageServer.enums.GameCardColor;
import org.foop.finalproject.theMessageServer.enums.IntelligenceType;
import java.util.ArrayList;
public class Distribute extends GameCard {
    public Distribute(GameCardColor gameCardColor, IntelligenceType intelligenceType, int order) {
        super(gameCardColor, intelligenceType, order);
        // name = "DISTRIBUTE"; // 真偽莫辨
        // timingDescription = "Play when playing cards in own round";
        // effectDescription = "Draw from deck top equal to the number of players and shuffle then anti-clockwise randomly distribute one to each player as message. You take first.";
        name = "真偽莫辨"; // 真偽莫辨
        timingDescription = "只能在自己回合使用。";
        effectDescription = "從牌庫頂將等同於現存玩家數的牌面朝下放置在桌面，從自己開始，逆時針每人輪流（隨機）選擇一張作為情報。";
        setId(name);
    }

    @Override
    public void perform(Player performer, Player playerTarget, Game game) {
        ArrayList<Player> players = game.getPlayers();
        int currentPlayerIdx = players.indexOf(performer);
        for(int i = 0; i < players.size(); i++){
            int turnPlayerIdx = (currentPlayerIdx + i) % players.size();
            players.get(turnPlayerIdx).drawCards_wo_broadcast(1);
            //TODO 要做真偽莫辨的動畫可以在這廣播
            //
            //
        }
        messageService.broadcastGameInformation(game, players);
    }

    @Override
    public String getGameMessage(Player performer, Player playerTarget, Game game) {
        return "";
    }

    @Override
    public boolean isValid(Round currentRound, Player owner){
        return currentRound.isGameCardRound() && currentRound.parentRoundIsMainRound() && currentRound.playerIsCurrentPlayerOfParentRound(owner);
    }
}
