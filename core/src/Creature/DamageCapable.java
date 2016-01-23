package Creature;

/**
 * Created by Immortan on 1/23/2016.
 */
public interface DamageCapable {

    float getDamage();
    float getArmorPiercing();
    int getPoison();
    void setDamage(float f);
    void setArmorPiercing(float f);
    void setPoison(int i);
}
