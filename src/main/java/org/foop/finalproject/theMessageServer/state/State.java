package org.foop.finalproject.theMessageServer.state;
import java.util.Stack;

import org.foop.finalproject.theMessageServer.GameCard;
import org.foop.finalproject.theMessageServer.Player;
import org.foop.finalproject.theMessageServer.Action;

public abstract class State{
    Player creater;
    Stack<Action> currentBoard;
    Round currentRound;
}
