package Creature;

/**
 * Created by Immortan on 1/23/2016.
 */
public interface Attackable {

    void setHealth(float f);
    void setArmor(float f);
    void takeDamage(float damage, float armorPiercing);
    void takePoisonDamage(float damage, int poisonType);
    void die();
    float getHealth();
    float getArmor();

}
