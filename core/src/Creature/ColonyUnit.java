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

    public ColonyUnit(float x, float y, Player player, int unitType) {
        super(x, y);
        this.setPlayer(player);
        this.unitType = unitType;
        this.setTexture(Player.factionString[player.getFaction()] + "_" + unitString[unitType]);
        this.setSpeed(20);
        this.setGridSize(1);
        this.setDamage(10);
        this.setArmor(1);
        this.setArmorPiercing(0);
        this.setHealth(100);
        setStats();
    }

    private void setStats() {
        switch (this.getPlayer().getFaction()) {
            case (Player.ANT_FIRE):
                this.setDamage(15);
                this.setArmorPiercing(1);
                this.setHealth(90);
        }
        switch (unitType){
            case(SOLDIER):
                this.setDamage(this.getDamage()+30);
                this.setHealth(this.getHealth()+200);
                this.setArmor(this.getArmor()+5);
        }
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }
}