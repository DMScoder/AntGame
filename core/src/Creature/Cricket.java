package Creature;

/**
 * Created by Immortan on 1/23/2016.
 */
public class Cricket extends Creature{

    public Cricket(float x, float y)
    {
        super(x,y);
        this.setTexture("Cricket");
        this.setTeam(2);
        this.setSpeed(10);
        this.setGridSize(2);
        this.setDamage(25);
        this.setArmor(2);
        this.setArmorPiercing(1);
        this.setHealth(250);
    }
}
