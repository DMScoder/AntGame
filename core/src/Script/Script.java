package Script;

import com.mygdx.game.World;

/**
 * Created by Immortan on 1/21/2016.
 */
public class Script {

    private World world;
    public final int DEFAULT = 1;

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
    }
}
