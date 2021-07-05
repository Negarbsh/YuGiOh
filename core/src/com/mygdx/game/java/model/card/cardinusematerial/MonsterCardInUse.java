//TODO complete putInGraveYard

package com.mygdx.game.java.model.card.cardinusematerial;

import lombok.Getter;
import lombok.Setter;
import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.monster.Monster;

@Getter
@Setter
public class MonsterCardInUse extends CardInUse {
    private int attack, defense;
    private boolean isInAttackMode;
    public boolean canBeDestroyed;

    {
        resetCell();
        defense = 0;
        attack = 0;
        isInAttackMode = true; //it's needed for the first use of "setInAttackMode"
        canBeDestroyed = true;
        //todo: if the monster is flip summoned, should we be able to change its attackMode after that?
    }

    public MonsterCardInUse(Board board) {
        super(board);
    }


    public void addToAttack(int amount) {   //amount can be negative
        this.attack += amount;
    }

    public void addToDefense(int amount) {   //amount can be negative
        this.defense += amount;
    }

    public void setInAttackMode(boolean inAttackMode) { //todo : watchers should be notified
        if (isInAttackMode != inAttackMode) {
            isInAttackMode = inAttackMode;
            this.isPositionChanged = true;
        }
    }

    @Override
    public void setACardInCell(Card card) {
        this.attack = ((Monster) card).getRawAttack();
        this.defense = ((Monster) card).getRawDefense();
        super.setACardInCell(card);
    }


    public void flipSummon() {
        watchByState(CardState.FLIP_SUMMON);
        faceUpCard();
        setInAttackMode(true);
    }

    /*
    * if the card is in attack point returns attack point
    * else returns defense point
    * */
    public int appropriatePointAtBattle() {
        if (isInAttackMode)
            return attack;

        return defense;
    }

    @Override
    public void resetCell() {
        isInAttackMode = false;
        canBeDestroyed = true;
        setAttack(0);
        setDefense(0);
        super.resetCell();
    }


    public void destroyThis() {
        watchByState(CardState.DESTROY);
        if (canBeDestroyed)     sendToGraveYard();
    }

    public void summon() {
        watchByState(CardState.SUMMON);
    }


}
