package Creature;

/**
 * Created by Immortan on 1/18/2016.
 */
public class Ant extends Creature{

    public Ant(float x, float y)
    {
        super(x, y);
        this.setTexture("Default_Ant");
        this.setSpeed(20);
        this.setGridSize(1);
        this.setDamage(10);
        this.setArmor(1);
        this.setArmorPiercing(0);
        this.setHealth(100);
    }


}
