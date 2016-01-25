package Script;

import Creature.Cricket;
import Creature.Nexus;
import com.mygdx.game.World;

import java.util.Random;

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
        generateCricketNexus();
    }

    private void generateCricketNexus()
    {
        Random r = new Random();
        int n = 15;
        Nexus crickets = new Nexus(world,2);
        for(int i=0;i<n;i++)
        {
            Cricket cricket = new Cricket(1500,1500);
            crickets.addCreature(cricket);
            world.addCreature(cricket);
        }
        world.addEntity(crickets);
    }
}
