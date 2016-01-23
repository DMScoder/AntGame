package Script;

import Creature.Cricket;
import Creature.Nexus;
import com.mygdx.game.World;

/**
 * Created by Immortan on 1/21/2016.
 */
public class Script {

    private World world;
    public static final int DEFAULT = 1;

    public Script(int choice,World world)
    {
        this.world=world;
        switch(choice)
        {
            case(DEFAULT):
                defaultScript();
                break;
        }
    }

    private void defaultScript()
    {
        world.createHive(0,0,1);
        Nexus cricketTest = new Nexus(world,2);
        Cricket cricket = new Cricket(100,100);
        cricketTest.addCreature(cricket);
        world.addCreature(cricket);
        world.addEntity(cricketTest);
    }
}
