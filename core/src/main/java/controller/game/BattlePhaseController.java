package main.java.controller.game;


import main.java.model.Enums.Phase;
import main.java.model.Enums.ZoneName;
import main.java.model.card.CardType;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.card.cardinusematerial.MonsterCardInUse;
import main.java.view.messageviewing.Print;
import main.java.view.exceptions.*;

import java.util.ArrayList;
import java.util.Collections;

public class BattlePhaseController {

    public RoundController gamePlay;
    public BattleController battleController;

    public ArrayList<CardInUse> attackedInThisTurn;

    {
        attackedInThisTurn = new ArrayList<>();
    }

    public BattlePhaseController(RoundController gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void battleAnnounced(int cellOfCard) throws NoSelectedCard, CardCantAttack, WrongPhaseForAction, NoCardToAttack, CardAttackedBeforeExeption, InvalidSelection, NoCardFound {
        MonsterCardInUse preyCard;
        MonsterCardInUse attacker;
        CardInUse cardInUse = gamePlay.getSelectedCardInUse();
        if (cardInUse == null || cardInUse.isCellEmpty())
            throw new NoSelectedCard();
        else if (!(cardInUse instanceof MonsterCardInUse))
            throw new CardCantAttack();
        else if (!((MonsterCardInUse) cardInUse).isInAttackMode())
            throw new CardCantAttack();
        else if (gamePlay.getCurrentPhase() != Phase.BATTLE)
            throw new WrongPhaseForAction();
        else if ((preyCard = gamePlay.getActionsOnRival().getRivalMonsterCell(cellOfCard)).isCellEmpty())
            throw new NoCardToAttack();
        else if (attackedInThisTurn.contains(attacker = (MonsterCardInUse) cardInUse))
            throw new CardAttackedBeforeExeption();
        else {
            battleController = new BattleController(attacker, preyCard, this);
            attackedInThisTurn.add(attacker);
        }
    }

    public void reArrangeBattle(MonsterCardInUse attacker) {
        if (gamePlay.arrangeAlternateBattle()) {
            SelectController selectController = new SelectController(new ArrayList<>(Collections.singletonList(ZoneName.RIVAL_MONSTER_ZONE)), gamePlay, attacker.getOwnerOfCard());
            selectController.setCardType(CardType.MONSTER);
            MonsterCardInUse preyCard = (MonsterCardInUse) selectController.getTheCardInUse();
            if (preyCard != null)
                battleController = new BattleController(attacker, preyCard, this);
            else    Print.print(Print.cancelBattle);
        }
    }

    public void attackToLifePoint() throws NoSelectedCard, CardCantAttack, WrongPhaseForAction, CardAttackedBeforeExeption, CantAttackDirectlyException {
        MonsterCardInUse attacker;
        CardInUse cardInUse = gamePlay.getSelectedCardInUse();
        if (cardInUse == null || cardInUse.isCellEmpty())
            throw new NoSelectedCard();
        else if (!(cardInUse instanceof MonsterCardInUse))
            throw new CardCantAttack();
        else if (!((MonsterCardInUse) cardInUse).isInAttackMode())
            throw new CardCantAttack();
        else if (gamePlay.getCurrentPhase() != Phase.BATTLE)
            throw new WrongPhaseForAction();
        else if (attackedInThisTurn.contains(attacker = (MonsterCardInUse) cardInUse))
            throw new CardAttackedBeforeExeption();
        else if (!canAttackDirectly())
            throw new CantAttackDirectlyException();
        else
            gamePlay.getRival().decreaseLifePoint(attacker.getAttack());
        gamePlay.updateBoards();
    }

    private boolean canAttackDirectly() {   //complete
        return gamePlay.getRivalBoard().getFirstEmptyCardInUse(true) == null;
    }


}
