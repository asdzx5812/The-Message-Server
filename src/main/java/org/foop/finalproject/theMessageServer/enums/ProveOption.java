package org.foop.finalproject.theMessageServer.enums;

public enum ProveOption {
    DRAW_TWO_CARDS("抽二張牌"),
    THROW_ONE_CARD("棄一張手牌"),
    NICE_GUY("回答「我是一個好人」"),
    BAD_GUY("回答「其實我是臥底」");
    public String proveOption;
    ProveOption(String proveOption) {
        this.proveOption = proveOption;
    }

}
