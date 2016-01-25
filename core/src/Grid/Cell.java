package Grid;

import Creature.Resource;
import com.mygdx.game.Entity;

import java.util.ArrayList;

/**
 * Created by Immortan on 1/21/2016.
 */
public class Cell {

    public int x;
    public int y;
    public Entity entity = null;
    public ArrayList <Resource> resources = new ArrayList(1);

    public Cell(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    public void addResource(Resource resource)
    {
        resources.add(resource);
    }

    public Resource getResource()
    {
        resources.trimToSize();
        Resource temp = resources.get(0);
        resources.remove(temp);
        return temp;
    }
}
