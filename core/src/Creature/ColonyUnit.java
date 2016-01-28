package Creature;

import com.mygdx.game.Player;

/**
 * Created by Immortan on 1/25/2016.
 */
public class ColonyUnit extends Creature {

    public static final int WORKER = 0;
    public static final int FORAGER = 1;
    public static final int SCOUT = 2;
    public static final int FLYER = 3;
    public static final int SOLDIER = 4;
    public static final int QUEEN = 5;
    public static final String[] unitString = {"Worker", "Forager",
            "Scout", "Flyer", "Soldier", "Queen"};


    private int unitType;
    private int faction;

    public ColonyUnit(float x, float y, int faction, int unitType) {
        super(x, y);
        this.faction = faction;
        this.unitType = unitType;
        this.setTexture(Player.factionString[faction] + "_" + unitString[unitType]);
        this.setSpeed(20);
        this.setGridSize(1);
        this.setDamage(10);
        this.setArmor(1);
        this.setArmorPiercing(0);
        this.setHealth(100);
        setStats();
    }

    private void setStats() {
        switch (faction) {
            case (Player.ANT_FIRE):
                this.setDamage(15);
                this.setArmorPiercing(1);
                this.setHealth(90);
        }
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public int getFaction() {
        return faction;
    }

    public void setFaction(int faction) {
        this.faction = faction;
    }
}