package org.foop.finalproject.theMessageServer;
import java.util.ArrayList;

public class ActionValidator {
    public ArrayList<GameCard> filterOnRoundStart(ArrayList<GameCard> handcards){
        ArrayList<GameCard> canPLayCards = new ArrayList<>();
        for(GameCard card:handcards){
            if(card.canPlayOnRoundStart()){
                canPLayCards.add(card);
            }
        }
        return canPLayCards;
    }

    public ArrayList<GameCard> filterOnPlayerTurn(ArrayList<GameCard> handcards){
        ArrayList<GameCard> canPLayCards = new ArrayList<>();
        for(GameCard card:handcards){
            if(card.canPlayOnPlayerTurn()){
                canPLayCards.add(card);
            }
        }
        return canPLayCards;
    }

    public ArrayList<GameCard> filterWhenOtherCardPlayed(ArrayList<GameCard> handcards){
        ArrayList<GameCard> canPLayCards = new ArrayList<>();
        for(GameCard card:handcards){
            if(card.canPlayWhenOtherCardPlayed()){
                canPLayCards.add(card);
            }
        }
        return canPLayCards;
    }
}
