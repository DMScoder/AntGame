package Creature;

import Grid.Cell;
import com.mygdx.game.Entity;
import com.mygdx.game.World;

/**
 * Created by Immortan on 1/23/2016.
 */
public class Resource extends Entity {

    Cell cell;
    String type;
    private int biomass;

    public Resource(float x, float y, String type, int biomass, float rotation,Cell cell) {
        super(x, y,0,0);
        this.biomass=biomass;
        this.setRotation(rotation);
        this.setTexture(type);
        this.type=type;
        this.cell = cell;
        cell.addResource(this);
    }

    public void pickedUp(Creature creature)
    {
        cell = null;
        this.setTexture("CarryResource");
    }

    public void drop(Creature creature, World world)
    {
        this.setTexture(type);
        this.setPosition(creature.getX(),creature.getY());
        this.cell = world.getFootPrint(creature);
    }

    public String getType()
    {
        return type;
    }

    public int getBiomass()
    {
        return biomass;
    }
}
