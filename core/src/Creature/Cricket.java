package Creature;

import com.mygdx.game.Player;

/**
 * Created by Immortan on 1/23/2016.
 */
public class Cricket extends Creature{

    public Cricket(float x, float y,Player player)
    {
        super(x,y);
        this.setTexture("Cricket");
        this.setTeam(player.getTeam());
        this.setSpeed(10);
        this.setGridSize(2);
        this.setDamage(25);
        this.setArmor(2);
        this.setArmorPiercing(1);
        this.setHealth(250);
    }
}
