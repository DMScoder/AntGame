package Creature;

import Grid.Cell;
import com.mygdx.game.Entity;

/**
 * Created by Immortan on 1/23/2016.
 */
public class Resource extends Entity {

    Cell cell;
    String type;

    public Resource(float x, float y, String type, int biomass, float rotation,Cell cell) {
        super(x, y,0,0);
        this.setRotation(rotation);
        this.setTexture(type);
        this.cell = cell;
    }

    public void pickdUp(Creature creature)
    {

    }

    public void drop(Creature creature)
    {
        this.setTexture(type);
        this.setPosition(creature.getX(),creature.getY());
    }
}
