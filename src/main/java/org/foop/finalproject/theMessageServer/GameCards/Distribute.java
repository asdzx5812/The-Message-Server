package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;

public class Distribute extends GameCard {
    public Distribute() {
        super();
        name = "DISTRIBUTE"; // 真偽莫辨
        timingDescription = "Play when playing cards in own round";
        effectDescription = "Draw from deck top equal to the number of players and shuffle then anti-clockwise randomly distribute one to each player as message. You take first.";
        playOnPlayerTurn = true;
    }

    @Override
    public void perform(Player performer, Player playerTarget, Game game) {

    }
}
