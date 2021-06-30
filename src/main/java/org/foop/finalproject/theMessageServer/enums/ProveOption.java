package org.foop.finalproject.theMessageServer.enums;

public enum ProveOption {
    DRAW_TWO_CARDS("抽二張牌", 0, 0),
    BAD_GUY("回答「其實我是臥底」", 0, 1),
    THROW_ONE_CARD("棄一張手牌", 1, 0),
    NICE_GUY("回答「我是一個好人」", 1, 1),
    staticFunctions(null, -1, -1);

    public String proveOption;
    public int proveType;
    public int chosedOption;
    ProveOption(String proveOption, int proveType, int chosedOption) {

        this.proveOption = proveOption;
        this.proveType = proveType;
        this.chosedOption = chosedOption;
    }
    public ProveOption getOption(int proveType, int chosedOption){
        switch (proveType){
            case 0:
                switch (chosedOption){
                    case 0:
                        return DRAW_TWO_CARDS;
                    case 1:
                        return BAD_GUY;
                    default:
                        GetUnexpectedArgumentError(proveType, chosedOption);
                        break;
                }
                break;
            case 1:
                switch (chosedOption) {
                    case 0:
                        return THROW_ONE_CARD;
                    case 1:
                        return NICE_GUY;
                    default:
                        GetUnexpectedArgumentError(proveType, chosedOption);
                        break;
                }
                break;
            default:
                GetUnexpectedArgumentError(proveType, chosedOption);
                break;
        }
        return null;
    }
    private void GetUnexpectedArgumentError(int proveType, int chosedOption){
        System.out.println("This is not expected -- proveType" + proveType + ", chosedOption:" + chosedOption + ".");
    }

    public String[] getPossibleOptions(int proveType){
        switch (proveType){
            case 0:
                return new String[] {ProveOption.DRAW_TWO_CARDS.proveOption, ProveOption.BAD_GUY.proveOption};
            case 1:
                return new String[] {ProveOption.THROW_ONE_CARD.proveOption, ProveOption.NICE_GUY.proveOption};
            default:
                System.out.println("Error: unexpected proveType:" + proveType);
                return null;
        }
    }
}
