package com.mygdx.game.java.model.watchers;

import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.controller.game.RoundController;
import lombok.Setter;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.monster.MonsterType;
import com.mygdx.game.java.model.card.spelltrap.PreSpellTrapCard;
import com.mygdx.game.java.model.watchers.monsters.*;
import com.mygdx.game.java.model.watchers.spells.*;
import com.mygdx.game.java.model.watchers.traps.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Watcher implements Comparable {
    public static HashMap<String, Watcher> allWatchers;
    @Setter
    public static RoundController roundController;
    public static ArrayList<Watcher> stack;
    public WhoToWatch whoToWatch;
    public ArrayList<CardInUse> amWatching;
    public boolean isWatcherActivated = false;
    public CardInUse ownerOfWatcher;
    protected static boolean isInChainMode = false;
    public boolean firstOfStack = false;
    public int speed = 1;

    static {
        allWatchers = new HashMap<>();
        stack = new ArrayList<>();
    }

    {
        amWatching = new ArrayList<>();
    }

    public Watcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        this.ownerOfWatcher = ownerOfWatcher;
        this.whoToWatch = whoToWatch;
    }

    public abstract void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController);

    /*
    sees if the watcher can be put on cards.
     */
    public abstract boolean canPutWatcher();

    public abstract void putWatcher(CardInUse cardInUse);

    public void update(Phase newPhase) {
    }

    public void disableWatcher(CardInUse cardInUse) {    //when probably the card is destroyed
        amWatching.remove(cardInUse);
    }

    public void deleteWatcher() {   //when owner of watcher is destroyed or the watcher can only be used once
        for (CardInUse cardInUse : amWatching) {
            cardInUse.watchersOfCardInUse.remove(this);
            amWatching.remove(cardInUse);
        }
    }

    protected static void emptyStack() {
        if (stack.size() > 0)
            stack.remove(stack.size() - 1);
        else
            System.out.println("bug bug stack empty");  //TODO remove
    }

    /*
    if can add watcher to stack -> true
    else -> false
     */
    protected static boolean addToStack(Watcher watcher) {
        if (!stack.contains(watcher)) {
            if (stack.size() == 0 || stack.get(stack.size() - 1).speed <= watcher.speed) {
                if (stack.size() !=0 && stack.get(stack.size() - 1).ownerOfWatcher.ownerOfCard != watcher.ownerOfWatcher.ownerOfCard)
                    roundController.temporaryTurnChange(watcher.ownerOfWatcher.ownerOfCard);
                if (watcher.ownerOfWatcher.thisCard.preCardInGeneral instanceof PreSpellTrapCard) {
                    PreSpellTrapCard preSpellTrapCard = (PreSpellTrapCard) watcher.ownerOfWatcher.thisCard.preCardInGeneral;
                    if (preSpellTrapCard.getCardType() == CardType.TRAP) {
                        if (roundController.wantToActivateCard(watcher.ownerOfWatcher.thisCard.getName())) {
                            stack.add(watcher);
                            return true;
                        }
                    } else {
                        stack.add(watcher);
                        return true;
                    }
                } else {
                    stack.add(watcher);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean handleChain() {
        if (Watcher.addToStack(this)) {
            ownerOfWatcher.watchByState(CardState.ACTIVE_EFFECT);
            emptyStack();
            return true;
        }

        return false;
    }

    public void trapHasDoneItsEffect() {
        isWatcherActivated = true;
        ownerOfWatcher.sendToGraveYard();
    }

    public void addWatcherToCardInUse(CardInUse cardInUse) {
        if (cardInUse == null) return;
        if (!cardInUse.isCellEmpty() && !amWatching.contains(cardInUse)) {
            cardInUse.watchersOfCardInUse.add(this);
            amWatching.add(cardInUse);
        }
    }


    public static Watcher createWatcher(String nameWatcher, CardInUse ownerOfWatcher) {
        switch (nameWatcher) {
            //monsters
            case "CommandKnightHolyWatcher":
                return new CommandKnightHolyWatcher(ownerOfWatcher, WhoToWatch.MINE);
            case "CommandKnightWatcher":
                return new CommandKnightWatcher(ownerOfWatcher, WhoToWatch.MINE);
            case "ManEaterWatcher":
                return new ManEaterWatcher(ownerOfWatcher, WhoToWatch.MINE);
            case "MarshmallonHolyWatcher":
                return new MarshmallonHolyWatcher(ownerOfWatcher, WhoToWatch.MINE);
            case "MarshmallonWatcher":
                return new MarshmallonWatcher(ownerOfWatcher, WhoToWatch.MINE);
            case "SuijinWatcher":
                return new SuijinWatcher(ownerOfWatcher, WhoToWatch.MINE);
            case "TexChangerWatcher":
                return new TexChangerWatcher(ownerOfWatcher, WhoToWatch.MINE);
            case "TheCalculatorWatcher":
                return new TheCalculatorWatcher(ownerOfWatcher, WhoToWatch.MINE);
            case "YomiShipWatcher":
                return new YomiShipWatcher(ownerOfWatcher, WhoToWatch.MINE);
            //spells
            case "AdvancedRitualArtWatcher":
                return new AdvancedRitualArtWatcher(ownerOfWatcher, WhoToWatch.MINE);
            case "Dark Hole":
                return new DestroyAllWatcher(ownerOfWatcher, WhoToWatch.ALL, Zone.MONSTER);
            case "Harpie":
                return new DestroyAllWatcher(ownerOfWatcher, WhoToWatch.RIVALS, Zone.SPELL);
            case "Raigeki":
                return new DestroyAllWatcher(ownerOfWatcher, WhoToWatch.RIVALS, Zone.MONSTER);
            case "MonsterRebornWatcher":
                return new MonsterRebornWatcher(ownerOfWatcher, WhoToWatch.ALL);
            //equips
            case "Sword-dark":
                return new EquipWatcher(ownerOfWatcher, new MonsterType[]{MonsterType.SPELLCASTER, MonsterType.FIEND}, 400, -200, WhoToWatch.ALL);
            case "Black":
                return new EquipWatcher(ownerOfWatcher, null, 500, 0, WhoToWatch.ALL);
            case "United":
                return new UnitedWatcher(ownerOfWatcher, null, 800, 800, WhoToWatch.ALL);
            case "Magnum":
                return new MagnumWatcher(ownerOfWatcher, new MonsterType[]{MonsterType.WARRIOR}, 0, 0, WhoToWatch.ALL);
            //Field spell
            case "YamiFirst":
                return new FieldWatcher(ownerOfWatcher, new MonsterType[]{MonsterType.FIEND, MonsterType.SPELLCASTER}, 200, 200, WhoToWatch.ALL);
            case "YamiSec":
                return new FieldWatcher(ownerOfWatcher, new MonsterType[]{MonsterType.FAIRY}, -200, -200, WhoToWatch.ALL);
            case "Forest":
                return new FieldWatcher(ownerOfWatcher, new MonsterType[]{MonsterType.INSECT, MonsterType.BEAST_WARRIOR, MonsterType.BEAST}, 200, 200, WhoToWatch.ALL);
            case "Umiiruka":
                return new FieldWatcher(ownerOfWatcher, new MonsterType[]{MonsterType.AQUA}, 500, -400, WhoToWatch.ALL);
            case "ClosedForest":
                return new ClosedForestWatcher(ownerOfWatcher, new MonsterType[]{MonsterType.BEAST, MonsterType.BEAST_WARRIOR}, 100, 0, WhoToWatch.MINE);
            //traps
            case "MagicCylinderWatcher":
                return new MagicCylinderWatcher(ownerOfWatcher, WhoToWatch.RIVALS);
            case "MirrorForceWatcher":
                return new MirrorForceWatcher(ownerOfWatcher, WhoToWatch.RIVALS);
            case "TrapHoleWatcher":
                return new TrapHoleWatcher(ownerOfWatcher, WhoToWatch.RIVALS);
            case "TorrentialTributeWatcher":
                return new TorrentialTributeWatcher(ownerOfWatcher, WhoToWatch.ALL);
            case "TimeSealWatcher":
                return new TimeSealWatcher(ownerOfWatcher, WhoToWatch.RIVALS);
            case "NegateAttackWatcher":
                return new NegateAttackWatcher(ownerOfWatcher, WhoToWatch.RIVALS);
        }

        System.out.println("wrong name " + nameWatcher);
        return null;
    }

    public static CardInUse[] uniteArrays(CardInUse[] a, CardInUse[] b) {
        HashSet<CardInUse> set = new HashSet<>();
        set.addAll(Arrays.asList(a));
        set.addAll(Arrays.asList(b));

        return (CardInUse[]) set.toArray();
    }

    public CardInUse[] theTargetCells(Zone zoneName) {
        if (whoToWatch == WhoToWatch.ALL) {
            switch (zoneName) {
                case MONSTER:
                    return uniteArrays(roundController.getCurrentPlayer().getBoard().getMonsterZone(),
                            roundController.getRivalBoard().getMonsterZone());
                case SPELL:
                    return uniteArrays(roundController.getCurrentPlayerBoard().getSpellTrapZone(),
                            roundController.getRivalBoard().getSpellTrapZone());
            }
        } else if (whoToWatch == WhoToWatch.MINE) {
            switch (zoneName) {
                case MONSTER:
                    return ownerOfWatcher.getBoard().getMonsterZone();
                case SPELL:
                    return ownerOfWatcher.getBoard().getSpellTrapZone();
            }
        } else if (whoToWatch == WhoToWatch.RIVALS) {
            Player myRival = roundController.getMyRival(ownerOfWatcher.getOwnerOfCard());
            switch (zoneName) {
                case MONSTER:
                    return myRival.getBoard().getMonsterZone();
                case SPELL:
                    return myRival.getBoard().getSpellTrapZone();
            }
        }

        //TODO remove sout
        System.out.println("who to watch is null");
        return null;
    }

    @Override
    public int compareTo(Object o) {
        Watcher secWatcher = (Watcher) o;
        if (this.whoToWatch == WhoToWatch.MINE)
            return 1;
        else if (this.whoToWatch == WhoToWatch.RIVALS)
            return -1;
        else {
            if (secWatcher.whoToWatch == WhoToWatch.MINE)
                return -1;
            else if (secWatcher.whoToWatch == WhoToWatch.RIVALS)
                return 1;
            else
                return 0;
        }
    }
}
