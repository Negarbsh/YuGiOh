package main.java.controller.game;

import lombok.Getter;
import lombok.Setter;
import main.java.model.Board;
import main.java.model.CardState;
import main.java.model.card.cardinusematerial.MonsterCardInUse;
import main.java.view.Print;
import main.java.view.messageviewing.Winner;

@Getter
@Setter
public class BattleController {
    int differenceOfPoints;
    private Board attackerBoard, preyBoard;
    private MonsterCardInUse attacker, preyCard;
    private boolean isPreyCardInAttackMode;
    private int attackerAttack, preyPoint;
    public boolean canBattleHappen = true;

    public BattleController(MonsterCardInUse attacker, MonsterCardInUse preyCard,
                            BattlePhaseController battlePhaseController) {
        this.attackerBoard = attacker.getBoard();
        this.preyBoard = preyCard.getBoard();
        this.attacker = attacker;
        this.preyCard = preyCard;
        MainPhaseController mainPhase = preyBoard.getController().getMainPhaseController();
        preyBoard.getController().getBattlePhaseController().battleController = this;
        int summonedCardsLength = mainPhase.summonedInThisPhase.size();
        attackerAttack = attacker.getAttack();
        preyPoint = preyCard.appropriatePointAtBattle();
        attacker.watchByState(CardState.WANT_TO_ATTACK);
        preyCard.watchByState(CardState.IS_ATTACKED);
        isPreyCardInAttackMode = preyCard.isInAttackMode();
        if (canBattleHappen && !attacker.isCellEmpty()) {
            if (preyCard.isCellEmpty() || summonedCardsLength > mainPhase.summonedInThisPhase.size())
                battlePhaseController.reArrangeBattle(attacker);
            else run();
        } else System.out.println("this battle can't happen");
    }

    private void run() {

        if (!preyCard.isFaceUp()) {
            preyCard.faceUpCard();
            Print.print(String.format("opponent’s monster card was %s",
                    preyCard.getThisCard().getName()));
        }
        differenceOfPoints = Math.abs(attackerAttack - preyPoint);

        if (attackerAttack > preyPoint) {
            attackerWins();
        } else if (attackerAttack == preyPoint) {
            noneWins();
        } else {
            preyWins();
        }
        preyBoard.getController().getRoundController().updateBoards();
    }

    private void attackerWins() {
        if (isPreyCardInAttackMode) {
            preyCard.destroyThis();
            preyBoard.getOwner().decreaseLifePoint(differenceOfPoints);
            Winner.setWinner(Winner.AGAINST_A_WINS, differenceOfPoints);
        } else {
            Winner winner = Winner.AGAINST_D_WINS;
            preyCard.destroyThis();
            Winner.setWinner(winner, differenceOfPoints);
        }
    }

    private void preyWins() {
        if (isPreyCardInAttackMode) {
            attacker.destroyThis();
            attackerBoard.getOwner().decreaseLifePoint(differenceOfPoints);
            Winner.setWinner(Winner.AGAINST_A_LOSE, differenceOfPoints);
        } else {
            Winner winner = Winner.AGAINST_D_LOSE;
            attackerBoard.getOwner().decreaseLifePoint(differenceOfPoints);
            Winner.setWinner(winner, differenceOfPoints);
        }
    }

    private void noneWins() {
        if (isPreyCardInAttackMode) {
            attacker.destroyThis();
            preyCard.destroyThis();
            Winner.setWinner(Winner.AGAINST_A_NONE, differenceOfPoints);
        } else {
            Winner winner = Winner.AGAINST_D_NONE;
            Winner.setWinner(winner, differenceOfPoints);
        }
    }
}
