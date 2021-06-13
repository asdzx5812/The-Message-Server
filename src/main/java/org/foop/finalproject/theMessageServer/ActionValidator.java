package org.foop.finalproject.theMessageServer;
import java.util.ArrayList;

public class ActionValidator {
    public ArrayList<GameCard> filterOnRoundStart(Player currentPlayer){
        ArrayList<GameCard> validCards = new ArrayList<>();
        for(GameCard card:currentPlayer.handCards){
            if(card.canPlayOnRoundStart()){
                validCards.add(card);
            }
        }
        return validCards;
    }
    public ArrayList<GameCard> filterOnPlayerTurn(Player currentPlayer){
        ArrayList<GameCard> validCards = new ArrayList<>();
        for(GameCard card:currentPlayer.handCards){
            if(card.canPlayOnPlayerTurn()){
                validCards.add(card);
            }
        }
        return validCards;
    }

    public ArrayList<GameCard> filterCardPlayed(Player currentPlayer){
        ArrayList<GameCard> validCards = new ArrayList<>();
        for(GameCard card:currentPlayer.handCards){
            if(card.canPlayWhenOtherCardPlayed()){
                validCards.add(card);
            }
        }
        return validCards;
    }
    public ArrayList<GameCard> filterOnIntelligencePassing(Player currentPlayer, Player intelligenceSender, Player intelligenceIsInFrontOfThisPlayer){
        ArrayList<GameCard> validCards = new ArrayList<>();
        if(currentPlayer == intelligenceSender){
            if(currentPlayer == intelligenceIsInFrontOfThisPlayer){
                for(GameCard card:currentPlayer.handCards){
                    if(card.canPlayOnWhenIntelligenceSendByPlayer() && card.canPlayOnWhenIntelligencePassingInFrontOfPlayer()){
                        validCards.add(card);
                    }
                }
            } else{
                for(GameCard card:currentPlayer.handCards){
                    if(card.canPlayOnWhenIntelligenceSendByPlayer() && card.canPlayOnWhenIntelligencePassingInFrontOfOthers()){
                        validCards.add(card);
                    }
                }
            }
        }
        else{
            if(currentPlayer == intelligenceIsInFrontOfThisPlayer){
                for(GameCard card:currentPlayer.handCards){
                    if(card.canPlayOnWhenIntelligenceSendByOthers() && card.canPlayOnWhenIntelligencePassingInFrontOfPlayer()){
                        validCards.add(card);
                    }
                }
            } else{
                for(GameCard card:currentPlayer.handCards){
                    if(card.canPlayOnWhenIntelligenceSendByOthers() && card.canPlayOnWhenIntelligencePassingInFrontOfOthers()){
                        validCards.add(card);
                    }
                }
            }
        }
        return validCards;
    }
}
