package Creature;

/**
 * Created by Immortan on 1/23/2016.
 */
public interface Attackable {

    void takeDamage(float damage);
    void takePoisonDamage(float damage, int poisonType);
    void die();
    float getHealth();
    float getArmor();

}
