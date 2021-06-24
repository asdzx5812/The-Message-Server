package org.foop.finalproject.theMessageServer.GameCards;
import org.foop.finalproject.theMessageServer.Game;
import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;

public class Prove extends GameCard {
    public void Prove() {
        name = "PROVE"; // 試探
        timingDescription = "Play in playing step of own round.";
        effectDescription = "Only the player be proved can check the card and take below action according to his identity, and then remove it out of game.";
        playOnPlayerTurn = true;
    }

    @Override
    public void perform(Player performer, Player playerTarget, Game game) {

    }
}