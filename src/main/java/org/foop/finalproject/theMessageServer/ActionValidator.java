package org.foop.finalproject.theMessageServer;
import java.util.ArrayList;

public class ActionValidator {
    public ArrayList<GameCard> filterOnRoundStart(ArrayList<GameCard> handcards){
        ArrayList<GameCard> validCards = new ArrayList<>();
        for(GameCard card:handcards){
            if(card.canPlayOnRoundStart()){
                validCards.add(card);
            }
        }
        return validCards;
    }
}
