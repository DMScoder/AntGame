package Script;

import Creature.ColonyUnit;
import Creature.Cricket;
import Creature.Nexus;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Hive;
import com.mygdx.game.Player;
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
        Player player = new Player(1,Player.ANT_DEFAULT,Player.HUMAN);
        Player fireAnts = new Player(2,Player.ANT_FIRE,Player.HUMAN);
        Player neutral = new Player(3,0,Player.HUMAN);
        world.createHive(0,0,player);
        Hive testHive = world.createHive(1000,2000,player);
        Nexus test =  testHive.spawnAnts(ColonyUnit.WORKER);
        test.merge(testHive.spawnAnts(ColonyUnit.WORKER));
        test.merge(testHive.spawnAnts(ColonyUnit.WORKER));
        test.merge(testHive.spawnAnts(ColonyUnit.WORKER));

        for(int i=0;i<5;i++)
        {
            Hive fireHive = world.createHive(i*-1000-1000,i*-1000-1000,fireAnts);
            Nexus fireNexus = fireHive.spawnAnts(ColonyUnit.WORKER);
            fireNexus.setTargetVector(new Vector3(-50,-50,0));
            fireNexus = fireHive.spawnAnts(ColonyUnit.WORKER);
            fireNexus.setTargetVector(new Vector3(-50,-50,0));
            fireNexus = fireHive.spawnAnts(ColonyUnit.WORKER);
            fireNexus.setTargetVector(new Vector3(-50,-50,0));
            fireNexus = fireHive.spawnAnts(ColonyUnit.WORKER);
            fireNexus.setTargetVector(new Vector3(-50,-50,0));
        }
        Hive fireHive = world.createHive(2000,2000,fireAnts);
        Nexus fireNexus = fireHive.spawnAnts(ColonyUnit.WORKER);
        fireNexus.merge(fireHive.spawnAnts(ColonyUnit.WORKER));
        fireNexus.merge(fireHive.spawnAnts(ColonyUnit.WORKER));
        fireNexus.merge(fireHive.spawnAnts(ColonyUnit.WORKER));
        generateCricketNexus(neutral);
        generateCricketNexus(neutral);
        generateCricketNexus(neutral);
    }

    private void generateCricketNexus(Player player)
    {

        Random r = new Random();
        int n = 15;
        int x = r.nextInt(5000)+500;
        int y = r.nextInt(5000)+500;
        Nexus crickets = new Nexus(world,player);
        for(int i=0;i<n;i++)
        {
            Cricket cricket = new Cricket(x,y,player);
            crickets.addCreature(cricket);
            world.addCreature(cricket);
        }
        world.addEntity(crickets);
    }
}
