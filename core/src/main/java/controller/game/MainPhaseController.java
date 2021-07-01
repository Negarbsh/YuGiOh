package main.java.controller.game;


import main.java.model.Player;
import main.java.model.card.Card;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.card.cardinusematerial.MonsterCardInUse;
import main.java.model.card.cardinusematerial.SpellTrapCardInUse;
import main.java.model.card.monster.Monster;
import main.java.model.card.spelltrap.CardIcon;
import main.java.model.card.spelltrap.SpellTrap;
import main.java.view.exceptions.*;
import main.java.view.messageviewing.Print;
import main.java.view.messageviewing.SuccessfulAction;

import java.util.ArrayList;

public class MainPhaseController {
    private final Player player;
    private final RoundController controller;


    public final ArrayList<CardInUse> summonedInThisPhase; //used in handling the flip summon. can be used for some effects of cards

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
        if (!summonedInThisPhase.isEmpty()) throw new AlreadyDoneAction("summoned/set");

        player.getHand().removeCard(selectedCard);
        monsterCardInUse.setACardInCell(selectedCard);
        monsterCardInUse.setFaceUp(false);
        monsterCardInUse.setInAttackMode(false);
        new SuccessfulAction("", "set");
    }

    private void setSpellTrap(SpellTrap selectedCard) throws BeingFull {
        SpellTrapCardInUse spellTrapCardInUse = (SpellTrapCardInUse) player.getBoard().getFirstEmptyCardInUse(false);
        if (spellTrapCardInUse == null) throw new BeingFull("spell card zone");

        player.getHand().removeCard(selectedCard);
        spellTrapCardInUse.setThisCard(selectedCard);
        spellTrapCardInUse.setFaceUp(false);
        new SuccessfulAction("", "set");
        controller.updateBoards();

        //todo: the spell or trap card in use should be face down. but will it be needed?
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

        Print.print("spell " + spellCard.name + "  is activated");
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
            Print.print("spell activated");
            controller.updateBoards();
        }
    }
}
