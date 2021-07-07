package com.mygdx.game.java.model.Enums;

public enum Phase {
    DRAW(0),
    STANDBY(1),
    MAIN_1(2),
    BATTLE(3),
    MAIN_2(4),
    END(5),
    DRAW_RIVAL(6),
    STANDBY_RIVAL(7),
    MAIN_1_RIVAL(8),
    BATTLE_RIVAL(9),
    MAIN_2_RIVAL(10),
    END_RIVAL(11);


    private final int phaseId;

    Phase(int phaseId) {
        this.phaseId = phaseId;
    }

    public Phase goToNextPhase() {
        switch ((this.phaseId + 1) % 12) {
            case 0:
                return DRAW;
            case 1:
                return STANDBY;
            case 2:
                return MAIN_1;
            case 3:
                return BATTLE;
            case 4:
                return MAIN_2;
            case 5:
                return END;
            case 6:
                return DRAW_RIVAL;
            case 7:
                return STANDBY_RIVAL;
            case 8:
                return MAIN_1_RIVAL;
            case 9:
                return BATTLE_RIVAL;
            case 10:
                return MAIN_2_RIVAL;
            case 11:
                return END_RIVAL;
            default:
                return null;
        }
    }

    public Phase goToNextGamePhase() {
        switch ((this.phaseId + 1) % 6) {
            case 0:
                return DRAW;
            case 1:
                return STANDBY;
            case 2:
                return MAIN_1;
            case 3:
                return BATTLE;
            case 4:
                return MAIN_2;
            case 5:
                return END;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        if (this.name().equals("MAIN_1")) {
            return "main phase 1";
        } else if (this.name().equals("MAIN_2")) {
            return "main phase 2";
        }

        return this.name().toLowerCase() + " phase";
    }
}
