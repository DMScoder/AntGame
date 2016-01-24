package Creature;

import com.mygdx.game.Entity;

/**
 * Created by Immortan on 1/23/2016.
 */
public class Resource extends Entity {

    public Resource(float x, float y, String type, int biomass, float rotation) {
        super(x, y);
        this.setRotation(rotation);
        this.setTexture(type);
    }
}
