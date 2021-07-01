package com.mygdx.game.java.model.Enums;

import com.mygdx.game.java.view.exceptions.InvalidSelection;
import com.mygdx.game.java.model.watchers.WhoToWatch;
import com.mygdx.game.java.model.watchers.Zone;

import java.util.ArrayList;

public enum ZoneName {
    HAND,
    MY_MONSTER_ZONE,
    MY_SPELL_ZONE,
    MY_FIELD,
    MY_GRAVEYARD,
    MY_DECK,
    RIVAL_MONSTER_ZONE,
    RIVAL_SPELL_ZONE,
    RIVAL_FIELD,
    RIVAL_GRAVEYARD;

    public static ZoneName getZoneName(String field, boolean isForRival) throws InvalidSelection {
        ZoneName zoneName;
        switch (field.toLowerCase()) {
            case "hand":
                if (!isForRival) zoneName = ZoneName.HAND;
                else throw new InvalidSelection();
                break;
            case "monster":
                if (!isForRival) zoneName = ZoneName.MY_MONSTER_ZONE;
                else zoneName = ZoneName.RIVAL_MONSTER_ZONE;
                break;
            case "spell":
                if (!isForRival) zoneName = ZoneName.MY_SPELL_ZONE;
                else zoneName = ZoneName.RIVAL_SPELL_ZONE;
                break;
            case "field":
                if (!isForRival) zoneName = ZoneName.MY_FIELD;
                else zoneName = ZoneName.RIVAL_FIELD;
                break;
            case "graveyard":
                if (!isForRival) zoneName = ZoneName.MY_GRAVEYARD;
                else zoneName = ZoneName.RIVAL_GRAVEYARD;
                break;
            case "deck":
                if (isForRival) throw new InvalidSelection();
                else zoneName = ZoneName.MY_DECK;
                break;
            default:
                throw new InvalidSelection();
        }
        return zoneName;
    }

    public static ArrayList<ZoneName> getZoneNamesByZone(Zone zone, WhoToWatch whoToWatch) {
        ArrayList<ZoneName> toReturn = new ArrayList<>();
        switch (zone) {

            case SPELL:
                if (whoToWatch != WhoToWatch.RIVALS) toReturn.add(ZoneName.MY_SPELL_ZONE);
                if (whoToWatch != WhoToWatch.MINE) toReturn.add(ZoneName.RIVAL_SPELL_ZONE);
                break;
            case MONSTER:
                if (whoToWatch != WhoToWatch.RIVALS) toReturn.add(ZoneName.MY_MONSTER_ZONE);
                if (whoToWatch != WhoToWatch.MINE) toReturn.add(ZoneName.MY_SPELL_ZONE);
                break;
            case FIELD:
                if (whoToWatch != WhoToWatch.RIVALS) toReturn.add(ZoneName.MY_FIELD);
                if (whoToWatch != WhoToWatch.MINE) toReturn.add(ZoneName.RIVAL_FIELD);
                break;
            case GRAVEYARD:
                if (whoToWatch != WhoToWatch.RIVALS) toReturn.add(ZoneName.MY_GRAVEYARD);
                if (whoToWatch != WhoToWatch.MINE) toReturn.add(ZoneName.RIVAL_GRAVEYARD);
                break;
            case HAND:
                break;
        }
        return toReturn;
    }
}
