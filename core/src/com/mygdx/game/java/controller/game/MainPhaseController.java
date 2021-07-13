package com.mygdx.game.java.controller.game;


import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.MonsterCardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.SpellTrapCardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.card.spelltrap.CardIcon;
import com.mygdx.game.java.model.card.spelltrap.SpellTrap;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.view.messageviewing.SuccessfulAction;

import java.util.ArrayList;

public class MainPhaseController {
    private final Player player;
    private final RoundController controller;


    public final ArrayList<CardInUse> summonedInThisPhase; //used in handling the flip summon. can be used for some effects of cards
    private boolean isAnyMonsterSet = false;

    {
        summonedInThisPhase = new ArrayList<>();
    }

    public MainPhaseController(RoundController controller) {
        this.controller = controller;
        this.player = controller.getCurrentPlayer();
        SummonController.resetNumOfNormalSummons();
    }


    public ArrayList<CardInUse> getSummonedInThisPhase() {
        return summonedInThisPhase;
    }

    private Card getSelectedCard() throws NoSelectedCard {
        if (this.controller.getSelectedCard() == null) throw new NoSelectedCard();
        return this.controller.getSelectedCard();
    }

    /* this function doesn't handle flip summon */
    public void summonMonster() throws NoSelectedCard, CantDoActionWithCard, AlreadyDoneAction, NotEnoughTributes, BeingFull {
        Card selectedCard = getSelectedCard();
        /* checking the errors */
        if (!player.getHand().doesContainCard(selectedCard) || !(selectedCard instanceof Monster))
            throw new CantDoActionWithCard("summon");
        if (player.getBoard().isMonsterZoneFull()) throw new BeingFull("monster card zone");
        if(isAnyMonsterSet) throw new AlreadyDoneAction("summoned/set");

        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) player.getBoard().getFirstEmptyCardInUse(true);
        SummonController summonController = new SummonController(monsterCardInUse, (Monster) selectedCard, controller, summonedInThisPhase);
        summonController.normal();
    }

    public void changePosition(boolean isToBeAttackMode)
            throws NoSelectedCard, AlreadyDoneAction, AlreadyInWantedPosition, CantDoActionWithCard {

        Card selectedCard = getSelectedCard();
        if (!(selectedCard instanceof Monster)) throw new CantDoActionWithCard("change position of");
        CardInUse cardInUse = player.getBoard().getCellOf(selectedCard);
        if (!(cardInUse instanceof MonsterCardInUse)) throw new CantDoActionWithCard("change position of");
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) cardInUse;

        if (monsterCardInUse.isPositionChanged()) throw new AlreadyDoneAction("changed this card position");
        if (controller.getCurrentPhase() == Phase.MAIN_2) {
            BattlePhaseController battle = controller.getDuelMenuController().getBattlePhaseController();
            if (battle != null) {
                ArrayList<CardInUse> alreadyAttacked = battle.attackedInThisTurn;
                if (alreadyAttacked.contains(cardInUse))
                    throw new CantDoActionWithCard("change position after attacking with");
            }
        }

        if (isToBeAttackMode) {
            if (monsterCardInUse.isInAttackMode() || !monsterCardInUse.isFaceUp()) throw new AlreadyInWantedPosition();
            monsterCardInUse.setInAttackMode(true);
        } else {
            if (!monsterCardInUse.isInAttackMode() || !monsterCardInUse.isFaceUp()) throw new AlreadyInWantedPosition();
            monsterCardInUse.setInAttackMode(false);
        }
        controller.updateBoards();
    }

    public void flipSummon() throws NoSelectedCard, CantDoActionWithCard {
        Card selectedCard = getSelectedCard();
        if (!(selectedCard instanceof Monster)) throw new CantDoActionWithCard("flip summon");
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) player.getBoard().getCellOf(selectedCard);
        if (monsterCardInUse == null) throw new CantDoActionWithCard("flip summon");

        if (summonedInThisPhase.contains(monsterCardInUse))
            throw new CantDoActionWithCard("flip summon");
        if (monsterCardInUse.isFaceUp() || monsterCardInUse.isInAttackMode())
            throw new CantDoActionWithCard("flip summon");

        monsterCardInUse.flipSummon();
        new SuccessfulAction("", "flip summoned");
        controller.updateBoards();
    }

    public void setCard() throws NoSelectedCard, CantDoActionWithCard, BeingFull, AlreadyDoneAction {
        Card selectedCard = getSelectedCard();
        if (!player.getHand().doesContainCard(selectedCard)) throw new CantDoActionWithCard("set");
        if (selectedCard instanceof Monster) setMonster((Monster) selectedCard);
        if (selectedCard instanceof SpellTrap) setSpellTrap((SpellTrap) selectedCard);
        controller.updateBoards();
    }

    private void setMonster(Monster selectedCard) throws BeingFull, AlreadyDoneAction {
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) player.getBoard().getFirstEmptyCardInUse(true);
        if (monsterCardInUse == null) throw new BeingFull("monster card zone");
        if (!summonedInThisPhase.isEmpty() || isAnyMonsterSet) throw new AlreadyDoneAction("summoned/set");

        player.getHand().removeCard(selectedCard);
        monsterCardInUse.setACardInCell(selectedCard);
        monsterCardInUse.setFaceUp(false);
        monsterCardInUse.setInAttackMode(false);
        isAnyMonsterSet = true;
        new SuccessfulAction("", "set");
    }

    private void setSpellTrap(SpellTrap selectedCard) throws BeingFull {
        SpellTrapCardInUse spellTrapCardInUse = (SpellTrapCardInUse) player.getBoard().getFirstEmptyCardInUse(false);
        if (spellTrapCardInUse == null) throw new BeingFull("spell card zone");

        player.getHand().removeCard(selectedCard);
        spellTrapCardInUse.setACardInCell(selectedCard);
        new SuccessfulAction("", "set");
        controller.updateBoards();
    }

    //todo: I think "of currentplayer " isn't needed. check with hasti
    public void activateEffect(boolean ofCurrentPlayer)
            throws NoSelectedCard, ActivateEffectNotSpell, BeingFull, AlreadyActivatedEffect {

        Card selectedCard = getSelectedCard();
        if (!(selectedCard instanceof SpellTrap)) throw new ActivateEffectNotSpell();
        if (ofCurrentPlayer && player.getHand().doesContainCard(selectedCard)) {
            activateEffectFromHand((SpellTrap) selectedCard);
        } else activateEffectFromBoard(ofCurrentPlayer);
        controller.updateBoards();
    }

    private void activateEffectFromBoard(boolean ofCurrentPlayer)
            throws NoSelectedCard, ActivateEffectNotSpell, AlreadyActivatedEffect {
        Card selectedCard = getSelectedCard();
        if (!(selectedCard instanceof SpellTrap)) throw new ActivateEffectNotSpell();
        SpellTrap spellCard = (SpellTrap) selectedCard;
        SpellTrapCardInUse cardInUse = (SpellTrapCardInUse) this.controller.getSelectedCardInUse();

        if (spellCard.isActivated()) throw new AlreadyActivatedEffect();
        cardInUse.activateMyEffect();

        new SuccessfulAction("Effect of spell " + selectedCard.getName(), " is activated");

        controller.updateBoards();

    }

    //the input is either a field spell or a spell that we should first set and then activate
    private void activateEffectFromHand(SpellTrap spell) throws BeingFull {
        if (spell == null) return;
        if (spell.getMyPreCard().getIcon().equals(CardIcon.FIELD)) {
            //it is a field spell inside hand and we want to send it to the field zone
            SpellTrapCardInUse fieldCell = player.getBoard().getFieldCell();
            if (fieldCell != null) {
                fieldCell.resetCell();
                fieldCell.sendToGraveYard();
                player.getHand().removeCard(spell);
                fieldCell.resetCell();
                fieldCell.setACardInCell(spell);
                fieldCell.activateMyEffect();
            }
        } else {
            //the spell is in hand and we should send it to the board and activate it
            SpellTrapCardInUse spellInUse = (SpellTrapCardInUse) player.getBoard().getFirstEmptyCardInUse(false);
            if (spellInUse == null) throw new BeingFull("spell card zone");

            spellInUse.setACardInCell(spell);
            spellInUse.activateMyEffect();
            player.getHand().removeCard(spell);
            new SuccessfulAction("Effect of spell " + spell.getName(), " is activated");
            controller.updateBoards();
        }
    }
}
